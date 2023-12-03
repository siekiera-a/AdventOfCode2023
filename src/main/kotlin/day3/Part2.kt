package day3

import utils.readNotEmptyLines
import kotlin.math.max
import kotlin.math.min

object Part2 {

    private data class Coordinates(val x: Int, val y: Int)

    fun part2(): Int {
        return readNotEmptyLines("day3-input.txt")
            .findGearRatios()
            .sum()
    }

    private fun List<String>.findGearRatios(): List<Int> {
        return mapIndexed { row, line ->
            line.mapIndexedNotNull { column, char ->
                takeIf { char == '*' }
                    ?.let { allPartNumbersAroundPoint(row, column) }
                    ?.takeIf { it.size == 2 }
                    ?.let { it[0] * it[1] }
            }
        }.flatten()
    }

    private fun List<String>.allPartNumbersAroundPoint(y: Int, x: Int): List<Int> {
        val top = Coordinates(x = x, y = max(y - 1, 0))
        val bottom = Coordinates(x = x, y = min(y + 1, size - 1))

        val numbers = mutableListOf<Int>()

        numbersOnRightAndLeftTo(Coordinates(x = x, y = y))
            .toCollection(numbers)

        listOf(top, bottom).forEach { coordinates ->
            if (this[coordinates].isDigit()) {
                buildNumberStartingAt(coordinates)
                    .also { numbers.add(it) }
            } else {
                numbersOnRightAndLeftTo(coordinates)
                    .toCollection(numbers)
            }
        }

        return numbers.toList()
    }

    private fun List<String>.numbersOnRightAndLeftTo(coordinates: Coordinates): List<Int> {
        val maxRight = this[0].length - 1

        return listOf(
            calcLeft(coordinates.x, coordinates.y),
            calcRight(coordinates.x, coordinates.y, maxRight)
        ).mapNotNull { point ->
            this[point].takeIf { it.isDigit() }
                ?.let { buildNumberStartingAt(point) }
        }
    }

    private fun calcLeft(x: Int, y: Int) = Coordinates(x = max(x - 1, 0), y = y)

    private fun calcRight(x: Int, y: Int, max: Int) = Coordinates(x = min(x + 1, max), y = y)

    private fun List<String>.buildNumberStartingAt(coordinates: Coordinates): Int {
        val leftAndMiddlePart = this[coordinates.y].substring(0, coordinates.x + 1)
            .reversed()
            .takeWhile { it.isDigit() }
            .fold(StringBuilder("")) { acc, char ->
                acc.append(char)
            }
            .reverse()

        this[coordinates.y].substring(coordinates.x + 1)
            .takeWhile { it.isDigit() }
            .fold(leftAndMiddlePart) { acc, char ->
                acc.append(char)
            }

        return leftAndMiddlePart.toString().toInt()
    }

    private operator fun List<String>.get(coordinates: Coordinates) = this[coordinates.y][coordinates.x]
}