import algebra.expalg.ExpAlg
import interpreters.printer.Printer
import zio.{Has, ULayer, ZLayer}

object PrintExpAlg extends ExpAlg.Service[ULayer[Printer]] {
  override def Lit(x: Int): ULayer[Printer] = ZLayer.succeed(new Printer.Service {
    override def print: String = x.toString
  })

  override def Add(e1: ULayer[Printer], e2: ULayer[Printer]): ULayer[Printer] =
    e1.zipPar(e2)
      .map { case (a, b) =>
        Has(new Printer.Service {
          override def print: String = a.get.print + " + " + b.get.print
        })
      }
}
