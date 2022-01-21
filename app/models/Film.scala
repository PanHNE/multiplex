package models

import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText, number}
import play.api.libs.json.{Json, OWrites}

case class Film(id: Long, title: String, length: Int)

object Film {
  implicit val writer: OWrites[Film] = Json.writes[Film]

  val form: Form[Film] = Form(
    mapping(
      "id" -> longNumber,
      "title" -> nonEmptyText,
      "length" ->  number
    )(Film.apply)(Film.unapply)
  )
}
