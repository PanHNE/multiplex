package daos

import forms.ReservationForm
import models.Reservation

import scala.concurrent.Future

trait ReservationDAO {
  def insert(reservation: ReservationForm): Future[Reservation]
}
