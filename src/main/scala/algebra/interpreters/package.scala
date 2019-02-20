package algebra

package object interpreters {
  implicit val printevalLifter: Lifter[Printer, Evaluator] = new Lifter[Printer, Evaluator] {
    def lift(p: Printer, e: Evaluator): Printer with Evaluator = new Printer with Evaluator {
      override def print(): String = p.print()
      override def eval(): Int = e.eval()
    }
  }

  implicit val printjsonLifter: Lifter[Printer, JsonSerializer] = new Lifter[Printer, JsonSerializer] {
    override def lift(a: Printer, b: JsonSerializer): Printer with JsonSerializer = new Printer with JsonSerializer {
      override def print(): String = a.print()
      override def toJson(): Json = b.toJson()
    }
  }

  implicit val evaljsonLifter: Lifter[Evaluator, JsonSerializer] = new Lifter[Evaluator, JsonSerializer] {
    override def lift(a: Evaluator, b: JsonSerializer): Evaluator with JsonSerializer = new Evaluator with JsonSerializer {
      override def eval(): Int = a.eval()
      override def toJson(): Json = b.toJson()
    }
  }

  implicit val jsonprintevalLifter: Lifter[Printer with Evaluator, JsonSerializer] = new Lifter[Printer with Evaluator, JsonSerializer] {
    override def lift(a: Printer with Evaluator, b: JsonSerializer): Printer with Evaluator with JsonSerializer = new Printer with Evaluator with JsonSerializer {
      override def print(): String = a.print()
      override def eval(): Int = a.eval()
      override def toJson(): Json = b.toJson()
    }
  }

  implicit val printevalExpr: ExpAlg[Printer with Evaluator] = algebra.combineExpAlg[Printer, Evaluator]
  implicit val printevalMinus: MinusAlg[Printer with Evaluator] = algebra.combineMinusAlg[Printer, Evaluator]

  implicit val printevaljsonExpr: ExpAlg[Printer with Evaluator with JsonSerializer] = algebra.combineExpAlg[Printer with Evaluator, JsonSerializer]
  implicit val printevaljsonMinus: MinusAlg[Printer with Evaluator with JsonSerializer] = algebra.combineMinusAlg[Printer with Evaluator, JsonSerializer]
}
