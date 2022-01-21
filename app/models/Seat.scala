package models

import play.api.libs.json.{Json, OFormat}

case class Seat(id: Option[Long], screeningId: Long, row: Int, numberSeat: Int, available: Boolean)

object Seat {
  implicit val format: OFormat[Seat] = Json.format[Seat]
}
