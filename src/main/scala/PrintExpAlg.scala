import algebra.ExpAlg
import interpreters.Printer

object PrintExpAlg extends ExpAlg[Printer] {
  override def Lit(x: Int): Printer =
    Printer(x.toString)

  override def Add(e1: Printer, e2: Printer): Printer =
    Printer(e1.print + " + " + e2.print)
}
