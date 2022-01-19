package bootstrap

import forms.{FilmForm, RoomForm}
import services.{FilmService, RoomService}

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

private[bootstrap] class InitialData @Inject() (
  filmService: FilmService,
  roomService: RoomService
)(implicit executionContext: ExecutionContext) {

  def insertInitialData(): Try[Unit] = {
    val insertInitialDataFuture = for {
      count <- roomService.count() if count == 0
      _ <- roomService.create(InitialData.rooms)
      _ <- filmService.create(InitialData.films)
      } yield ()

    Try(Await.result(insertInitialDataFuture, Duration.Inf))
  }

  insertInitialData()
}

private[bootstrap] object InitialData {
  private def rooms = Seq(
    RoomForm(10, 10),
    RoomForm(10, 20),
    RoomForm(20, 20),
    RoomForm(10, 20)
  )

  private def films = Seq(
    FilmForm("Inception", 148),
    FilmForm("Sherlock Holmes", 128),
    FilmForm("355", 123),
    FilmForm("MATRIX", 148),
    FilmForm("AVENGERS", 142)
  )


}

