import algebra.*
import cats.implicits.given
import cats.InvariantSemigroupal
import interpreters.*
import zio.Console.printLine
import zio.*

import java.io.IOException

object Main extends ZIOAppDefault {
  extension[F[_] : InvariantSemigroupal, A: Tag] (fa: F[ZEnvironment[A]]) {
    def &[B: Tag](fb: F[ZEnvironment[B]]): F[ZEnvironment[A & B]] =
      (fa, fb).imapN(_ ++ _)(combined => (ZEnvironment(combined.get[A]), ZEnvironment(combined.get[B])))
  }

  extension[F[_] : InvariantSemigroupal, A: Tag] (fa: F[A]) {
    def env: F[ZEnvironment[A]] = fa.imap(ZEnvironment.apply)(_.get)
  }

  given ExpAlg[ZEnvironment[Evaluator & Printer]] = EvalExpAlg.env & PrintExpAlg.env
  given MinusAlg[ZEnvironment[Evaluator & Printer]] = EvalMinusAlg.env & PrintMinusAlg.env

  override def run: ZIO[Any, IOException, Unit] = {
    val exp: ZEnvironment[Evaluator & Printer] = Expressions.exp2[ZEnvironment[Evaluator & Printer]]

    val printer: Printer = exp.get[Printer]
    val evaluator: Evaluator = exp.get[Evaluator]

    val printed: String = printer.print
    val evaluated: Int = evaluator.eval

    printLine(s"Printed: $printed") *>
    printLine(s"Evaluated: $evaluated")
  }
}
