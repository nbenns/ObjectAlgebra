package interpreters

trait Evaluator {
  def eval: Int
}

object Evaluator {
  def apply(i: Int): Evaluator =
    new Evaluator {
      override def eval: Int = i
    }
}
