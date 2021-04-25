package algebra

import zio.{Has, Tag}

trait ExpAlg[E] {
  def Lit(x: Int): E
  def Add(e1: E, e2: E): E
}

object ExpAlg {
  def apply[E](implicit expAlg: ExpAlg[E]): ExpAlg[E] = expAlg

  def lit[E: ExpAlg](x: Int): E = ExpAlg[E].Lit(x)

  implicit class ExpAlgOps[E: ExpAlg](e1: E) {
    def add(e2: E): E = ExpAlg[E].Add(e1, e2)
  }

  implicit def hasExpr[A: ExpAlg: Tag]: ExpAlg[Has[A]] = new ExpAlg[Has[A]] {
    override def Lit(x: Int): Has[A] = Has(ExpAlg[A].Lit(x))

    override def Add(e1: Has[A], e2: Has[A]): Has[A] = Has(ExpAlg[A].Add(e1.get, e2.get))
  }

  def lift[A: Tag, B: Tag](implicit a: ExpAlg[Has[A]], b: ExpAlg[Has[B]]): ExpAlg[Has[A] with Has[B]] = new ExpAlg[Has[A] with Has[B]] {
    override def Lit(x: Int): Has[A] with Has[B] = a.Lit(x) ++ b.Lit(x)

    override def Add(e1: Has[A] with Has[B], e2: Has[A] with Has[B]): Has[A] with Has[B] =
      a.Add(e1, e2) ++ b.Add(e1, e2)
  }

  def lift[A: Tag, B: Tag, C: Tag](implicit a: ExpAlg[Has[A]], b: ExpAlg[Has[B]], c: ExpAlg[Has[C]]): ExpAlg[Has[A] with Has[B] with Has[C]] = new ExpAlg[Has[A] with Has[B] with Has[C]] {
    override def Lit(x: Int): Has[A] with Has[B] with Has[C] = a.Lit(x) ++ b.Lit(x) ++ c.Lit(x)

    override def Add(e1: Has[A] with Has[B] with Has[C], e2: Has[A] with Has[B] with Has[C]): Has[A] with Has[B] with Has[C] =
      a.Add(e1, e2) ++ b.Add(e1, e2) ++ c.Add(e1, e2)
  }
}


