package services

import daos.ScreeningDAO
import forms.ScreeningForm
import models.Screening
import services.Service.NotFound

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ScreeningServiceImpl @Inject()(
  screeningDAO: ScreeningDAO,
  roomService: RoomService,
  filmService: FilmService
)(implicit context: ExecutionContext) extends ScreeningService {

  override def count(): Future[Int] =
    screeningDAO.count()

  override def create(screening: ScreeningForm): Future[Either[ServiceError, Screening]] = {
    roomService.find(screening.roomId) flatMap { optRoom =>
      filmService.find(screening.filmId) flatMap { optFilm =>
        (optRoom, optFilm) match {
          case (Right(room), Right(film)) => screeningDAO.insert(Screening(None, room.id, film.id, screening.dateAndTime))
          case (Left(err), _) => Future.successful(Left(err))
          case (_, Left(err)) => Future(Left(err))
        }
      }
    }
  }

  override def create(screenings: Seq[ScreeningForm]): Future[Unit] =
    Future(screenings.foreach( screening => create(screening)))

  override def find(id: Long): Future[Either[NotFound, Screening]] =
    for {
      screening <- screeningDAO.find(id)
    } yield screening match {
      case Some(value) => Right(value)
      case None => Left(NotFound(s"Not found screening with id ${id}"))
    }

  override def list(): Future[Seq[Screening]] =
    screeningDAO.list()
}
