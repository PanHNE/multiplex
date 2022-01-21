package models

import play.api.libs.json.{Json, OFormat}

import java.time.LocalDateTime

case class Screening(id: Option[Long], roomId: Long, filmId: Long, dateAndTime: LocalDateTime)

object Screening {
  implicit val format: OFormat[Screening] = Json.format[Screening]
}
