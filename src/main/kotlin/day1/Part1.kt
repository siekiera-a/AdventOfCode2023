package day1

import day1.Day1.convertToNumber
import day1.Day1.firstDigit
import day1.Day1.lastDigit
import utils.readNotEmptyLines

object Part1 {

    fun part1(): Long {
        return readNotEmptyLines("day1-input.txt")
            .map { it.firstDigit() to it.lastDigit() }
            .sumOf { it.convertToNumber() }
    }
}