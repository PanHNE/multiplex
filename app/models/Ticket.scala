package models

import play.api.data.Forms.{longNumber, mapping, optional}
import play.api.data.{Form, Forms}
import play.api.libs.json.{Json, OWrites}
import utils.FormHelper
import utils.TicketType.TicketType

case class Ticket(id: Option[Long], reservationId: Long, seatId: Long, ticketType: TicketType)

object Ticket {
  import FormHelper._
  implicit val writer: OWrites[Ticket] = Json.writes[Ticket]

  val form: Form[Ticket] = Form(
    mapping(
      "id" -> optional(longNumber),
      "reservationId" -> longNumber,
      "seatId" -> longNumber,
      "typeTicket" -> Forms.of[TicketType]
    )(Ticket.apply)(Ticket.unapply)
  )
}
