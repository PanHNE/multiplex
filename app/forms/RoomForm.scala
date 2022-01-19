package forms

import play.api.data.Form
import play.api.data.Forms.{mapping, number}
import play.api.libs.json.{Json, OWrites}

case class RoomForm(numberOfRows: Int, numberOfSeatsInRow: Int)

object RoomForm {
  implicit val writer: OWrites[RoomForm] = Json.writes[RoomForm]

  val form: Form[RoomForm] = Form(
    mapping(
      "numberOfRows" -> number(10),
      "numberOfSeatsInRow" -> number(10)
    )(RoomForm.apply)(RoomForm.unapply)
  )

}
