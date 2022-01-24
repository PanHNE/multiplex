package daos

import forms.ReservationForm
import models.Reservation
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReservationDAOImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends ReservationDAO {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class ReservationTable(tag: Tag) extends Table[Reservation](tag, "reservations") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def surname = column[String]("surname")

    def * = (id.?, name, surname) <> ((Reservation.apply _).tupled, Reservation.unapply)
  }

  private val reservations = TableQuery[ReservationTable]

  override def insert(reservation: ReservationForm): Future[Reservation] = db.run {
    ( reservations.map( r => (r.name, r.surname))
      returning reservations.map(_.id)
      into ((res, id) => Reservation(Some(id), res._1, res._2))
    ) += (reservation.name, reservation.surname)
  }
}
