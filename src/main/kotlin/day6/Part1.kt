package day6

import day6.Day6.countWinPossibilities
import day6.Day6.multiply
import day6.Day6.readRaces

object Part1 {

    fun part1(): Int {
        return readRaces(Part1::extractNumbers)
            .map { race -> countWinPossibilities(race) }
            .multiply()
    }

    private fun extractNumbers(line: String) = line.split(Regex("\\s+"))
        .drop(1)
        .map { it.toLong() }
}
