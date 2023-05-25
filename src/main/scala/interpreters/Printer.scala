package interpreters

trait Printer {
  def print: String
}

object Printer {
  def apply(s: String): Printer =
    new Printer {
      override def print: String = s
    }
}
