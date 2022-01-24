package data

import models._
import utils.TicketType

import java.time.LocalDateTime

object Data {

  val films: Seq[Film] = Seq(
    Film(1, "Inception", 148),
    Film(2, "Sherlock Holmes", 128)
  )

  val rooms: Seq[Room] = Seq(
    Room(1, 10, 10)
  )

  val screenings: Seq[Screening] = Seq(
    Screening(Some(1), 1, 1, LocalDateTime.of(2022, 2, 1, 14, 30)),
    Screening(Some(2), 1, 2, LocalDateTime.of(2022, 2, 1, 18, 0))
  )

  val seats: Seq[Seq[Seat]] = Seq(createSeats(1), createSeats(2))

  private def createSeats(screeningId: Long): Seq[Seat] = for {
    row <- 1 to 4
    numberOfSeat <- 1 to 8
  } yield {
    val id = numberOfSeat + ((row - 1) * 10)
    Seat(Some(id), screeningId, row, numberOfSeat, available = true)
  }

  val reservations: Seq[Reservation] = Seq(
    Reservation(Some(1), "Paweł", "Mądry"),
    Reservation(Some(2), "Julia", "Kowalska"),
    Reservation(Some(3), "Tonny", "Johnex"),
  )

  val kidTickets: Seq[Ticket] = Seq(
    Ticket(Some(1), 1, 1, TicketType.Child),
    Ticket(Some(2), 1, 1, TicketType.Child),
    Ticket(Some(3), 1, 1, TicketType.Child)
  )

  val differentTickets: Seq[Ticket] = Seq(
    Ticket(Some(1), 1, 1, TicketType.Adult),
    Ticket(Some(2), 1, 1, TicketType.Student),
    Ticket(Some(3), 1, 1, TicketType.Child)
  )

  val filmScreeningData: Seq[FilmScreeningData] = for {
    film <- films
    screening <- screenings
  } yield FilmScreeningData(film, screening)

  val roomSeatScreeningData = RoomSeatScreeningData(rooms(0), screenings(0), seats(0))

}
