import algebra.minusalg.MinusAlg
import interpreters.printer.Printer
import zio.{Has, ULayer}

object PrintMinusAlg extends MinusAlg.Service[ULayer[Printer]] {
  override def Minus(e1: ULayer[Printer], e2: ULayer[Printer]): ULayer[Printer] =
    e1.zipPar(e2)
      .map { case (a, b) =>
        Has(new Printer.Service {
          override def print: String = a.get.print + " - " + b.get.print
        })
      }
}
