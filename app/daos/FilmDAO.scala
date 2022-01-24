package daos

import forms.FilmForm
import models.Film

import scala.concurrent.Future

trait FilmDAO {
  def count(): Future[Int]
  def insert(film: FilmForm): Future[Film]
  def find(id: Long): Future[Option[Film]]
  def list(): Future[Seq[Film]]
  def list(ids: Seq[Long]): Future[Seq[Film]]
}
