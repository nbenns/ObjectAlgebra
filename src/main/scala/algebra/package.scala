import algebra.interpreters.Lifter

package object algebra {
  implicit def combineExpAlg[A, B](implicit lifter: Lifter[A, B], aExpAlg: ExpAlg[A], bExpAlg: ExpAlg[B]): ExpAlg[A with B] = new ExpAlg[A with B] {
    override def Lit(x: Int): A with B = lifter.lift(aExpAlg.Lit(x), bExpAlg.Lit(x))
    override def Add(e1: A with B, e2: A with B): A with B = lifter.lift(ExpAlg[A].Add(e1, e2), ExpAlg[B].Add(e1, e2))
  }

  implicit def combineMinusAlg[A, B](implicit lifter: Lifter[A, B], aMinusAlg: MinusAlg[A], bMinusAlg: MinusAlg[B]): MinusAlg[A with B] = new MinusAlg[A with B] {
    override def Minus(e1: A with B, e2: A with B): A with B = lifter.lift(aMinusAlg.Minus(e1, e2), bMinusAlg.Minus(e1, e2))
  }

  def lit[Interpreter: ExpAlg](x: Int): Interpreter = ExpAlg[Interpreter].Lit(x)
  def add[Interpreter: ExpAlg](e1: Interpreter, e2: Interpreter): Interpreter = ExpAlg[Interpreter].Add(e1, e2)
  def minus[Interpreter: MinusAlg](e1: Interpreter, e2: Interpreter): Interpreter = MinusAlg[Interpreter].Minus(e1, e2)

  implicit class ExpAlgOps[Interpreter: ExpAlg](e1: Interpreter) {
    def add(e2: Interpreter): Interpreter = ExpAlg[Interpreter].Add(e1, e2)
  }

  implicit class MinusAlgOps[Interpreter: MinusAlg](e1: Interpreter) {
    def minus(e2: Interpreter): Interpreter = MinusAlg[Interpreter].Minus(e1, e2)
  }
}
