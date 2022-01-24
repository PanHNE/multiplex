package services

import daos.ReservationDAO
import forms.ReservationForm
import models.{ReservationData, Screening, Seat}
import services.Service.{ProblemWithService, SeatIsTaken, ToLateForReservation}

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReservationServiceImpl @Inject()(
  reservationDAO: ReservationDAO,
  seatService: SeatService,
  ticketService: TicketService,
  screeningService: ScreeningService
)(implicit context: ExecutionContext) extends ReservationService {

  override def create(reservationForm: ReservationForm): Future[Either[ServiceError, ReservationData]] = {
    for {
      screening <- screeningService.find(reservationForm.screeningId)
      screeningSeats <- seatService.findSeatsByScreeningId(reservationForm.screeningId, None)
      reservationSeats <- seatService.findSeats(reservationForm.tickets.map(_.seatId))
    } yield (screening, screeningSeats, reservationSeats) match {
      case (Right(screening), Right(sSeats), Right(rSeats)) =>
        checkTime(screening) match {
          case Left(error) =>
            Future.successful(Left(error))

          case Right(s) =>
            createReservationAndTicket(reservationForm, rSeats, sSeats, s)
        }

      case (Left(error), _, _) =>
        Future.successful(Left(error))

      case (_, Left(error), _) =>
        Future.successful(Left(error))

      case (_, _, Left(error)) =>
        Future.successful(Left(error))

      case _ =>
        Future.successful(Left(ProblemWithService("Problem with reservation service")))
    }
  }.flatten

  private def checkTime(screening: Screening): Either[ServiceError, Screening] = {
    val timeNow = LocalDateTime.now()
    val reservationTime = screening.dateAndTime.minusMinutes(15)

    if (timeNow.isBefore(reservationTime)) Right(screening)
    else Left(ToLateForReservation("Reservation time is up left"))
  }

  private def createReservationAndTicket(reservationForm: ReservationForm, rSeats: Seq[Seat], sSeat: Seq[Seat], screening: Screening): Future[Either[ServiceError, ReservationData]] = {
    if (Seat.checkSeats(rSeats, sSeat)) {
      (for {
        reservation <- reservationDAO.insert(reservationForm)
      } yield reservation.id match {
        case Some(r) => for {
          tickets <- ticketService.create(reservationForm.tickets, r)
          isChanged <- seatService.changeAvailable(rSeats, available = false)
        } yield {
          if (isChanged) Right(ReservationData(r, reservation.name, reservation.surname, tickets, screening))
          else Left(ProblemWithService("Problem with changing available seats"))
        }
        case None => Future.successful(Left(ProblemWithService("Problem with create reservation")))
      }).flatten
    } else {
        Future.successful(Left(SeatIsTaken("Seats are taken")))
      }
    }

}
