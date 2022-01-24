package daos

import models.Seat
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SeatDAOImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends SeatDAO {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class SeatTable(tag: Tag) extends Table[Seat](tag, "seats") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def screeningId = column[Long]("screeningId")
    def row = column[Int]("row")
    def numberSeat = column[Int]("numberSeat")
    def available = column[Boolean]("available")

    def * = (id.?, screeningId, row, numberSeat, available) <> ((Seat.apply _).tupled, Seat.unapply)
  }

  private val seats = TableQuery[SeatTable]

  override def count(): Future[Int] = db.run {
    seats.length.result
  }

  override def update(seqSeat: Seq[Seat]): Future[Seq[Int]] = db.run {
    DBIO.sequence( seqSeat.map { seat =>
      seats.filter(_.id === seat.id).update(seat)
    })
  }

  override def available(screeningId: Long, available: Boolean): Future[Seq[Seat]] = db.run {
    seats.filter(seat => seat.screeningId === screeningId && seat.available === available).result
  }

  override def allSeatFromScreening(screeningId: Long): Future[Seq[Seat]] = db.run {
    seats.filter(seat => seat.screeningId === screeningId).result
  }

  override def create(seat: Seq[Seat]): Future[Unit] =
    db.run(seats ++= seat).map( _ => ())

  override def find(seatId: Long): Future[Option[Seat]] =
    db.run( seats.filter(_.id === seatId).result.headOption)

  override def findSeats(seatIds: Seq[Long]): Future[Seq[Seat]] =
    db.run( seats.filter(_.id inSet seatIds).result)
}
