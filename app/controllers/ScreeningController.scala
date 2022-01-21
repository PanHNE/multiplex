package controllers

import forms.{ScreeningByDaysAndHours, ScreeningForm}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{ScreeningService, Service}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ScreeningController @Inject()(screeningService: ScreeningService, cc: ControllerComponents)(implicit context: ExecutionContext) extends AbstractController(cc) {

  def add: Action[AnyContent] = Action.async { implicit request =>
    ScreeningForm.form.bindFromRequest.fold(
      _ =>
        Future.successful(BadRequest),

      data => {
        screeningService.create(data).map {
          case Right(screening) =>
            Ok(Json.toJson(screening))

          case Left(error) => error match {
            case Service.NotFound(error) =>
              NotFound(error)

            case Service.NotAddNewElement(error) =>
              BadRequest(error)
          }
        }
      }
    )
  }

  def list: Action[AnyContent] = Action.async { implicit request =>
    screeningService.list().map { screenings =>
      Ok(Json.toJson(screenings))
    }
  }

  def find(id: Long): Action[AnyContent] = Action.async { implicit request =>
    screeningService.find(id).map {
      case Right(screening) =>
        Ok(Json.toJson(screening))

      case Left(error) =>
        NotFound(error.message)
    }
  }

  def findByDaysAndHours: Action[AnyContent] = Action.async { implicit request =>
    ScreeningByDaysAndHours.form.bindFromRequest.fold(
      _ =>
        Future.successful(BadRequest),

      data =>
        screeningService.listOfFilms(data).map { ps =>
          Ok(Json.toJson(ps))
        }
    )
  }

  def infoAboutSeatRoomAndScreening(screeningId: Long): Action[AnyContent] = Action.async { implicit request =>
    screeningService.allInfo(screeningId).map {
      case Right(info) =>
        Ok(Json.toJson(info))

      case Left(error) =>
        NotFound(error.message)
      }
  }
}
