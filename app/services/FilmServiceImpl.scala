package services

import daos.FilmDAO
import forms.FilmForm
import models.Film

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FilmServiceImpl @Inject()(filmDAO: FilmDAO)(implicit context: ExecutionContext) extends FilmService {

  override def count(): Future[Int] =
    filmDAO.count()

  override def create(film: FilmForm): Future[Film] =
    filmDAO.insert(film)

  override def create(films: Seq[FilmForm]): Future[Unit] =
    Future(films.foreach(film => create(film)))

  override def find(id: Long): Future[Option[Film]] =
    filmDAO.find(id)

  override def list(): Future[Seq[Film]] =
    filmDAO.list()

}
