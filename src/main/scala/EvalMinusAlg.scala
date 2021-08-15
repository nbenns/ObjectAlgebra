import algebra.MinusAlg
import interpreters.Evaluator
import zio.{Has, ULayer}

object EvalMinusAlg extends MinusAlg[ULayer[Has[Evaluator]]] {
  override def Minus(e1: ULayer[Has[Evaluator]], e2: ULayer[Has[Evaluator]]): ULayer[Has[Evaluator]] =
    e1.zipPar(e2).map { case (a, b) =>
      Has(new Evaluator {
        override def eval: Int = a.get.eval - b.get.eval
      })
  }
}
