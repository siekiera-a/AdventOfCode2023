package day5

import day5.Day5.parseInput

object Part1 {

    private class SeedsAsNumbersGenerator(numbers: List<Long>) : SeedsGenerator by numbers.iterator()

    fun part1(): Long {
        return parseInput { rawSeeds -> SeedsAsNumbersGenerator(rawSeeds) }
            .findLowestLocation()
    }
}