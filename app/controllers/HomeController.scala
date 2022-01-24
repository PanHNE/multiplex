package controllers

import play.api.libs.json.Json

import javax.inject._
import play.api.mvc._


@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index: Action[AnyContent] = Action {
    Ok(Json.toJson("Everything is OK!"))
  }

}
