package controllers

import forms.RoomForm
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.RoomService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class RoomController @Inject()(roomService: RoomService, cc: ControllerComponents)(implicit context: ExecutionContext) extends AbstractController(cc) {

  def add: Action[AnyContent] = Action.async { implicit request =>
    RoomForm.form.bindFromRequest.fold(
      _ =>
        Future.successful(BadRequest),

      data => {
        roomService.create(data).map { room =>
          Ok(Json.toJson(room))
        }
      }
    )
  }

  def list: Action[AnyContent] = Action.async { implicit request =>
    roomService.list().map { rooms =>
      Ok(Json.toJson(rooms))
    }
  }

  def find(id: Long): Action[AnyContent] = Action.async { implicit request =>
    roomService.find(id).map {
      case Right(room) =>
        Ok(Json.toJson(room))

      case Left(error) =>
        NotFound(error.error)
    }
  }
}
