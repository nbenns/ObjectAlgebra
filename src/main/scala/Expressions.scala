import algebra.{ExpAlg, MinusAlg}
import algebra.ExpAlg.{ExpAlgOps, lit}
import algebra.MinusAlg.MinusAlgOps
import zio.{Has, Tag, ZIO}


object Expressions {
  def exp1[I: ExpAlg]: I = lit(3) add lit(4)
  def exp2[I: ExpAlg: MinusAlg]: I = exp1 minus lit(5)

  def exp3[E: Tag]: ZIO[Has[MinusAlg[E]] with Has[ExpAlg[E]], Nothing, E] = {
    for {
      expr  <- ZIO.service[ExpAlg[E]]
      minus <- ZIO.service[MinusAlg[E]]
    } yield {
      val a = expr.Lit(2)
      val b = expr.Lit(3)
      val c = expr.Lit(4)
      val d = expr.Add(a, b)
      minus.Minus(d, c)
    }
  }

}
