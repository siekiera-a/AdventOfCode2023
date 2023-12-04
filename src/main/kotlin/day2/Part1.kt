package day2

import day2.Day2.Game
import day2.Day2.convertToGames
import utils.readNotEmptyLines

object Part1 {

    fun part1(): Int {
        return readNotEmptyLines("day2-input.txt")
            .convertToGames()
            .filter { it.wasPossible() }
            .sumOf { it.id }
    }

    private fun Game.wasPossible() = rounds.all {
        it.red <= MAX_RED_CUBES && it.blue <= MAX_BLUE_CUBES && it.green <= MAX_GREEN_CUBES
    }

    private const val MAX_RED_CUBES = 12
    private const val MAX_GREEN_CUBES = 13
    private const val MAX_BLUE_CUBES = 14
}