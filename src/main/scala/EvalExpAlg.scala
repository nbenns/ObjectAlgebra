import algebra.ExpAlg
import interpreters.Evaluator
import zio.Has

object EvalExpAlg extends ExpAlg[Has[Evaluator]] {
  override def Lit(x: Int): Has[Evaluator] =
    Has(new Evaluator {
      override def eval: Int = x
    })

  override def Add(e1: Has[Evaluator], e2: Has[Evaluator]): Has[Evaluator] =
    Has(new Evaluator {
      override def eval: Int = e1.get.eval + e2.get.eval
    })
}
