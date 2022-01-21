package models

import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, number}
import play.api.libs.json.{Json, OWrites}

case class Room(id: Long, numberOfRows: Int, numberOfSeatsInRow: Int)

object Room {
  implicit val writer: OWrites[Room] = Json.writes[Room]

  val form: Form[Room] = Form(
    mapping(
      "id" -> longNumber,
      "numberOfRows" -> number,
      "numberOfSeatsInRow" ->  number
    )(Room.apply)(Room.unapply)
  )
}
