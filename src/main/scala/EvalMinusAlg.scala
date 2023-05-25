import algebra.MinusAlg
import interpreters.Evaluator

object EvalMinusAlg extends MinusAlg[Evaluator] {
  override def Minus(e1: Evaluator, e2: Evaluator): Evaluator =
    Evaluator(e1.eval - e2.eval)
}
