package algebra

trait MinusAlg[E] {
  def Minus(e1: E, e2: E): E
}

object MinusAlg {
  def apply[E](implicit minusAlg: MinusAlg[E]): MinusAlg[E] = minusAlg
}
