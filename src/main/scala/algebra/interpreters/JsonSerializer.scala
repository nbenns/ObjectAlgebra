package algebra.interpreters

import algebra._

sealed trait Json
case object JsonNull extends Json
case class JsonBool(value: Boolean) extends Json
case class JsonNumber(value: Double) extends Json
case class JsonString(value: String) extends Json
case class JsonArray(values: List[Json]) extends Json
case class JsonObject(values: Map[String, Json]) extends Json

trait JsonSerializer {
  def toJson(): Json
}

object JsonSerializer {
  implicit def idataExpAlg: ExpAlg[JsonSerializer] = new ExpAlg[JsonSerializer] {
    override def Lit(x: Int): JsonSerializer = () => JsonNumber(x)

    override def Add(e1: JsonSerializer, e2: JsonSerializer): JsonSerializer =
      () => JsonObject(Map("operation" -> JsonString("Add"), "e1" -> e1.toJson(), "e2" -> e2.toJson()))
  }

  implicit def idataMinusAlg: MinusAlg[JsonSerializer] = new MinusAlg[JsonSerializer] {
    override def Minus(e1: JsonSerializer, e2: JsonSerializer): JsonSerializer = () =>
      JsonObject(Map("operation" -> JsonString("Minus"), "e1" -> e1.toJson(), "e2" -> e2.toJson()))
  }
}