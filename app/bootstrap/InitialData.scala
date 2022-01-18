package bootstrap

import forms.FilmForm
import services.FilmService

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

private[bootstrap] class InitialData @Inject() (
  filmService: FilmService
)(implicit executionContext: ExecutionContext) {

  def insertInitialData(): Try[Unit] = {
    val insertInitialDataFuture = for {
      count <- filmService.count() if count == 0
      _ <- filmService.create(InitialData.films)
      } yield ()

    Try(Await.result(insertInitialDataFuture, Duration.Inf))
  }

  insertInitialData()
}

private[bootstrap] object InitialData {
  private def films = Seq(
    FilmForm("Inception", 148),
    FilmForm("Sherlock Holmes", 128),
    FilmForm("355", 123),
    FilmForm("MATRIX", 148),
    FilmForm("AVENGERS", 142)
  )
}

