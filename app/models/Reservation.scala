package models

import play.api.libs.json.{Json, OFormat}

case class Reservation(id: Option[Long], name: String, surname: String)

object Reservation {
  implicit val format: OFormat[Reservation] = Json.format[Reservation]
}
