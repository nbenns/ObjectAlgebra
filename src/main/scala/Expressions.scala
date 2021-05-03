import algebra.expalg.ExpAlg
import algebra.expalg.ExpAlg._
import algebra.minusalg.MinusAlg
import algebra.minusalg.MinusAlg.minus
import zio.{Tag, ZIO}

object Expressions {
  def exp1[E: Tag]: ZIO[ExpAlg[E], Nothing, E] =
    for {
      a <- lit[E](2)
      b <- lit[E](3)
      c <- add(a, b)
    } yield c

  def exp2[E: Tag]: ZIO[ExpAlg[E] with MinusAlg[E], Nothing, E] =
    for {
      e1 <- exp1
      c <- lit[E](4)
      d <- minus(e1, c)
    } yield d
}
