package services

import daos.SeatDAO
import models.Seat
import services.Service.NotFound

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SeatServiceImpl @Inject()(seatDAO: SeatDAO)(implicit context: ExecutionContext) extends SeatService {

  override def count(): Future[Int] =
    seatDAO.count()

  override def available(id: Long, available: Boolean): Future[Either[NotFound, Seq[Seat]]] =
    seatDAO.available(id, available).map {
      case seat if seat.nonEmpty => Right(seat)
      case _ => Left(NotFound("Not found available seat"))
    }

  override def create(seats: Seq[Seat]): Future[Unit] =
    seatDAO.create(seats)

  override def find(seatId: Long): Future[Either[NotFound, Seat]] =
    seatDAO.find(seatId).map {
      case Some(seat) => Right(seat)
      case None => Left(NotFound("Seat with id not found"))
    }

  override def findSeats(seatIds: Seq[Long]): Future[Either[NotFound, Seq[Seat]]] =
    seatDAO.findSeats(seatIds).map {
      case seat if seat.nonEmpty => Right(seat)
      case _ => Left(NotFound("Seats not found"))
    }
}
