import algebra.ExpAlg
import interpreters.Printer
import zio.{Has, ULayer, ZLayer}

object PrintExpAlg extends ExpAlg[Has[Printer]] {
  override def Lit(x: Int): Has[Printer] =
    Has(new Printer {
      override def print: String = x.toString
    })

  override def Add(e1: Has[Printer], e2: Has[Printer]): Has[Printer] =
    Has(new Printer {
      override def print: String = e1.get.print + " + " + e2.get.print
    })
}
