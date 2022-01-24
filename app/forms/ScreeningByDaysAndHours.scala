package forms

import play.api.data.Form
import play.api.data.Forms.{list, localDate, localTime, mapping}
import play.api.libs.json.{Json, OWrites}
import utils.Helpers

import java.time.{LocalDate, LocalTime}

case class ScreeningByDaysAndHours(days: List[LocalDate], hours: List[LocalTime])

object ScreeningByDaysAndHours {
  implicit val writer: OWrites[ScreeningByDaysAndHours] = Json.writes[ScreeningByDaysAndHours]

  val form: Form[ScreeningByDaysAndHours] = Form(
    mapping(
      "days" ->  list(localDate("yyyy-MM-dd")),
      "hours" ->  list(localTime("HH:mm")).verifying(hours => verifyHours(hours))
    )(ScreeningByDaysAndHours.apply)(ScreeningByDaysAndHours.unapply)
  )

  def verifyHours(hours: List[LocalTime]): Boolean =
    hours.forall(hours => Helpers.hoursScreening.contains(hours))
}