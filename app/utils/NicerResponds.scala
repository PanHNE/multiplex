package utils

import models.{FilmScreeningData, RoomSeatScreeningData, Screening, Seat, Ticket}
import utils.Helpers.ticketPrice

import java.time.LocalDateTime

object NicerResponds {

  def amountToPay(tickets: Seq[Ticket]): Double = {
    tickets.foldLeft(0){ (f, n) =>
      f + ticketPrice(n.ticketType)
    } / 100
  }

  def nicerDataAndTime(localDateTime: LocalDateTime): String = {
    s"${localDateTime.toLocalDate}, ${localDateTime.toLocalTime}"
  }

  def respondWithAmountToPayAndReservationTime(tickets: Seq[Ticket], screening: Screening): String = {
    val dataAndTIme = nicerDataAndTime(screening.dateAndTime.minusMinutes(15))
    s"Amount to pay: ${amountToPay(tickets)}. Reservation expiration time: $dataAndTIme"
  }

  def respondWithListFilms(data: Seq[FilmScreeningData]): Seq[String] = {
    data.map { d =>
      s"${d.film.title} : ${nicerDataAndTime(d.screening.dateAndTime)}"
    }
  }

  def nicerStringPlace(seats: Seq[Seat]): Seq[String] = {
    seats.map( s => s"[${s.row}-${s.numberSeat}]")
  }

  def respondWithAvailableSeatsAndRoom(data: RoomSeatScreeningData): String = {
    s"Number room ${data.room.id}. Available seats: [${nicerStringPlace(data.availableSeat)}]"
  }

}
