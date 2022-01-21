package daos

import models.Ticket

import scala.concurrent.Future

trait TicketDAO {
  def insert(ticket: Ticket): Future[Unit]
}
