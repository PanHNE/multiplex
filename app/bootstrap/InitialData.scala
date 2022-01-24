package bootstrap

import forms.{FilmForm, RoomForm, ScreeningForm}
import models.Seat
import services.{FilmService, RoomService, ScreeningService, SeatService}

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

private[bootstrap] class InitialData @Inject() (
  filmService: FilmService,
  roomService: RoomService,
  screeningService: ScreeningService,
  seatService: SeatService
)(implicit executionContext: ExecutionContext) {

  def insertInitialData(): Try[Unit] = {
    val insertInitialDataFuture = for {
      count <- roomService.count() if count == 0
      _ <- roomService.create(InitialData.rooms)
      _ <- filmService.create(InitialData.films)
      _ <- screeningService.create(InitialData.screenings)
      _ <- seatService.create(InitialData.seats)
      } yield ()

    Try(Await.result(insertInitialDataFuture, Duration.Inf))
  }

  insertInitialData()
}

private[bootstrap] object InitialData {
  private def rooms = (1 to 5).map( _ =>
    RoomForm(20, 20),
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
    ScreeningForm(2, 2, LocalDateTime.of(2022, 2, 1, 14,30)),
    ScreeningForm(2, 2, LocalDateTime.of(2022, 2, 1, 18,0)),
    ScreeningForm(3, 4, LocalDateTime.of(2022, 2, 1, 14,30)),
    ScreeningForm(3, 4, LocalDateTime.of(2022, 2, 1, 18,0)),
    ScreeningForm(4, 3, LocalDateTime.of(2022, 2, 1, 21,0)),
    ScreeningForm(4, 5, LocalDateTime.of(2022, 2, 2, 14,30)),
    ScreeningForm(4, 5, LocalDateTime.of(2022, 2, 2, 18,0)),
    ScreeningForm(4, 5, LocalDateTime.of(2022, 2, 2, 21,0)),
    ScreeningForm(4, 1, LocalDateTime.of(2021, 2, 1, 21,0))
  )

  private def seats = for {
      screeningId <- 1 to screenings.length
      row <- 1 to 20
      numberOfSeat <- 1 to 20
    } yield Seat(None, screeningId, row, numberOfSeat, available = true)

}
