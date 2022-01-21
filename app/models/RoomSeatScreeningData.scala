package models

import play.api.libs.json.{Json, OFormat}

case class RoomSeatScreeningData(room: Room, screening: Screening, availableSeat: Seq[Seat])

object RoomSeatScreeningData {
  implicit val format: OFormat[RoomSeatScreeningData] = Json.format[RoomSeatScreeningData]
}
