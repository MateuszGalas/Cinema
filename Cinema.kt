package cinema


enum class CinemaSeats(val price: Int) {
    SEAT(10),
    CHEAPER_SEAT(8),
    TAKEN(0)
}

class Cinema(val rows: Int, val seats: Int) {
    val cinema: MutableList<MutableList<CinemaSeats>>
    var totalIncome = 0
    var currentIncome = 0

    init { //Initializing list with different prizes depending on the size of the room.
        if (rows * seats <= 60) {
            cinema = MutableList(rows) { MutableList<CinemaSeats>(seats) { CinemaSeats.SEAT } }
        } else {
            cinema = MutableList(rows) { mutableListOf() }

            for (i in 0 until rows) {
                if (i <= (rows / 2) - 1) repeat(seats) { cinema[i].add(CinemaSeats.SEAT) }
                else repeat(seats) { cinema[i].add(CinemaSeats.CHEAPER_SEAT) }
            }
        }

        cinema.forEach { it.forEach { totalIncome += it.price } } //Counting total possible income.
    }

    fun printCinema() {
        print("\nCinema:\n ")
        repeat(seats) { print(" ${it + 1}") }
        println()
        cinema.forEachIndexed { index, rows ->
            print(index + 1)
            rows.forEach {
                if (it == CinemaSeats.SEAT || it == CinemaSeats.CHEAPER_SEAT) print(" S")
                else print(" B")
            }
            println()
        }
    }

    fun buyTicket() { //Buy ticket if it's not taken and is inside the room
        println("\nEnter a row number:")
        val row = readln().toInt() - 1
        println("Enter a seat number in that row:")
        val seat = readln().toInt() - 1

        if (row !in 0 until rows || seat !in 0 until seats) {
            println("\nWrong input!").also { return buyTicket() }
        }
        if (cinema[row][seat] == CinemaSeats.TAKEN) {
            println("\nThat ticket has already been purchased!").also { return buyTicket() }
        }

        println("Ticket price: $${cinema[row][seat].price}")
        currentIncome += cinema[row][seat].price
        cinema[row][seat] = CinemaSeats.TAKEN
    }

    fun statistics() {// Statistics of Cinema, sold tickets, percentage of sold ticket, current income and total income.
        var allSoldTickets = 0
        cinema.forEach { allSoldTickets += it.count { it == CinemaSeats.TAKEN } }
        println(
            "\nNumber of purchased tickets: $allSoldTickets\n" +
                    "Percentage: %.2f%%\n".format((allSoldTickets.toDouble() / (rows * seats)) * 100) +
                    "Current income: $$currentIncome\n" +
                    "Total income: $$totalIncome"
        )
    }
}


fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    val cinema = Cinema(rows, seats)
    while (true) { // Menu
        println(
            "\n1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit"
        )

        when (readln()) {
            "1" -> cinema.printCinema()
            "2" -> cinema.buyTicket()
            "3" -> cinema.statistics()
            "0" -> break
        }
    }
}