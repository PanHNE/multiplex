package utils

import data.Data._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class NicerRespondsSpec extends AnyFreeSpec with Matchers {

  val nicerResponder = NicerResponds

  "Check nicer responds" - {
    "check amount to pay for 3 kids" in {
      val charge = nicerResponder.amountToPay(kidTickets)
      val req = 37.5

      charge should be(req)
    }

    "check amount to pay for 3 tickets" in {
      val charge = nicerResponder.amountToPay(differentTickets)
      val req = 55.5

      charge should be(req)
    }

    "checking respond with amount to pay and reservation time" in {
      val respond = nicerResponder.respondWithAmountToPayAndReservationTime(differentTickets, screenings(0))
      val req = "Amount to pay: 55.5. Reservation expiration time: 2022-02-01, 14:15"

      respond should be(req)
    }

    "checking respond with films" in {
      val respond = nicerResponder.respondWithListFilms(filmScreeningData)
      val req = List("Inception : 2022-02-01, 14:30", "Inception : 2022-02-01, 18:00", "Sherlock Holmes : 2022-02-01, 14:30", "Sherlock Holmes : 2022-02-01, 18:00")

      respond should be(req)
    }

    "checking respond with available seats" in {
      val respond = nicerResponder.respondWithAvailableSeatsAndRoom(roomSeatScreeningData)
      val req = "Number room 1. Available seats: [Vector(" +
        "[1-1], [1-2], [1-3], [1-4], [1-5], [1-6], [1-7], [1-8], " +
        "[2-1], [2-2], [2-3], [2-4], [2-5], [2-6], [2-7], [2-8], " +
        "[3-1], [3-2], [3-3], [3-4], [3-5], [3-6], [3-7], [3-8], " +
        "[4-1], [4-2], [4-3], [4-4], [4-5], [4-6], [4-7], [4-8])]"

      println(respond)
      println(req)

      respond should be(req)
    }
  }
}
