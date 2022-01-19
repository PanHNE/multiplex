package daos

import forms.ScreeningForm
import models.Screening
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ScreeningDAOImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends ScreeningDAO {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class ScreeningTable(tag: Tag) extends Table[Screening](tag, "screenings") {
    implicit val localDateTimeColumnType = MappedColumnType.base[LocalDateTime, Timestamp](
      ldt => Timestamp.valueOf(ldt),
      t => t.toLocalDateTime
    )

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def roomId = column[Long]("roomId")
    def filmId = column[Long]("filmId")
    def dateAndTime = column[LocalDateTime]("dateAndTime")

    def * = (id.?, roomId, filmId, dateAndTime) <> ((Screening.apply _).tupled, Screening.unapply)
  }

  private val screenings = TableQuery[ScreeningTable]

  override def count(): Future[Int] = db.run {
    screenings.length.result
  }

  override def insert(screening: Screening): Future[Unit] =
    db.run(screenings += screening).map( _ => () )

  override def find(id: Long): Future[Option[Screening]] = db.run {
    screenings.filter(_.id === id).result.headOption
  }

  override def list(): Future[Seq[Screening]] = db.run {
    screenings.result
  }
}
