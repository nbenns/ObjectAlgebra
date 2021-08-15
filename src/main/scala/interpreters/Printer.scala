package interpreters

import zio._

trait Printer {
  def print: String
}

object Printer {
  def print: ZIO[Has[Printer], Nothing, String] =
    ZIO.service[Printer].map(_.print)
}
