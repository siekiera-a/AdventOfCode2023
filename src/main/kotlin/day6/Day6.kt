package day6

import utils.readFile

object Day6 {
    data class Race(
        val time: Long,
        val distance: Long
    )

    fun readRaces(numbersExtractor: (String) -> List<Long>): List<Race> = readFile("day6-input.txt").let {
        val (times, distances) = it.split("\n")
        numbersExtractor(times).zip(numbersExtractor(distances)) { time, distance ->
            Race(time, distance)
        }
    }

    fun countWinPossibilities(race: Race): Int {
        return (1..<race.time).asSequence()
            .map { timeOfHoldingButton -> timeOfHoldingButton * (race.time - timeOfHoldingButton) }
            .count { it > race.distance }
    }

    fun List<Int>.multiply() = reduce { acc, value -> acc * value }
}

fun main() {
    println("Part 1 = ${Part1.part1()}")
    println("Part 2 = ${Part2.part2()}")
}
