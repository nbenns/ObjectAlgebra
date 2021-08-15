import algebra._
import cats.implicits._
import interpreters._
import interpreters.Evaluator.eval
import interpreters.Printer.print
import zio.console.putStrLn
import zio._

object Main extends App {
  val e1: ExpAlg[ULayer[Has[Evaluator]]]   = EvalExpAlg
  val e2: MinusAlg[ULayer[Has[Evaluator]]] = EvalMinusAlg
  val p1: ExpAlg[ULayer[Has[Printer]]]     = PrintExpAlg
  val p2: MinusAlg[ULayer[Has[Printer]]]   = PrintMinusAlg

  def splitLayer[A: Tag, B: Tag](layer: ULayer[Has[A] with Has[B]]): (ULayer[Has[A]], ULayer[Has[B]]) =
    (layer.map(a => Has(a.get[A])), layer.map(b => Has(b.get[B])))

  val ep1: ExpAlg[ULayer[Has[Evaluator] with Has[Printer]]] =
    (e1, p1)
      .imapN(_ ++ _)(splitLayer[Evaluator, Printer])

  val lep1: ULayer[Has[ExpAlg[ULayer[Has[Evaluator] with Has[Printer]]]]] =
    ZLayer.succeed(ep1)

  val ep2: MinusAlg[ULayer[Has[Evaluator] with Has[Printer]]] =
    (e2, p2)
      .imapN(_ ++ _ )(splitLayer[Evaluator, Printer])

  val lep2: ULayer[Has[MinusAlg[ULayer[Has[Evaluator] with Has[Printer]]]]] =
    ZLayer.succeed(ep2)

  val dependencies: ULayer[Has[ExpAlg[ULayer[Has[Evaluator] with Has[Printer]]]] with Has[MinusAlg[ULayer[Has[Evaluator] with Has[Printer]]]]] =
    lep1 ++ lep2

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = (
    for {
      exp            <- Expressions.exp2[ULayer[Has[Evaluator] with Has[Printer]]]
      (p, e)         <- print.zip(eval).provideCustomLayer(exp)
      _              <- putStrLn(s"Printed: $p")
      _              <- putStrLn(s"Evaluated: $e")
    } yield ()
  ).provideCustomLayer(dependencies)
    .exitCode
}
