import algebra.ExpAlg
import algebra.ExpAlg.*
import algebra.MinusAlg
import algebra.MinusAlg.minus

object Expressions {
  def exp1[E: ExpAlg]: E = {
    val a = lit(2)
    val b = lit(3)

    add(a, b)
  }

  def exp2[E: ExpAlg: MinusAlg]: E = {
    val e1 = exp1
    val c = lit(4)

    minus(e1, c)
  }
}
