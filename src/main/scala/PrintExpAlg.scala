import algebra.ExpAlg
import interpreters.Printer
import zio.{Has, ULayer, ZLayer}

object PrintExpAlg extends ExpAlg[ULayer[Has[Printer]]] {
  override def Lit(x: Int): ULayer[Has[Printer]] = ZLayer.succeed(new Printer {
    override def print: String = x.toString
  })

  override def Add(e1: ULayer[Has[Printer]], e2: ULayer[Has[Printer]]): ULayer[Has[Printer]] =
    e1.zipPar(e2)
      .map { case (a, b) =>
        Has(new Printer {
          override def print: String = a.get.print + " + " + b.get.print
        })
      }
}
