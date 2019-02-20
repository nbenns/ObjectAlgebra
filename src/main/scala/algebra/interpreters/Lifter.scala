package algebra.interpreters

trait Lifter[A, B] {
  def lift(a: A, b: B): A with B
}

object Lifter {
  def lift[A, B](a: A, b: B)(implicit lifter: Lifter[A, B]): A with B = lifter.lift(a, b)
}
