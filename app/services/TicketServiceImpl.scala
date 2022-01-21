package services

import daos.TicketDAO
import forms.TicketForm
import models.Ticket

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TicketServiceImpl @Inject()(ticketDAO: TicketDAO)(implicit context: ExecutionContext) extends TicketService {

  override def create(tickets: Seq[TicketForm], reservationId: Long):  Future[Seq[Ticket]] =
    Future.sequence(tickets.map { ticket =>
      val d = Ticket(None, reservationId, ticket.seatId, ticket.typeTicket)
      ticketDAO.insert(d)
      Future(d)
    })
}
