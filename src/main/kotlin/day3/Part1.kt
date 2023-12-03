package day3

import utils.readNotEmptyLines
import kotlin.math.max
import kotlin.math.min

object Part1 {

    private data class DigitPosition(val column: Int, val digit: Char)

    fun part1(): Int {
        return readNotEmptyLines("day3-input.txt")
            .findPartsNumbers()
            .sum()
    }

    private fun List<String>.findPartsNumbers(): List<Int> =
        mapIndexed { row, line ->
            val numbers = mutableListOf<Int>()
            val numberBuilder = mutableListOf<DigitPosition>()

            "$line.".forEachIndexed { column, char ->
                when {
                    char.isDigit() -> numberBuilder.add(DigitPosition(column, char))
                    numberBuilder.isNotEmpty() -> {
                        if (char.isSymbol() || isAnySymbolAround(this, numberBuilder, row)) {
                            numbers.add(numberBuilder.buildNumber())
                        }
                        numberBuilder.clear()
                    }
                }
            }

            numbers.toList()
        }.flatten()

    private fun Char.isSymbol() = !isDigit() && this != '.'

    private fun isAnySymbolAround(board: List<String>, numberBuilder: List<DigitPosition>, row: Int): Boolean {
        val numberStartedAt = numberBuilder.first.column
        val numberFinishedAt = numberBuilder.last.column

        val startSearchingFromColumn = max(numberStartedAt - 1, 0)
        val finishSearchingOnColumn = min(numberFinishedAt + 1, board[0].length - 1)

        if (board[row].let { it[startSearchingFromColumn].isSymbol() || it[finishSearchingOnColumn].isSymbol() }) {
            return true
        }

        return listOfNotNull(
            (row - 1).takeIf { it >= 0 },
            (row + 1).takeIf { it < board.size }
        ).find {
            board[it].substring(startSearchingFromColumn..finishSearchingOnColumn)
                .any { char -> char.isSymbol() }
        } != null
    }

    private fun List<DigitPosition>.buildNumber() = fold(StringBuilder("")) { acc, digitPosition ->
        acc.append(digitPosition.digit)
    }.toString().toInt()
}
