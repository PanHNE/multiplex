package services

import models.Seat
import services.Service.NotFound

import scala.concurrent.Future

trait SeatService {
  def count(): Future[Int]
  def changeAvailable(seats: Seq[Seat], available: Boolean): Future[Boolean]
  def findSeatsByScreeningId(screeningId: Long, available: Option[Boolean]): Future[Either[NotFound, Seq[Seat]]]
  def create(seat: Seq[Seat]): Future[Unit]
  def find(seatId: Long): Future[Either[NotFound, Seat]]
  def findSeats(seatIds: Seq[Long]): Future[Either[NotFound, Seq[Seat]]]
}
