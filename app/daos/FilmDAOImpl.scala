package daos

import forms.FilmForm
import models.Film
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FilmDAOImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends FilmDAO {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class FilmTable(tag: Tag) extends Table[Film](tag, "films") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def length = column[Int]("length")

    def * = (id, title, length) <> ((Film.apply _).tupled, Film.unapply)
  }

  private val films = TableQuery[FilmTable]

  override def count(): Future[Int] = db.run {
    films.length.result
  }

  override def insert(film: FilmForm): Future[Film] = db.run {
    (films.map( f => (f.title, f.length))
      returning films.map(_.id)
      into ((data, id) => Film(id, data._1, data._2))
      ) += (film.title, film.length)
  }

  override def find(id: Long): Future[Option[Film]] = db.run {
    films.filter(_.id === id).result.headOption
  }

  override def list(): Future[Seq[Film]] = db.run {
    films.result
  }

  override def list(ids: Seq[Long]): Future[Seq[Film]] = db.run {
    films.filter(_.id inSet ids).result
  }
}
