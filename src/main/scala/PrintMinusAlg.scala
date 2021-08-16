import algebra.MinusAlg
import interpreters.Printer
import zio.Has

object PrintMinusAlg extends MinusAlg[Has[Printer]] {
  override def Minus(e1: Has[Printer], e2: Has[Printer]): Has[Printer] =
    Has(new Printer {
      override def print: String = e1.get.print + " - " + e2.get.print
    })
}
