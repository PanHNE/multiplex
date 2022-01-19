package services

import forms.ScreeningForm
import models.Screening

import scala.concurrent.Future

trait ScreeningService {
  def count(): Future[Int]
  def create(screening: ScreeningForm): Future[Unit]
  def create(screenings: Seq[ScreeningForm]): Future[Unit]
  def find(id: Long): Future[Either[String, Screening]]
  def list(): Future[Seq[Screening]]
}
