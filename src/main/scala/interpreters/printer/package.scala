package interpreters

import zio.{Has, ULayer, ZIO}

package object printer {
  type Printer = Has[Printer.Service]

  object Printer {
    trait Service {
      def print: String
    }

    def print: ZIO[Printer, Nothing, String] =
      ZIO.service[Printer.Service].map(_.print)
  }
}
