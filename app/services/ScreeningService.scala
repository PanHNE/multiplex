package services

import forms.{ScreeningByDaysAndHours, ScreeningForm}
import models.{FilmScreeningData, RoomSeatScreeningData, Screening}
import services.Service.NotFound

import scala.concurrent.Future

trait ScreeningService {
  def count(): Future[Int]
  def create(screening: ScreeningForm): Future[Either[ServiceError, Screening]]
  def create(screenings: Seq[ScreeningForm]): Future[Unit]
  def find(id: Long): Future[Either[NotFound, Screening]]
  def list(): Future[Seq[Screening]]
  def list(screeningByDateAndTimeForm: ScreeningByDaysAndHours): Future[Seq[Screening]]
  def listOfFilms(date: ScreeningByDaysAndHours): Future[Seq[FilmScreeningData]]
  def allInfo(screeningId: Long, available: Option[Boolean]): Future[Either[NotFound, RoomSeatScreeningData]]
}
