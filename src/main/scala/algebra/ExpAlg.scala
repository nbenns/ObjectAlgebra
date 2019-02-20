package algebra

trait ExpAlg[E] {
  def Lit(x: Int): E
  def Add(e1: E, e2: E): E
}

object ExpAlg {
  def apply[E](implicit expAlg: ExpAlg[E]): ExpAlg[E] = expAlg
}


