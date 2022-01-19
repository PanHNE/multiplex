package services

import daos.ScreeningDAO
import forms.ScreeningForm
import models.Screening

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ScreeningServiceImpl @Inject()(
  screeningDAO: ScreeningDAO,
  roomService: RoomService,
  filmService: FilmService
)(implicit context: ExecutionContext) extends ScreeningService {

  override def count(): Future[Int] =
    screeningDAO.count()

  override def create(screening: ScreeningForm): Future[Unit] = {
    for {
      optRoom <- roomService.find(screening.roomId)
      optFilm <- filmService.find(screening.filmId)
    } yield for {
      roomId <- optRoom.map(_.id)
      filmId <- optFilm.map(_.id)
    } yield {
      screeningDAO.insert(Screening(None, roomId, filmId, screening.dateAndTime))
    }
  }

  override def create(screenings: Seq[ScreeningForm]): Future[Unit] =
    Future(screenings.foreach( screening => create(screening)))

  override def find(id: Long): Future[Either[String, Screening]] =
    for {
      screening <- screeningDAO.find(id)
    } yield screening match {
      case Some(value) => Right(value)
      case None => Left(s"Not found screening with id ${id}")
    }

  override def list(): Future[Seq[Screening]] =
    screeningDAO.list()
}
