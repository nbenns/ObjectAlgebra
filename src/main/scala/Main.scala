import interpreters.{Printer, Evaluator}
import algebra.{ExpAlg, MinusAlg}
import zio.console.putStrLn
import zio.{App, ExitCode, Has, ULayer, URIO, URLayer, ZEnv, ZIO, ZLayer}

object Main extends App {
  implicit val e1: ExpAlg[Evaluator]   = EvalExpAlg
  implicit val e2: MinusAlg[Evaluator] = EvalMinusAlg
  implicit val p1: ExpAlg[Printer]     = PrintExpAlg
  implicit val p2: MinusAlg[Printer]   = PrintMinusAlg

  val interpretersExprAlg: ULayer[Has[ExpAlg[Has[Printer] with Has[Evaluator]]]] =
    ZLayer.succeed(ExpAlg.lift[Printer, Evaluator])

  val interpretersMinusAlg: ULayer[Has[MinusAlg[Has[Printer] with Has[Evaluator]]]] =
    ZLayer.succeed(MinusAlg.lift[Printer, Evaluator])

  val expression: URLayer[Has[MinusAlg[Has[Printer] with Has[Evaluator]]] with Has[ExpAlg[Has[Printer] with Has[Evaluator]]], Has[Printer] with Has[Evaluator]] =
    Expressions.exp3[Has[Printer] with Has[Evaluator]].toLayer.map(_.get)

  val dependencies: ULayer[Has[Printer] with Has[Evaluator]] =
    (interpretersExprAlg ++ interpretersMinusAlg) >>> expression

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    (
      for {
        printer        <- ZIO.service[Printer]
        evaluator      <- ZIO.service[Evaluator]
        _              <- putStrLn(s"Printed: ${printer.print}")
        _              <- putStrLn(s"Evaluated: ${evaluator.eval}")
      } yield ()
    ).provideCustomLayer(dependencies)
      .exitCode
  }
}
