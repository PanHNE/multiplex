package controllers

import forms.ReservationForm
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.{ReservationService, Service}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReservationController  @Inject()(reservationService: ReservationService, cc: ControllerComponents)(implicit context: ExecutionContext) extends AbstractController(cc) {

  def reservation = Action.async { implicit request =>
    ReservationForm.form.bindFromRequest.fold(
      _ =>
        Future.successful(BadRequest),

      data =>
        reservationService.create(data).map {
          case Right(reservation) =>
            Ok(Json.toJson(reservation))

          case Left(value) => value match {
            case Service.NotFound(message) =>
              NotFound(message)

            case Service.NotAddNewElement(message) =>
              InternalServerError(message)

            case Service.SeatIsTaken(messages) =>
              BadRequest(messages)

            case Service.ToLateForReservation(messages) =>
              BadRequest(messages)

            case Service.ProblemWithService(message) =>
              InternalServerError(message)
          }
        }
    )
  }
}
