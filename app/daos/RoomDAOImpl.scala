package daos

import forms.{FilmForm, RoomForm}
import models.{Film, Room}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class RoomDAOImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends RoomDAO {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class RoomTable(tag: Tag) extends Table[Room](tag, "rooms") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def numberOfRows = column[Int]("numberOfRows")
    def numberOfSeatsInRow = column[Int]("numberOfSeatsInRow")

    def * = (id, numberOfRows, numberOfSeatsInRow) <> ((Room.apply _).tupled, Room.unapply)
  }

  private val rooms = TableQuery[RoomTable]

  override def count(): Future[Int] = db.run {
    rooms.length.result
  }

  override def insert(room: RoomForm): Future[Room] = db.run {
    (rooms.map( r => (r.numberOfRows, r.numberOfSeatsInRow))
      returning rooms.map(_.id)
      into ((data, id) => Room(id, data._1, data._2))
      ) += (room.numberOfRows, room.numberOfSeatsInRow)
  }

  override def find(id: Long): Future[Option[Room]] = db.run {
    rooms.filter(_.id === id).result.headOption
  }

  override def list(): Future[Seq[Room]] = db.run {
    rooms.result
  }
}
