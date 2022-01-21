package models
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText, seq}
import play.api.libs.json.{Json, OWrites}
import utils.FormHelper.{fulfilConditions, isSurnameCorrect}


case class ReservationData(id: Long, name: String, surname: String, tickets: Seq[Ticket])

object ReservationData {
  implicit val writer: OWrites[ReservationData] = Json.writes[ReservationData]

  val form: Form[ReservationData] = Form(
    mapping(
      "id" -> longNumber,"name" -> nonEmptyText(3).verifying(name => fulfilConditions(name)),
      "surname" ->  nonEmptyText(3).verifying(isSurnameCorrect),
      "tickets" ->  seq(Ticket.form.mapping)
    )(ReservationData.apply)(ReservationData.unapply)
  )
}


