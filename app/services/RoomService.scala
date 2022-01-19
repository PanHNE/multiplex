package services

import forms.RoomForm
import models.Room

import scala.concurrent.Future

trait RoomService {
  def count(): Future[Int]
  def create(room: RoomForm): Future[Room]
  def create(rooms: Seq[RoomForm]): Future[Unit]
  def find(id: Long): Future[Option[Room]]
  def list(): Future[Seq[Room]]
}
