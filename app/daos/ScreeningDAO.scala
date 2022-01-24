package daos

import models.Screening
import services.ServiceError

import scala.concurrent.Future

trait ScreeningDAO {
  def count(): Future[Int]
  def insert(screening: Screening): Future[Either[ServiceError, Screening]]
  def find(id: Long): Future[Option[Screening]]
  def list(): Future[Seq[Screening]]
}
