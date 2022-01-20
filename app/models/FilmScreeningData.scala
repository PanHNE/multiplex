package models

import play.api.libs.json.{Json, OFormat}

case class FilmScreeningData(film: Film, screening: Screening) {

}

object FilmScreeningData {
  implicit val filmFormat: OFormat[FilmScreeningData] = Json.format[FilmScreeningData]

  def zipFilmWithScreening(films: Seq[Film], screenings: Seq[Screening]) =
    screenings.foldLeft(Seq[FilmScreeningData]()) { (f, n) =>
      f ++ films.find(_.id == n.filmId).map(f => FilmScreeningData(f, n))
    }

  def sort(seq: Seq[FilmScreeningData]): Seq[FilmScreeningData] = {
    seq.sortBy(_.film.title).sortBy(_.screening.dateAndTime)
  }
}
