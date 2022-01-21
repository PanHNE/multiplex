package daos

import models.Seat
import scala.concurrent.Future

trait SeatDAO {
  def count(): Future[Int]
  def available(screeningId: Long, available: Boolean): Future[Seq[Seat]]
  def create(seat: Seq[Seat]): Future[Unit]
  def find(seatId: Long): Future[Option[Seat]]
  def findSeats(seatIds: Seq[Long]): Future[Seq[Seat]]
}
