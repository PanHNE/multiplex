package daos

import models.Ticket
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import utils.Helpers.ticketMatcher
import utils.TicketType.TicketType

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TicketDAOImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends TicketDAO {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class TicketTable(tag: Tag) extends Table[Ticket](tag, "tickets") {

    implicit val ticketTypeToColumnType = MappedColumnType.base[TicketType, String](
      ldt => ldt.toString,
      t => ticketMatcher(t)
    )

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def reservationId = column[Long]("reservationId")
    def seatId = column[Long]("seatId")
    def ticketType = column[TicketType]("ticketType")

    def * = (id.?, reservationId, seatId, ticketType) <> ((Ticket.apply _).tupled, Ticket.unapply)
  }

  private val tickets = TableQuery[TicketTable]

  override def insert(ticket: Ticket): Future[Unit] = db.run {
    tickets += ticket
  }.map( _ => ())
}
