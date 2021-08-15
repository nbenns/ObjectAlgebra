package algebra

import cats.InvariantSemigroupal
import zio._

trait ExpAlg[A] {
  def Lit(x: Int): A
  def Add(a: A, b: A): A
}

object ExpAlg {
  def lit[E: Tag](x: Int): ZIO[Has[ExpAlg[E]], Nothing, E] =
    ZIO.service[ExpAlg[E]].map(_.Lit(x))

  def add[E: Tag](e1: E, e2: E): ZIO[Has[ExpAlg[E]], Nothing, E] =
    ZIO.service[ExpAlg[E]].map(_.Add(e1, e2))

  implicit val expAlgISemigroupal: InvariantSemigroupal[ExpAlg] = new InvariantSemigroupal[ExpAlg] {
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