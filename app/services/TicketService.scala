package services

import forms.TicketForm
import models.Ticket

import scala.concurrent.Future

trait TicketService {
  def create(tickets: Seq[TicketForm], reservationId: Long):  Future[Seq[Ticket]]
}
