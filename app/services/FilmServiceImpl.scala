package services

import daos.FilmDAO
import forms.FilmForm
import models.Film
import services.Service.NotFound

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FilmServiceImpl @Inject()(filmDAO: FilmDAO)(implicit context: ExecutionContext) extends FilmService {

  override def count(): Future[Int] =
    filmDAO.count()

  override def create(film: FilmForm): Future[Film] =
    filmDAO.insert(film)

  override def create(films: Seq[FilmForm]): Future[Unit] =
    Future(films.foreach(film => create(film)))

  override def find(id: Long): Future[Either[NotFound, Film]] =
    filmDAO.find(id).map {
      case Some(film) => Right(film)
      case None => Left(NotFound(s"Not found film with id $id"))
    }

  override def list(): Future[Seq[Film]] =
    filmDAO.list()

  override def findByIds(ids: Seq[Long]): Future[Seq[Film]] =
    filmDAO.list(ids)
}
