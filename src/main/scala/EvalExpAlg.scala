import algebra.ExpAlg
import interpreters.Evaluator

object EvalExpAlg extends ExpAlg[Evaluator] {
  override def Lit(x: Int): Evaluator =
    new Evaluator {
      override def eval: Int = x
    }

  override def Add(e1: Evaluator, e2: Evaluator): Evaluator =
    new Evaluator {
      override def eval: Int = e1.eval + e2.eval
    }
}
