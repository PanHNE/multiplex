package models

import play.api.data.Form
import play.api.data.Forms.{mapping}
import play.api.libs.json.{Json, OWrites}

case class FilmScreeningData(film: Film, screening: Screening) {

}

object FilmScreeningData {
  implicit val writer: OWrites[FilmScreeningData] = Json.writes[FilmScreeningData]

  val form: Form[FilmScreeningData] = Form(
    mapping(
      "film" -> Film.form.mapping,
      "screening" ->  Screening.form.mapping
    )(FilmScreeningData.apply)(FilmScreeningData.unapply)
  )

  def zipFilmWithScreening(films: Seq[Film], screenings: Seq[Screening]) =
    screenings.foldLeft(Seq[FilmScreeningData]()) { (f, n) =>
      f ++ films.find(_.id == n.filmId).map(f => FilmScreeningData(f, n))
    }

  def sort(seq: Seq[FilmScreeningData]): Seq[FilmScreeningData] = {
    seq.sortBy(_.film.title).sortBy(_.screening.dateAndTime)
  }
}
