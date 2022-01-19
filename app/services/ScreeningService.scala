package services

import forms.ScreeningForm
import models.Screening
import services.Service.NotFound

import scala.concurrent.Future

trait ScreeningService {
  def count(): Future[Int]
  def create(screening: ScreeningForm): Future[Unit]
  def create(screenings: Seq[ScreeningForm]): Future[Unit]
  def find(id: Long): Future[Either[NotFound, Screening]]
  def list(): Future[Seq[Screening]]
}
