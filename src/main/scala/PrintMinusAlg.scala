import algebra.MinusAlg
import interpreters.Printer
import zio.{Has, ULayer}

object PrintMinusAlg extends MinusAlg[ULayer[Has[Printer]]] {
  override def Minus(e1: ULayer[Has[Printer]], e2: ULayer[Has[Printer]]): ULayer[Has[Printer]] =
    e1.zipPar(e2)
      .map { case (a, b) =>
        Has(new Printer {
          override def print: String = a.get.print + " - " + b.get.print
        })
      }
}
