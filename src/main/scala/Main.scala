import algebra.interpreters._

object Main extends App {
  type Interpreters = Printer with Evaluator with JsonSerializer
  val n = Expressions.exp2[Interpreters]
  println(s"Evaluated: ${n.eval()}, Printed: ${n.print()}, JsonSerialized: ${n.toJson()}")
}
