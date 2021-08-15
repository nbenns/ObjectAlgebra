import algebra.ExpAlg
import algebra.ExpAlg._
import algebra.MinusAlg
import algebra.MinusAlg.minus
import zio.{Tag, ZIO, Has}

object Expressions {
  def exp1[E: Tag]: ZIO[Has[ExpAlg[E]], Nothing, E] =
    for {
      a <- lit[E](2)
      b <- lit[E](3)
      c <- add(a, b)
    } yield c

  def exp2[E: Tag]: ZIO[Has[ExpAlg[E]] with Has[MinusAlg[E]], Nothing, E] =
    for {
      e1 <- exp1
      c <- lit[E](4)
      d <- minus(e1, c)
    } yield d
}
