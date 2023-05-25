import algebra.ExpAlg
import interpreters.Evaluator

object EvalExpAlg extends ExpAlg[Evaluator] {
  override def Lit(x: Int): Evaluator =
    Evaluator(x)

  override def Add(e1: Evaluator, e2: Evaluator): Evaluator =
    Evaluator(e1.eval + e2.eval)
}
