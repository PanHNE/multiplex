package services

import forms.ReservationForm
import models.ReservationData

import scala.concurrent.Future

trait ReservationService {
  def create(reservationForm: ReservationForm): Future[Either[ServiceError, ReservationData]]
}
