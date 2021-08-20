package algebra

import cats.InvariantSemigroupal

trait MinusAlg[A] {
  def Minus(a: A, b: A): A

  extension(a: A) {
    def -(b: A): A = Minus(a, b)
  }
}

object MinusAlg {
  def apply[E](implicit minusAlg: MinusAlg[E]): MinusAlg[E] = minusAlg

  def minus[E: MinusAlg](e1: E, e2: E): E = MinusAlg[E].Minus(e1, e2)

  given InvariantSemigroupal[MinusAlg] with {
    override def product[A, B](fa: MinusAlg[A], fb: MinusAlg[B]): MinusAlg[(A, B)] =
      new MinusAlg[(A, B)] {
        override def Minus(e1: (A, B), e2: (A, B)): (A, B) = (fa.Minus(e1._1, e2._1), fb.Minus(e1._2, e2._2))
      }

    override def imap[A, B](fa: MinusAlg[A])(f: A => B)(g: B => A): MinusAlg[B] =
      new MinusAlg[B] {
        override def Minus(e1: B, e2: B): B = f(fa.Minus(g(e1), g(e2)))
      }
  }
}
