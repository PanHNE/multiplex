package utils

import utils.TicketType.TicketType

import java.time.LocalTime

object Helpers {

  val hoursScreening = List(
    LocalTime.of(14, 30),
    LocalTime.of(18, 0),
    LocalTime.of(21, 0)
  )

  val ticketPrice = Map(
    TicketType.Adult -> 2500,
    TicketType.Student -> 1800,
    TicketType.Child -> 1250
  )

  def ticketMatcher(stringRepresentation: String): TicketType = {
    stringRepresentation match {
      case "Adult" => TicketType.Adult
      case "Student" => TicketType.Student
      case "Child" => TicketType.Child
    }
  }

}

object TicketType extends Enumeration {
  type TicketType = Value

  val Adult, Student, Child = Value
}


