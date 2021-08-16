import algebra.MinusAlg
import interpreters.Evaluator
import zio.Has

object EvalMinusAlg extends MinusAlg[Has[Evaluator]] {
  override def Minus(e1: Has[Evaluator], e2: Has[Evaluator]): Has[Evaluator] =
    Has(new Evaluator {
      override def eval: Int = e1.get.eval - e2.get.eval
    })
}
