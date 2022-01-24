package daos

import forms.RoomForm
import models.Room

import scala.concurrent.Future

trait RoomDAO {
  def count(): Future[Int]
  def insert(room: RoomForm): Future[Room]
  def find(id: Long): Future[Option[Room]]
  def list(): Future[Seq[Room]]
}
