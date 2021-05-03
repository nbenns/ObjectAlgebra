package algebra

import cats.InvariantSemigroupal
import zio.{Has, Tag, ZIO}

package object minusalg {
  type MinusAlg[A] = Has[MinusAlg.Service[A]]

  object MinusAlg {
    trait Service[A] {
      def Minus(a: A, b: A): A
    }

    object Service {
      implicit val minusAlgISemigroupal: InvariantSemigroupal[MinusAlg.Service] = new InvariantSemigroupal[MinusAlg.Service] {
        override def product[A, B](fa: MinusAlg.Service[A], fb: MinusAlg.Service[B]): MinusAlg.Service[(A, B)] =
          new MinusAlg.Service[(A, B)] {
            override def Minus(e1: (A, B), e2: (A, B)): (A, B) = (fa.Minus(e1._1, e2._1), fb.Minus(e1._2, e2._2))
          }

        override def imap[A, B](fa: MinusAlg.Service[A])(f: A => B)(g: B => A): MinusAlg.Service[B] =
          new MinusAlg.Service[B] {
            override def Minus(e1: B, e2: B): B = f(fa.Minus(g(e1), g(e2)))
          }
      }
    }

    def minus[E: Tag](e1: E, e2: E): ZIO[MinusAlg[E], Nothing, E] =
      ZIO.service[MinusAlg.Service[E]].map(_.Minus(e1, e2))
  }
}
