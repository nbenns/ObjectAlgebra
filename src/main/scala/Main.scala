import algebra.expalg.ExpAlg
import algebra.minusalg.MinusAlg
import cats.implicits._
import interpreters.evaluator.Evaluator
import interpreters.evaluator.Evaluator.eval
import interpreters.printer.Printer
import interpreters.printer.Printer.print
import zio.console.putStrLn
import zio.{App, ExitCode, Has, Tag, ULayer, URIO, ZEnv, ZLayer}

object Main extends App {
  val e1: ExpAlg.Service[ULayer[Evaluator]]   = EvalExpAlg
  val e2: MinusAlg.Service[ULayer[Evaluator]] = EvalMinusAlg
  val p1: ExpAlg.Service[ULayer[Printer]]     = PrintExpAlg
  val p2: MinusAlg.Service[ULayer[Printer]]   = PrintMinusAlg

  def splitLayer[A: Tag, B: Tag](layer: ULayer[Has[A] with Has[B]]): (ULayer[Has[A]], ULayer[Has[B]]) =
    (layer.map(a => Has(a.get[A])), layer.map(b => Has(b.get[B])))

  val ep1: ExpAlg.Service[ULayer[Evaluator with Printer]] =
    (e1, p1)
      .imapN(_ ++ _)(splitLayer[Evaluator.Service, Printer.Service])

  val lep1: ULayer[ExpAlg[ULayer[Evaluator with Printer]]] =
    ZLayer.succeed(ep1)

  val ep2: MinusAlg.Service[ULayer[Evaluator with Printer]] =
    (e2, p2)
      .imapN(_ ++ _ )(splitLayer[Evaluator.Service, Printer.Service])

  val lep2: ULayer[MinusAlg[ULayer[Evaluator with Printer]]] =
    ZLayer.succeed(ep2)

  val dependencies: ULayer[ExpAlg[ULayer[Evaluator with Printer]] with MinusAlg[ULayer[Evaluator with Printer]]] =
    lep1 ++ lep2

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = (
    for {
      exp            <- Expressions.exp2[ULayer[Evaluator with Printer]]
      (p, e)         <- print.zip(eval).provideCustomLayer(exp)
      _              <- putStrLn(s"Printed: $p")
      _              <- putStrLn(s"Evaluated: $e")
    } yield ()
  ).provideCustomLayer(dependencies)
    .exitCode
}
