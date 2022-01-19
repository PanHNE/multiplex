package controllers

import forms.ScreeningForm
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.ScreeningService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ScreeningController @Inject()(screeningService: ScreeningService, cc: ControllerComponents)(implicit context: ExecutionContext) extends AbstractController(cc) {

  def add: Action[AnyContent] = Action.async { implicit request =>
    ScreeningForm.form.bindFromRequest.fold(
      _ =>
        Future.successful(BadRequest),

      data => {
        screeningService.create(data).map { _ =>
          Ok(Json.toJson("Add new screening"))
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
      case Left(value) =>
        NotFound(value)

      case Right(screening) =>
        Ok(Json.toJson(screening))
    }
  }
}