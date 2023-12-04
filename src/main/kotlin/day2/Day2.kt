package day2

private typealias RawRound = List<Pair<Int, String>>

object Day2 {

    data class Round(
        val red: Int,
        val blue: Int,
        val green: Int
    ) {

        fun addRed(value: Int) = copy(red = red + value)

        fun addBlue(value: Int) = copy(blue = blue + value)

        fun addGreen(value: Int) = copy(green = green + value)
    }

    data class Game(
        val id: Int,
        val rounds: List<Round>
    )

    fun List<String>.convertToGames(): List<Game> = mapIndexed { index, rawData ->
        val rounds = rawData.substring(rawData.indexOf(":") + 1).split(";")
            .map { roundsString -> roundsString.parseRounds().sumCubesInRound() }

        Game(index + 1, rounds)
    }

    private fun String.parseRounds(): RawRound = split(",")
        .map { cube ->
            val (numberAsString, color) = cube.trim().split(" ")
            numberAsString.toInt() to color
        }

    private fun RawRound.sumCubesInRound() = fold(Round(red = 0, blue = 0, green = 0)) { acc, cubeData ->
        val (number, color) = cubeData
        when (color.lowercase()) {
            "red" -> acc.addRed(number)
            "blue" -> acc.addBlue(number)
            "green" -> acc.addGreen(number)
            else -> throw IllegalArgumentException("unknown color = $color")
        }
    }
}

fun main() {
    println("Part 1 = ${Part1.part1()}")
    println("Part 2 = ${Part2.part2()}")
}
