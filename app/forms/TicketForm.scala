package forms

import play.api.data.Forms.{longNumber, mapping}
import play.api.data.{Form, Forms}
import play.api.libs.json.{Json, OWrites}
import utils.FormHelper
import utils.TicketType.TicketType

case class TicketForm(seatId: Long, typeTicket: TicketType)

object TicketForm {
  implicit val writer: OWrites[TicketForm] = Json.writes[TicketForm]
  import FormHelper._

  val form: Form[TicketForm] = Form(
    mapping(
      "seatId" -> longNumber,
      "typeTicket" ->  Forms.of[TicketType]
    )(TicketForm.apply)(TicketForm.unapply)
  )
}