package models

import play.api.libs.json.{Json, OFormat}

case class RoomSeatScreeningData(room: Room, screening: Screening, availableSeat: List[Seat])

object RoomSeatScreeningData {
  implicit val writer: OFormat[RoomSeatScreeningData] = Json.format[RoomSeatScreeningData]
}