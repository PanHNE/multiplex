package services

import daos.ScreeningDAO
import forms.{ScreeningByDaysAndHours, ScreeningForm}
import models.{FilmScreeningData, RoomSeatScreeningData, Screening}
import services.Service.NotFound

import java.time.{LocalDate, LocalDateTime, LocalTime}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ScreeningServiceImpl @Inject()(
  screeningDAO: ScreeningDAO,
  roomService: RoomService,
  filmService: FilmService,
  seatService: SeatService
)(implicit context: ExecutionContext) extends ScreeningService {

  override def count(): Future[Int] =
    screeningDAO.count()

  override def create(screening: ScreeningForm): Future[Either[ServiceError, Screening]] = {
    roomService.find(screening.roomId) flatMap { optRoom =>
      filmService.find(screening.filmId) flatMap { optFilm =>
        (optRoom, optFilm) match {
          case (Right(room), Right(film)) => screeningDAO.insert(Screening(None, room.id, film.id, screening.dateAndTime))
          case (Left(err), _) => Future.successful(Left(err))
          case (_, Left(err)) => Future.successful(Left(err))
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

  override def listOfFilms(date: ScreeningByDaysAndHours): Future[Seq[FilmScreeningData]] = {
    (for {
      screenings <- list(date)
    } yield for {
      films <- filmService.findByIds(screenings.map(_.filmId))
    } yield {
      FilmScreeningData.sort(FilmScreeningData.zipFilmWithScreening(films, screenings))
    }).flatten
  }

  override def list(): Future[Seq[Screening]] =
    screeningDAO.list().map(_.filter(data => data.dateAndTime.isEqual(LocalDateTime.now()) || data.dateAndTime.isAfter(LocalDateTime.now())))

  override def list(data: ScreeningByDaysAndHours): Future[Seq[Screening]] = {
    (data.days, data.hours) match {
      case (days, hours) if days.nonEmpty && hours.nonEmpty => list(days, hours)
      case (days, _) if days.nonEmpty => listByDays(days)
      case (_, hours) if hours.nonEmpty => listByHours(hours)
      case _ => list()
    }
  }

  override def allInfo(id: Long): Future[Either[NotFound, RoomSeatScreeningData]] = {
    find(id).flatMap {
      case Left(error) => Future.successful(Left(error))
      case Right(screening) => for {
        room <- roomService.find(screening.roomId)
        seats <- seatService.available(id, available = true)
      } yield (room, seats) match {
        case (Right(room), Right(seats)) => Right(RoomSeatScreeningData(room, screening, seats))
        case (Left(err), _) => Left(err)
        case (_, Left(err)) => Left(err)
      }
    }
  }

  private def listByHours(hours: List[LocalTime]): Future[Seq[Screening]] = {
    list().map { list =>
      list.filter( screenings => hours.contains(screenings.dateAndTime.toLocalTime))
    }
  }

  private def listByDays(days: List[LocalDate]): Future[Seq[Screening]] = {
    list().map { list =>
      list.filter( screenings => days.contains(screenings.dateAndTime.toLocalDate))
    }
  }

  private def list(days: List[LocalDate], hours: List[LocalTime]): Future[Seq[Screening]] = {
    list().map { list =>
      list.filter { screening =>
        days.contains(screening.dateAndTime.toLocalDate) && hours.contains(screening.dateAndTime.toLocalTime)
      }
    }
  }

}
