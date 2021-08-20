import algebra.ExpAlg
import interpreters.Printer
import zio.{Has, ULayer, ZLayer}

object PrintExpAlg extends ExpAlg[Printer] {
  override def Lit(x: Int): Printer =
    new Printer {
      override def print: String = x.toString
    }

  override def Add(e1: Printer, e2: Printer): Printer =
    new Printer {
      override def print: String = e1.print + " + " + e2.print
    }
}
