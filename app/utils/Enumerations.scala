package utils

import java.time.LocalTime

object Enumerations extends Enumeration {

  val hoursScreening = List(
    LocalTime.of(14, 30),
    LocalTime.of(18, 0),
    LocalTime.of(21, 0)
  )

  val tickets = Map(
    "adult" -> 2500,
    "student" -> 1800,
    "child" -> 1250
  )

}
