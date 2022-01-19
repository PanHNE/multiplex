package bootstrap

import bootstrap.InitialData.screenings
import forms.{FilmForm, RoomForm, ScreeningForm}
import services.{FilmService, RoomService, ScreeningService}

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

private[bootstrap] class InitialData @Inject() (
  filmService: FilmService,
  roomService: RoomService,
  screeningService: ScreeningService
)(implicit executionContext: ExecutionContext) {

  def insertInitialData(): Try[Unit] = {
    val insertInitialDataFuture = for {
      count <- roomService.count() if count == 0
      _ <- roomService.create(InitialData.rooms)
      _ <- filmService.create(InitialData.films)
      _ <- screeningService.create(InitialData.screenings)
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

  private def screenings = Seq(
    ScreeningForm(1, 1, LocalDateTime.of(2022, 2, 1, 14,30)),
    ScreeningForm(1, 1, LocalDateTime.of(2022, 2, 1, 18,0)),
    ScreeningForm(1, 1, LocalDateTime.of(2022, 2, 1, 21,0)),
    ScreeningForm(2, 2, LocalDateTime.of(2022, 2, 1, 14,30)),
    ScreeningForm(2, 2, LocalDateTime.of(2022, 2, 1, 18,0)),
    ScreeningForm(2, 2, LocalDateTime.of(2022, 2, 1, 21,0)),
    ScreeningForm(3, 4, LocalDateTime.of(2022, 2, 1, 14,30)),
    ScreeningForm(3, 4, LocalDateTime.of(2022, 2, 1, 18,0)),
    ScreeningForm(3, 4, LocalDateTime.of(2022, 2, 1, 21,0)),
    ScreeningForm(4, 5, LocalDateTime.of(2022, 2, 1, 14,30)),
    ScreeningForm(4, 5, LocalDateTime.of(2022, 2, 1, 18,0)),
    ScreeningForm(4, 5, LocalDateTime.of(2022, 2, 1, 21,0))
  )


}

