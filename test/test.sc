import models.Seat

def createSeats(screeningId: Long) = for {
  row <- 1 to 10
  numberOfSeat <- 1 to 10
} yield {
  val id = numberOfSeat + ((row - 1) * 10)
  Seat(Some(id), screeningId, row, numberOfSeat, available = true)
}


val seats: Seq[Seat] = createSeats(1)

println(seats)