import algebra.*
import cats.implicits.given
import cats.InvariantSemigroupal
import interpreters.*
import zio.Console.printLine
import zio.*

object Main extends App {
  extension[F[_]: InvariantSemigroupal, A: Tag](fa: F[Has[A]]) {
    def &[B: Tag](fb: F[Has[B]])(using ev:Tag[Has[B]]): F[Has[A] & Has[B]] =
      (fa, fb).imapN(_ ++ _)(combined => (Has(combined.get[A]), Has(combined.get[B])))
  }

  extension[F[_]: InvariantSemigroupal, A: Tag](fa: F[A]) {
    def has: F[Has[A]] = fa.imap(Has.apply)(_.get)
  }

  given ExpAlg[Has[Evaluator] & Has[Printer]] = EvalExpAlg.has & PrintExpAlg.has
  given MinusAlg[Has[Evaluator] & Has[Printer]] = EvalMinusAlg.has & PrintMinusAlg.has

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    val exp: Has[Evaluator] & Has[Printer] = Expressions.exp2[Has[Evaluator] & Has[Printer]]

    val printer: Printer = exp.get[Printer]
    val evaluator: Evaluator = exp.get[Evaluator]

    val printed: String = printer.print
    val evaluated: Int = evaluator.eval

    printLine(s"Printed: $printed") *>
    printLine(s"Evaluated: $evaluated")
  }.exitCode
}
