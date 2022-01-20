package utils

import java.time.LocalTime

object Enumerations extends Enumeration {

  val hoursScreening = List(
    LocalTime.of(14, 30),
    LocalTime.of(18, 0),
    LocalTime.of(21, 0)
  )

}
