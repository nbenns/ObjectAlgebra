package algebra.interpreters

import algebra._

trait Evaluator {
  def eval(): Int
}

object Evaluator {
  implicit val ievalExpAlg: ExpAlg[Evaluator] = new ExpAlg[Evaluator] {
    override def Lit(x: Int): Evaluator = () => x
    override def Add(e1: Evaluator, e2: Evaluator): Evaluator = () => e1.eval + e2.eval
  }

  implicit val ievalMinusAlg: MinusAlg[Evaluator] = new MinusAlg[Evaluator] {
    override def Minus(e1: Evaluator, e2: Evaluator): Evaluator = () => e1.eval - e2.eval
  }
}
