import algebra._

object Expressions {
  def exp1[Interpreter: ExpAlg]: Interpreter = lit(3) add lit(4)
  def exp2[Interpreter: ExpAlg: MinusAlg]: Interpreter = exp1 minus lit(5)
}
