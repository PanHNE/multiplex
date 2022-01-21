package models

import play.api.libs.json.{Json, OFormat}

case class Room(id: Long, numberOfRows: Int, numberOfSeatsInRow: Int)

object Room {
  implicit val format: OFormat[Room] = Json.format[Room]
}
