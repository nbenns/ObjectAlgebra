package interpreters

import zio._

trait Evaluator {
  def eval: Int
}

object Evaluator {
  def eval: ZIO[Has[Evaluator], Nothing, Int] =
    ZIO.service[Evaluator].map(_.eval)
}
