import algebra.expalg.ExpAlg
import interpreters.evaluator.Evaluator
import zio.{Has, ULayer, ZLayer}

object EvalExpAlg extends ExpAlg.Service[ULayer[Evaluator]] {
  override def Lit(x: Int): ULayer[Evaluator] = ZLayer.succeed(new Evaluator.Service {
    override def eval: Int = x
  })

  override def Add(e1: ULayer[Evaluator], e2: ULayer[Evaluator]): ULayer[Evaluator] =
    e1.zipPar(e2)
      .map { case (a, b) =>
        Has(new Evaluator.Service {
          override def eval: Int = a.get.eval + b.get.eval
        })
      }
}
