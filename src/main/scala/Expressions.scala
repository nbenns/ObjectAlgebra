import algebra.ExpAlg
import algebra.ExpAlg.*
import algebra.MinusAlg
import algebra.MinusAlg.minus

object Expressions {
  def exp1[A: ExpAlg]: A =
    2.lit + 3.lit

  def exp2[A: ExpAlg: MinusAlg]: A =
    exp1 - lit(4)
}
