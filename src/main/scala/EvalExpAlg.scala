import algebra.ExpAlg
import interpreters.Evaluator
import zio.{Has, ULayer, ZLayer}

object EvalExpAlg extends ExpAlg[ULayer[Has[Evaluator]]] {
  override def Lit(x: Int): ULayer[Has[Evaluator]] = ZLayer.succeed(new Evaluator {
    override def eval: Int = x
  })

  override def Add(e1: ULayer[Has[Evaluator]], e2: ULayer[Has[Evaluator]]): ULayer[Has[Evaluator]] =
    e1.zipPar(e2)
      .map { case (a, b) =>
        Has(new Evaluator {
          override def eval: Int = a.get.eval + b.get.eval
        })
      }
}
