package models

import play.api.data.Form
import play.api.data.Forms.{mapping, seq}
import play.api.libs.json.{Json, OWrites}

case class RoomSeatScreeningData(room: Room, screening: Screening, availableSeat: Seq[Seat])

object RoomSeatScreeningData {
  implicit val writer: OWrites[RoomSeatScreeningData] = Json.writes[RoomSeatScreeningData]

  val form: Form[RoomSeatScreeningData] = Form(
    mapping(
      "room" -> Room.form.mapping,
      "screening" ->  Screening.form.mapping,
      "availableSeat" ->  seq(Seat.form.mapping)
    )(RoomSeatScreeningData.apply)(RoomSeatScreeningData.unapply)
  )
}
