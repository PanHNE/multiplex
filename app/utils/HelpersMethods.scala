package utils

import models.{Screening, Ticket}
import utils.Helpers.ticketPrice

import java.time.LocalDateTime

object HelpersMethods {

  def amountToPay(tickets: Seq[Ticket]): Double = {
    tickets.foldLeft(0){ (f, n) =>
      f + ticketPrice(n.ticketType)
    } / 100
  }

  def nicerDataAndTime(localDateTime: LocalDateTime): String = {
    s"${localDateTime.toLocalDate}, ${localDateTime.toLocalTime}"
  }

  def respond(tickets: Seq[Ticket], screening: Screening): String = {
    val dataAndTIme = nicerDataAndTime(screening.dateAndTime.minusMinutes(15))
    s"Amount to pay: ${amountToPay(tickets)}. Reservation expiration time: $dataAndTIme"
  }

}
