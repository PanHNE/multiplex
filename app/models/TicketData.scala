package models

import play.api.data.Forms.{longNumber, mapping}
import play.api.data.{Form, Forms}
import play.api.libs.json.{Json, OWrites}
import utils.FormHelper
import utils.TicketType.TicketType

case class TicketData(id: Long, reservationId: Long, seat: Seat, ticketType: TicketType)

object TicketData {
  import FormHelper._
  implicit val writer: OWrites[TicketData] = Json.writes[TicketData]

  val form: Form[TicketData] = Form(
    mapping(
      "id" -> longNumber,
      "reservationId" -> longNumber,
      "seat" -> Seat.form.mapping,
      "typeTicket" ->  Forms.of[TicketType]
    )(TicketData.apply)(TicketData.unapply)
  )
}