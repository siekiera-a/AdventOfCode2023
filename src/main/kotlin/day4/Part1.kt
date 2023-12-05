package day4

import day4.Day4.Card
import day4.Day4.convertToCards
import utils.readNotEmptyLines
import kotlin.math.pow

object Part1 {

    fun part1(): Int {
        return readNotEmptyLines("day4-input.txt")
            .convertToCards()
            .sumOf { it.getScore() }
    }

    private fun Card.getScore(): Int {
        return ownedWinningNumbers()
            .takeIf { it.isNotEmpty() }
            ?.let { 2.0.pow(it.size - 1).toInt() }
            ?: 0
    }

    fun Card.ownedWinningNumbers(): Set<Int> = winningNumbers intersect ownedNumbers
}