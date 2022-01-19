package services

import forms.RoomForm
import models.Room
import services.Service.NotFound

import scala.concurrent.Future

trait RoomService {
  def count(): Future[Int]
  def create(room: RoomForm): Future[Room]
  def create(rooms: Seq[RoomForm]): Future[Unit]
  def find(id: Long): Future[Either[NotFound, Room]]
  def list(): Future[Seq[Room]]
}
