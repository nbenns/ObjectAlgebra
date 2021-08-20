import algebra.MinusAlg
import interpreters.Evaluator
import zio.Has

object EvalMinusAlg extends MinusAlg[Evaluator] {
  override def Minus(e1: Evaluator, e2: Evaluator): Evaluator =
    new Evaluator {
      override def eval: Int = e1.eval - e2.eval
    }
}
