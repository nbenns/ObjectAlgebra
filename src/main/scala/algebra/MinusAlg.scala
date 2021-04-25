package algebra

import zio.{Has, Tag}

trait MinusAlg[E] {
  def Minus(e1: E, e2: E): E
}

object MinusAlg {
  def apply[E](implicit minusAlg: MinusAlg[E]): MinusAlg[E] = minusAlg

  implicit class MinusAlgOps[E: MinusAlg](e1: E) {
    def minus(e2: E): E = MinusAlg[E].Minus(e1, e2)
  }

  implicit def hasExpr[A: MinusAlg: Tag]: MinusAlg[Has[A]] = new MinusAlg[Has[A]] {
    override def Minus(e1: Has[A], e2: Has[A]): Has[A] = Has(MinusAlg[A].Minus(e1.get, e2.get))
  }

  def lift[A: Tag, B: Tag](implicit a: MinusAlg[Has[A]], b: MinusAlg[Has[B]]): MinusAlg[Has[A] with Has[B]] = new MinusAlg[Has[A] with Has[B]] {
    override def Minus(e1: Has[A] with Has[B], e2: Has[A] with Has[B]): Has[A] with Has[B] = a.Minus(e1, e2) ++ b.Minus(e1, e2)
  }

  def lift[A: Tag, B: Tag, C: Tag](implicit a: MinusAlg[Has[A]], b: MinusAlg[Has[B]], c: MinusAlg[Has[C]]): MinusAlg[Has[A] with Has[B] with Has[C]] = new MinusAlg[Has[A] with Has[B] with Has[C]] {
    override def Minus(e1: Has[A] with Has[B] with Has[C], e2: Has[A] with Has[B] with Has[C]): Has[A] with Has[B] with Has[C] =
      a.Minus(e1, e2) ++ b.Minus(e1, e2) ++ c.Minus(e1, e2)
  }
}
