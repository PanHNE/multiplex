package models

import play.api.data.Form
import play.api.data.Forms.{localDateTime, longNumber, mapping, optional}
import play.api.libs.json.{Json, OWrites}

import java.time.LocalDateTime

case class Screening(id: Option[Long], roomId: Long, filmId: Long, dateAndTime: LocalDateTime)


object Screening {
  implicit val writer: OWrites[Screening] = Json.writes[Screening]

  val form: Form[Screening] = Form(
    mapping(
      "id" -> optional(longNumber),
      "roomId" -> longNumber,
      "filmId" ->  longNumber,
      "dateAndTime" ->  localDateTime("yyyy-MM-dd HH:mm")
    )(Screening.apply)(Screening.unapply)
  )
}