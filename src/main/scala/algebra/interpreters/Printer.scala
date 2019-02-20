package algebra.interpreters

import algebra._

trait Printer {
  def print(): String
}

object Printer {
  implicit val iprintExpAlg: ExpAlg[Printer] = new ExpAlg[Printer] {
    override def Lit(x: Int): Printer = () => x.toString
    override def Add(e1: Printer, e2: Printer): Printer = () => e1.print + " + " + e2.print
  }

  implicit val iprintMinusAlg: MinusAlg[Printer] = new MinusAlg[Printer] {
    override def Minus(e1: Printer, e2: Printer): Printer = () => e1.print + " - " + e2.print
  }
}