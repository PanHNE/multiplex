package services

import forms.FilmForm
import models.Film
import services.Service.NotFound

import scala.concurrent.Future

trait FilmService {
  def count(): Future[Int]
  def create(film: FilmForm): Future[Film]
  def create(films: Seq[FilmForm]): Future[Unit]
  def find(id: Long): Future[Either[NotFound, Film]]
  def list(): Future[Seq[Film]]
  def findByIds(ids: Seq[Long]): Future[Seq[Film]]
}
