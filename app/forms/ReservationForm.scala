package forms

import play.api.data.Form
import play.api.data.Forms.{list, longNumber, mapping, nonEmptyText, seq}
import play.api.libs.json.{Json, OWrites}
import utils.FormHelper.{fulfilConditions, isSurnameCorrect}

case class ReservationForm(name: String, surname: String, tickets: Seq[TicketForm])

object ReservationForm {
  implicit val writer: OWrites[ReservationForm] = Json.writes[ReservationForm]

  val form: Form[ReservationForm] = Form(
    mapping(
      "name" -> nonEmptyText(3).verifying(name => fulfilConditions(name)),
      "surname" ->  nonEmptyText(3).verifying(isSurnameCorrect),
      "tickets" ->  seq(TicketForm.form.mapping)
    )(ReservationForm.apply)(ReservationForm.unapply)
  )

}