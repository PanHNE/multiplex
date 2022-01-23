package services

import daos.ReservationDAO
import forms.ReservationForm
import models.{ReservationData, Seat, Ticket}
import services.Service.{ProblemWithService, SeatIsTaken}

import javax.inject.Inject
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class ReservationServiceImpl @Inject()(
  reservationDAO: ReservationDAO,
  seatService: SeatService,
  ticketService: TicketService

)(implicit context: ExecutionContext) extends ReservationService {

  override def create(reservationForm: ReservationForm): Future[Either[ServiceError, ReservationData]] = {
    for {
      screeningSeats <- seatService.findSeatsByScreeningId(reservationForm.screeningId, None)
      reservationSeats <- seatService.findSeats(reservationForm.tickets.map(_.seatId))
    } yield (screeningSeats, reservationSeats) match {
      case (Right(sSeats), Right(rSeats)) =>
        if (Seat.checkSeats(rSeats, sSeats)) createReservationAndTicket(reservationForm, rSeats)
        else Future.successful(Left(ProblemWithService("Problem with create reservation")))

      case (Left(error), _) => Future.successful(Left(error))

      case (_, Left(error)) => Future.successful(Left(error))

      case _ =>
        Future.successful(Left(ProblemWithService("Problem with reservation service")))
    }
  }.flatten

  private def createReservationAndTicket(reservationForm: ReservationForm, seats: Seq[Seat]): Future[Either[ServiceError, ReservationData]] = {
    (for {
      reservation <- reservationDAO.insert(reservationForm)
    } yield reservation.id match {
      case Some(r) => for {
        tickets <- ticketService.create(reservationForm.tickets, r)
        isChanged <- seatService.changeAvailable(seats, available = false)
      } yield {
        if (isChanged) Right(ReservationData(r, reservation.name, reservation.surname, tickets))
        else Left(ProblemWithService("Problem with changing available seats"))
      }
      case None => Future.successful(Left(ProblemWithService("Problem with create reservation")))
    }).flatten
  }
}
