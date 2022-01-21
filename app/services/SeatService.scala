package services

import models.Seat
import services.Service.NotFound

import scala.concurrent.Future

trait SeatService {
  def count(): Future[Int]
  def available(id: Long, available: Boolean): Future[Either[NotFound, Seq[Seat]]]
  def create(seat: Seq[Seat]): Future[Unit]
}
