package models

import play.api.libs.json.{Json, OFormat}

case class Film(id: Long, title: String, length: Int)

object Film {
  implicit val filmFormat: OFormat[Film] = Json.format[Film]
}
