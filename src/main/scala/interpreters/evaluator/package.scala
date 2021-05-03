package interpreters

import zio.{Has, ULayer, ZIO}

package object evaluator {
  type Evaluator = Has[Evaluator.Service]

  object Evaluator {
    trait Service {
      def eval: Int
    }

    def eval: ZIO[Evaluator, Nothing, Int] =
      ZIO.service[Evaluator.Service].map(_.eval)
  }
}
