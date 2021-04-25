import algebra.MinusAlg
import interpreters.Printer

object PrintMinusAlg extends MinusAlg[Printer] {
  override def Minus(e1: Printer, e2: Printer): Printer = new Printer {
    override def print: String = e1.print + " - " + e2.print
  }
}
