package day2

import day2.Day2.convertToGames
import utils.readNotEmptyLines

object Part2 {

    fun part2(): Int {
        return readNotEmptyLines("day2-input.txt")
            .convertToGames()
            .map { game -> game.calculateMinRequiredCubes() }
            .sumOf { (red, blue, green) -> red * blue * green }
    }

    private fun Game.calculateMinRequiredCubes() = Round(
        red = rounds.maxOf { it.red },
        blue = rounds.maxOf { it.blue },
        green = rounds.maxOf { it.green }
    )
}