import models.Seat
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class SeatSpec extends AnyFreeSpec with Matchers {

  def createSeatsWithOnPlaceNAreTaken(taken: Seq[Int] = Seq(), numberSeat: Int) = {
    (0 until numberSeat).map { n =>
      Seat(Some(n.toLong), 1, (n / 10) + 1, (n % 10) + 1, !taken.contains(n))
    }.toSeq
  }

  "Check seats" - {
    "check layout row when all seats all available" in {
      val available = createSeatsWithOnPlaceNAreTaken(numberSeat = 10)
      val taken = Seq[Seat]()
      val length = available.length

      Seat.checkLayoutRow(available, taken, length) should be(true)
    }

    "check layout row when two seat with number 3 and 4 are taken" in {
      val seats = createSeatsWithOnPlaceNAreTaken(Seq(3, 4), 10)

      Seat.checkLayoutSeats(seats) should be(true)
    }

    "check layout seats" in {
      val available = createSeatsWithOnPlaceNAreTaken(numberSeat = 20)

      Seat.checkLayoutSeats(available) should be(true)
    }


    "one seat" in {
      val seat = Seq(Seat(Some(1L), 1, 1, 1, true))
      val seats = createSeatsWithOnPlaceNAreTaken(numberSeat = 10)

      Seat.checkSeats(seat, seats) should be(true)
    }

    "check fourth seats in one row with seat number 1, 2, 3, 4" in {
      val seats = createSeatsWithOnPlaceNAreTaken(numberSeat = 10)
      val reservationSeats = createSeatsWithOnPlaceNAreTaken(numberSeat = 4)

      Seat.checkSeats(reservationSeats, seats) should be(true)
    }
  }


}
