package algebra

import cats.InvariantSemigroupal
import zio._

trait MinusAlg[A] {
  def Minus(a: A, b: A): A
}

object MinusAlg {
  def minus[E: Tag](e1: E, e2: E): ZIO[Has[MinusAlg[E]], Nothing, E] =
    ZIO.service[MinusAlg[E]].map(_.Minus(e1, e2))

  implicit val minusAlgISemigroupal: InvariantSemigroupal[MinusAlg] = new InvariantSemigroupal[MinusAlg] {
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
