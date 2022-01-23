#!/bin/bash

# Add new film
echo
curl --location --request POST 'http://localhost:9000/film/add' \
--form 'title="Shawsenk"' \
--form 'length="123"'

# List with films
echo
curl --location --request GET 'http://localhost:9000/films'

# Find film by id
curl --location --request GET 'http://localhost:9000/film/2'

# Add new room
echo
curl --location --request POST 'http://localhost:9000/room/add' \
--form 'numberOfRows="10"' \
--form 'numberOfSeatsInRow="16"'

# List with rooms
echo
curl --location --request GET 'http://localhost:9000/rooms'

# Find room by id
curl --location --request GET 'http://localhost:9000/room/3'

# Add new screening
echo
curl --location --request POST 'http://localhost:9000/screening/add' \
--form 'roomId="1"' \
--form 'filmId="1"' \
--form 'dateAndTime="2022-02-02 14:30"'

# List with screenings
echo
curl --location --request GET 'http://localhost:9000/screenings'

# Find screening by id
curl --location --request GET 'http://localhost:9000/screening/1'

# List of film by data 2022-02-01 and hour 18:00
echo
curl --location --request POST 'http://localhost:9000/screenings/byDaysAndHours' \
--form 'days[0]="2022-02-01"' \
--form 'hours[0]="18:00"'

# List of available seats for screening with id 1
echo
curl --location --request GET 'http://localhost:9000/screening?screeningId=1&available=true'

# Respond the total amount to pay and reservation expiration time
echo
curl --location --request POST 'http://localhost:9000/reservation' \
--form 'screeningId="1"' \
--form 'name="Adrian"' \
--form 'surname="Domin"' \
--form 'tickets[0].seatId="1"' \
--form 'tickets[0].typeTicket="Child"' \
--form 'tickets[1].seatId="2"' \
--form 'tickets[1].typeTicket="Adult"' \
--form 'tickets[2].seatId="3"' \
--form 'tickets[2].typeTicket="Student"'
