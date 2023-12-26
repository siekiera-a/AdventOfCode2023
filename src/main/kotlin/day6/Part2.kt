package day6

import day6.Day6.countWinPossibilities
import day6.Day6.multiply
import day6.Day6.readRaces

object Part2 {

    fun part2(): Int {
        return readRaces(Part2::extractNumbers)
            .map { race -> countWinPossibilities(race) }
            .multiply()
    }

    private fun extractNumbers(line: String) = line.substring(line.indexOf(":") + 1)
        .replace(Regex("\\s+"), "")
        .let { listOf(it.toLong()) }
}
