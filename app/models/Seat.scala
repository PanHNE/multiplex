package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Json, OWrites}

case class Seat(id: Option[Long], screeningId: Long, row: Int, numberSeat: Int, available: Boolean) {

  def this(seat: Seat, available: Boolean) {
    this(seat.id, seat.screeningId, seat.row, seat.numberSeat, available)
  }
}

object Seat {
  implicit val writer: OWrites[Seat] = Json.writes[Seat]

  val form: Form[Seat] = Form(
    mapping(
      "id" -> optional(longNumber),
      "screeningId" -> longNumber,
      "row" -> number,
      "numberSeat" -> number,
      "available" ->  boolean
    )(Seat.apply)(Seat.unapply)
  )

  def checkSeats(reservationSeats: Seq[Seat], screeningSeats: Seq[Seat]): Boolean = {
    val (available, taken) = screeningSeats.partition(_.available)

    if (reservationSeats.forall(s => available.map(_.id).contains(s.id))) {
      val (newTaken, newAvailable) = available.partition(s => reservationSeats.contains(s))
      val newSeatLayout = taken ++ changeAvailable(newTaken, available = false) ++ newAvailable
      checkLayoutSeats(newSeatLayout)
    } else
      false
  }

  def changeAvailable(seat: Seq[Seat], available: Boolean): Seq[Seat] = {
    seat.map( s => new Seat(s, available))
  }

  def seqSeatToMap(seats: Seq[Seat]): Map[Int, Seq[Seat]] =
    seats.foldLeft(Map[Int, Seq[Seat]]()) { (f, n) =>
      f.get(n.row) match {
        case Some(value) =>
          f + (n.row -> (value :+ n))
        case None =>
          f + (n.row -> Seq(n))
      }
    }

  def checkLayoutSeats(seats: Seq[Seat]): Boolean = {
    val mapSeats = seqSeatToMap(seats)

    mapSeats.forall { row =>
      val (available, taken) = row._2.partition(_.available)
      checkLayoutRow(available, taken, seats.length)
    }
  }

  def checkLayoutRow(available: Seq[Seat], taken: Seq[Seat], lastSeat: Int): Boolean = {
    available.map(_.numberSeat).forall {
      case x: Int if x == 1 => !taken.exists(_.numberSeat == 2)
      case x: Int if x == lastSeat => !taken.exists(_.numberSeat == lastSeat - 1)
      case x: Int => !taken.exists(ns =>  ns.numberSeat == x - 1 && ns.numberSeat == x + 1)
    }
  }
}