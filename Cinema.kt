package cinema

var ticketsPurchased = 0
var income = 0
var totalIncome = 0

fun generateSeating(rows:Int, seatsPerRow: Int):MutableList<MutableList<String>> {
    val seating = mutableListOf<MutableList<String>>()
    repeat(rows+1) {
        seating.add(mutableListOf())
    }
    seating[0].add(" ")
    for (i in 1..seatsPerRow) {
        seating[0].add("$i")
    }
    for (i in 1..rows) {
        seating[i].add("$i")
        repeat(seatsPerRow) {
            seating[i].add("S")
        }
    }

    totalIncome =  if (rows * seatsPerRow <= 60) {
        10 * rows * seatsPerRow
    } else {
        10 * (rows/2) * seatsPerRow + 8 * (rows - rows/2) * seatsPerRow
    }


    return seating
}

fun showSeating(rows:Int, seatsPerRow:Int, seating:MutableList<MutableList<String>>) {
    println("Cinema:")
    for (i in seating.indices) {
        println(seating[i].joinToString(" "))
    }
    println()
    menu(rows, seatsPerRow, seating)
}

fun buyTicket(rows:Int, seatsPerRow:Int, seating:MutableList<MutableList<String>>) {
    println("Enter a row number:")
    val rowNum = readln().toInt()
    println("Enter a seat number in that row:")
    val seatNum = readln().toInt()

    if (rowNum !in 1..rows || seatNum !in 1..seatsPerRow) {
        println("Wrong input!")
        buyTicket(rows, seatsPerRow, seating)
        return
    }

    if (seating[rowNum][seatNum] == "B") {
        println("That ticket has already been purchased!")
        buyTicket(rows, seatsPerRow, seating)
        return
    }


    if (rows * seatsPerRow <= 60) {
        println("Ticket price: $10")
        income+=10
    } else {
        if (rowNum <= (rows / 2)) {
            println("Ticket price: $10")
            income+=10
        } else {
            println("Ticket price: $8")
            income+=8
        }
    }
    ticketsPurchased++
    seating[rowNum][seatNum] = "B"
    menu(rows, seatsPerRow, seating)
}

fun statistics(rows:Int, seatsPerRow:Int, seating:MutableList<MutableList<String>>) {
    println("Number of purchased tickets: $ticketsPurchased")
    println("Percentage: ${"%.2f".format(100 * ticketsPurchased.toDouble() / (rows * seatsPerRow))}%")
    println("Current income: $$income")
    println("Total income: $$totalIncome")
    println()
    menu(rows, seatsPerRow, seating)
}

fun menu(rows:Int, seatsPerRow:Int, seating:MutableList<MutableList<String>>) {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    when(readln().toInt()) {
        1 -> showSeating(rows, seatsPerRow, seating)
        2 -> buyTicket(rows, seatsPerRow, seating)
        3 -> statistics(rows, seatsPerRow, seating)
        0 -> Unit
    }
}

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    val seats = generateSeating(rows, seatsPerRow)

    menu(rows,seatsPerRow,seats)

}