package models

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class SeatSpec extends AnyFreeSpec with Matchers {

  def createRowWithSomeSeatsTaken(taken: Seq[Int] = Seq(), numberOfSeats: Int): Seq[Seat] = {
    (0 until numberOfSeats).map { n =>
      Seat(Some(n.toLong), 1, (n / 10) + 1, (n % 10) + 1, !taken.contains(n + 1))
    }
  }

  "Check" - {
    "layout row when all seats are available" in {
      val available = createRowWithSomeSeatsTaken(numberOfSeats = 10)
      val taken = Seq[Seat]()
      val length = available.length

      Seat.checkLayoutRow(available, taken, length) should be(true)
    }

    "layout row when two seats with number 3 and 4 are taken" in {
      val seats = createRowWithSomeSeatsTaken(Seq(3, 4), 10)

      Seat.checkLayoutSeats(seats) should be(true)
    }

    "reserved one seat" in {
      val seat = Seq(Seat(Some(1L), 1, 1, 1, available = true))
      val seats = createRowWithSomeSeatsTaken(numberOfSeats = 10)

      Seat.checkSeats(seat, seats) should be(true)
    }

    "reserved four seats with numbers: 1, 2, 3, 4" in {
      val seats = createRowWithSomeSeatsTaken(numberOfSeats = 10)
      val reservationSeats = createRowWithSomeSeatsTaken(numberOfSeats = 4)

      Seat.checkSeats(reservationSeats, seats) should be(true)
    }

    "reserved four seats with numbers: 1, 2, 3, 4 where 1 and 2 are taken" in {
      val seats = createRowWithSomeSeatsTaken(Seq(1,2), numberOfSeats = 10)
      val reservationSeats = createRowWithSomeSeatsTaken(numberOfSeats = 4)

      Seat.checkSeats(reservationSeats, seats) should be(false)
    }

    "reserved seats with numbers: 3, 4 where: 1, 2, 5, 6 are taken" in {
      val seats = createRowWithSomeSeatsTaken(Seq(1,2,5,6), numberOfSeats = 10)
      val reservationSeats = Seq(Seat(Some(2L), 1, 1, 3, available = true), Seat(Some(3L), 1, 1, 4, available = true))

      Seat.checkSeats(reservationSeats, seats) should be(true)
    }

    "reserved seat with numbers: 2" in {
      val seats = createRowWithSomeSeatsTaken(Seq(), numberOfSeats = 10)
      val reservationSeats = Seq(Seat(Some(1L), 1, 1, 2, available = true))

      Seat.checkSeats(reservationSeats, seats) should be(false)
    }

    "reserved seat with numbers: 9" in {
      val seats = createRowWithSomeSeatsTaken(Seq(), numberOfSeats = 10)
      val reservationSeats = Seq(Seat(Some(8L), 1, 1, 9, available = true))

      Seat.checkSeats(reservationSeats, seats) should be(false)
    }

    "reserved seat with number: 3 where: 1, 2, 5, 6 are taken" in {
      val seats = createRowWithSomeSeatsTaken(Seq(1,2,5,6), numberOfSeats = 10)
      val reservationSeats = Seq(Seat(Some(2L), 1, 1, 3, available = true))

      Seat.checkSeats(reservationSeats, seats) should be(false)
    }
  }
}
