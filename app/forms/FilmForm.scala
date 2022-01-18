package forms

import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}
import play.api.libs.json.{Json, OWrites}

case class FilmForm(title: String, length: Int)

object FilmForm {
  implicit val writer: OWrites[FilmForm] = Json.writes[FilmForm]

  val form: Form[FilmForm] = Form(
    mapping(
      "title" -> nonEmptyText(1),
      "length" -> number(1)
    )(FilmForm.apply)(FilmForm.unapply)
  )

}
