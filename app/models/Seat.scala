package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Json, OWrites}

case class Seat(id: Option[Long], screeningId: Long, row: Int, numberSeat: Int, available: Boolean)

object Seat {
  implicit val writer: OWrites[Seat] = Json.writes[Seat]

  val form: Form[Seat] = Form(
    mapping(
      "id" -> optional(longNumber),
      "screeningId" -> longNumber,
      "row" -> number,
      "numberSeat" -> number,
      "available" ->  boolean
    )(Seat.apply)(Seat.unapply)
  )
}