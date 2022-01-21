package utils

import play.api.data.FormError
import play.api.data.format.Formatter
import utils.TicketType.TicketType

object FormHelper {
  implicit def matchFilterFormat: Formatter[TicketType] = new Formatter[TicketType] {

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], utils.TicketType.Value] =
      data.get(key)
        .map(TicketType.withName)
        .toRight(Seq(FormError(key, "error.required", Nil)))

    override def unbind(key: String, value: TicketType) =
      Map(key -> value.toString)
  }

  def fulfilConditions(word: String): Boolean = {
    word.head.isUpper && word.tail.forall(_.isLower) && word.forall(_.isLetter)
  }


  lazy val isSurnameCorrect: String => Boolean = { surname =>
    val arr = surname.split("-")
    arr.length match {
      case x if x == 1 || x == 2 => arr.forall(surname => fulfilConditions(surname))
      case _ => false
    }
  }
}
