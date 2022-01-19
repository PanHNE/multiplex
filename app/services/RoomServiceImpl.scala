package services

import daos.RoomDAO
import forms.RoomForm
import models.Room

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class RoomServiceImpl @Inject()(roomDAO: RoomDAO)(implicit context: ExecutionContext) extends RoomService {

  override def count(): Future[Int] =
    roomDAO.count()

  override def create(room: RoomForm): Future[Room] =
    roomDAO.insert(room)

  override def create(rooms: Seq[RoomForm]): Future[Unit] =
    Future(rooms.foreach( room => roomDAO.insert(room)))

  override def find(id: Long): Future[Option[Room]] =
    roomDAO.find(id)

  override def list(): Future[Seq[Room]] =
    roomDAO.list()
}
