package forms

import play.api.data.Form
import play.api.data.Forms.{localDateTime, longNumber, mapping}
import play.api.libs.json.{Json, OWrites}

import java.time.LocalDateTime

case class ScreeningForm(roomId: Long, filmId: Long, dateAndTime: LocalDateTime)

object ScreeningForm {
  implicit val writer: OWrites[ScreeningForm] = Json.writes[ScreeningForm]

  val form: Form[ScreeningForm] = Form(
    mapping(
      "roomId" -> longNumber,
      "filmId" -> longNumber,
      "dateAndTime" -> localDateTime("yyyy-MM-dd HH:mm")
    )(ScreeningForm.apply)(ScreeningForm.unapply)
  )
}