package daos

import forms.ScreeningForm
import models.Screening

import scala.concurrent.Future

trait ScreeningDAO {
  def count(): Future[Int]
  def insert(screening: Screening): Future[Unit]
  def find(id: Long): Future[Option[Screening]]
  def list(): Future[Seq[Screening]]
}
