package services

import daos.ReservationDAO
import forms.ReservationForm
import models.{ReservationData, Seat}
import services.Service.{ProblemWithService, SeatIsTaken}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReservationServiceImpl @Inject()(
  reservationDAO: ReservationDAO,
  seatService: SeatService,
  ticketService: TicketService

)(implicit context: ExecutionContext) extends ReservationService {

  override def create(reservationForm: ReservationForm): Future[Either[ServiceError, ReservationData]] = {
    seatService.findSeats(reservationForm.tickets.map(_.seatId)).flatMap {
      case Left(error) =>
        Future.successful(Left(error))

      case Right(seats) if seats.exists(_.available == false) =>
        Future.successful(Left(SeatIsTaken("One of seats is taken")))

      case Right(seats) if seats.forall(_.available) =>
        createReservationAndTicket(reservationForm, seats)

      case _ =>
        Future.successful(Left(ProblemWithService("Problem with create reservation")))
    }
  }

  private def createReservationAndTicket(reservationForm: ReservationForm, seats: Seq[Seat]): Future[Either[ServiceError, ReservationData]] = {
    (for {
      reservation <- reservationDAO.insert(reservationForm)
    } yield reservation.id match {
      case Some(r) => for {
        tickets <- ticketService.create(reservationForm.tickets, r)
      } yield {
        Right(ReservationData(r, reservation.name, reservation.surname, tickets))
      }
      case None => Future.successful(Left(ProblemWithService("Problem with create reservation")))
    }).flatten
  }

}
