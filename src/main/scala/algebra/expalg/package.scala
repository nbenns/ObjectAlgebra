package algebra

import cats.InvariantSemigroupal
import zio.{Has, Tag, ZIO}

package object expalg {
  type ExpAlg[A] = Has[ExpAlg.Service[A]]

  object ExpAlg {
    trait Service[A] {
      def Lit(x: Int): A
      def Add(a: A, b: A): A
    }

    object Service {
      implicit val expAlgISemigroupal: InvariantSemigroupal[ExpAlg.Service] = new InvariantSemigroupal[ExpAlg.Service] {
        override def imap[A, B](fa: ExpAlg.Service[A])(f: A => B)(g: B => A): ExpAlg.Service[B] =
          new ExpAlg.Service[B] {
            override def Lit(x: Int): B = f(fa.Lit(x))
            override def Add(e1: B, e2: B): B = f(fa.Add(g(e1), g(e2)))
          }

        override def product[A, B](fa: ExpAlg.Service[A], fb: ExpAlg.Service[B]): ExpAlg.Service[(A, B)] =
          new ExpAlg.Service[(A, B)] {
            override def Lit(x: Int): (A, B) = (fa.Lit(x), fb.Lit(x))
            override def Add(e1: (A, B), e2: (A, B)): (A, B) = (fa.Add(e1._1, e2._1), fb.Add(e1._2, e2._2))
          }
      }
    }

    def lit[E: Tag](x: Int): ZIO[ExpAlg[E], Nothing, E] =
      ZIO.service[ExpAlg.Service[E]].map(_.Lit(x))

    def add[E: Tag](e1: E, e2: E): ZIO[ExpAlg[E], Nothing, E] =
      ZIO.service[ExpAlg.Service[E]].map(_.Add(e1, e2))
  }
}
