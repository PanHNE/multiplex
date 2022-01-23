package controllers

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.SeatService

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class SeatController  @Inject()(seatService: SeatService, cc: ControllerComponents)(implicit context: ExecutionContext) extends AbstractController(cc) {

  def availableSets(screeningId: Long, available: Boolean) = Action.async { implicit request =>
    seatService.findSeatsByScreeningId(screeningId, Some(available)).map {
      case Left(error) =>
        NotFound(error.message)

      case Right(seats) =>
        Ok(Json.toJson(seats))
    }
  }
}
