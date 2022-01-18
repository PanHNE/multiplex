package services

import forms.FilmForm
import models.Film

import scala.concurrent.Future

trait FilmService {
  def count(): Future[Int]
  def create(film: FilmForm): Future[Film]
  def create(films: Seq[FilmForm]): Future[Unit]
  def find(id: Long): Future[Option[Film]]
  def list(): Future[Seq[Film]]
}
