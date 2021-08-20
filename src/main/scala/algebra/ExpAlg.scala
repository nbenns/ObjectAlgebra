package algebra

import cats.InvariantSemigroupal

trait ExpAlg[A] {
  def Lit(x: Int): A
  def Add(a: A, b: A): A

  extension(a: A) {
    def +(b: A): A = Add(a, b)
  }

  extension(x: Int) {
    def lit: A = Lit(x)
  }
}

object ExpAlg {
  def apply[E](implicit expAlg: ExpAlg[E]): ExpAlg[E] = expAlg

  def lit[E: ExpAlg](x: Int): E = ExpAlg[E].Lit(x)
  def add[E: ExpAlg](e1: E, e2: E): E = ExpAlg[E].Add(e1, e2)

  given InvariantSemigroupal[ExpAlg] with {
    override def imap[A, B](fa: ExpAlg[A])(f: A => B)(g: B => A): ExpAlg[B] =
      new ExpAlg[B] {
        override def Lit(x: Int): B = f(fa.Lit(x))
        override def Add(e1: B, e2: B): B = f(fa.Add(g(e1), g(e2)))
      }

    override def product[A, B](fa: ExpAlg[A], fb: ExpAlg[B]): ExpAlg[(A, B)] =
      new ExpAlg[(A, B)] {
        override def Lit(x: Int): (A, B) = (fa.Lit(x), fb.Lit(x))
        override def Add(e1: (A, B), e2: (A, B)): (A, B) = (fa.Add(e1._1, e2._1), fb.Add(e1._2, e2._2))
      }
  }
}
