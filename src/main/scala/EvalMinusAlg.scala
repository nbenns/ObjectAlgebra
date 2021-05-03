import algebra.minusalg.MinusAlg
import interpreters.evaluator.Evaluator
import zio.{Has, ULayer, ZLayer}

object EvalMinusAlg extends MinusAlg.Service[ULayer[Evaluator]] {
  override def Minus(e1: ULayer[Evaluator], e2: ULayer[Evaluator]): ULayer[Evaluator] =
    e1.zipPar(e2).map { case (a, b) =>
      Has(new Evaluator.Service {
        override def eval: Int = a.get.eval - b.get.eval
      })
  }
}
