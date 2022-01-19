package controllers

import forms.FilmForm
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.FilmService
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FilmController @Inject()(filmService: FilmService, cc: ControllerComponents)(implicit context: ExecutionContext) extends AbstractController(cc) {

  def add: Action[AnyContent] = Action.async { implicit request =>
    FilmForm.form.bindFromRequest.fold(
      _ =>
        Future.successful(BadRequest),

      data => {
        filmService.create(data).map { user =>
          Ok(Json.toJson(user))
        }
      }
    )
  }

  def list: Action[AnyContent] = Action.async { implicit request =>
    filmService.list().map { films =>
      Ok(Json.toJson(films))
    }
  }

  def find(id: Long): Action[AnyContent] = Action.async { implicit request =>
    filmService.find(id).map {
      case Right(film) =>
        Ok(Json.toJson(film))

      case Left(value) =>
        NotFound(value.error)
    }
  }
}
