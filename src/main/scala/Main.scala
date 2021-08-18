import algebra.*
import cats.implicits.*
import cats.InvariantSemigroupal
import interpreters.*
import zio.Console.putStrLn
import zio.*

object Main extends App {
  def mergeInterpreters[F[_]: InvariantSemigroupal, A: Tag, B: Tag](fa: F[Has[A]], fb: F[Has[B]]): F[Has[A] & Has[B]] =
    (fa, fb)
      .imapN { (hasA, hasB) =>
        val a = hasA.get[A]
        val b = hasB.get[B]
        val res = hasA ++ hasB  // This breaks in Scala 3
        res
      } { combined =>
        val a = combined.get[A]
        val b = combined.get[B]

        (Has(a), Has(b))
      }

  implicit val ep1: ExpAlg[Has[Evaluator] & Has[Printer]] =
    mergeInterpreters[ExpAlg, Evaluator, Printer](EvalExpAlg, PrintExpAlg)

  implicit val ep2: MinusAlg[Has[Evaluator] & Has[Printer]] =
    mergeInterpreters[MinusAlg, Evaluator, Printer](EvalMinusAlg, PrintMinusAlg)

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    val exp: Has[Evaluator] & Has[Printer] = Expressions.exp2[Has[Evaluator] & Has[Printer]]

    val printer: Printer = exp.get[Printer]
    val evaluator: Evaluator = exp.get[Evaluator]

    val printed: String = printer.print
    val evaluated: Int = evaluator.eval

    putStrLn(s"Printed: $printed") *>
    putStrLn(s"Evaluated: $evaluated")
  }.exitCode
}
