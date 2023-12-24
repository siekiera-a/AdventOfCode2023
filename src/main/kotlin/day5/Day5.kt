package day5

import utils.readFile
import kotlin.math.min

typealias SeedsGenerator = Iterator<Long>

object Day5 {

    data class DestinationSourceData(
        val destinationStart: Long,
        val sourceStart: Long,
        val rangeSize: Long
    )

    data class MapOfData(val data: List<DestinationSourceData>) {

        private data class Ranges(
            val destinationRange: LongRange,
            val sourceRange: LongRange
        )

        private val ranges = data.map {
            Ranges(
                destinationRange = it.destinationStart..<(it.destinationStart + it.rangeSize),
                sourceRange = it.sourceStart..<(it.sourceStart + it.rangeSize)
            )
        }

        fun mappingFor(number: Long): Long {
            return ranges.firstOrNull { number in it.sourceRange }
                ?.let {
                    val offset = number - it.sourceRange.first
                    it.destinationRange.first + offset
                } ?: number
        }
    }

    data class Day5Input(val seedsGenerator: SeedsGenerator, val maps: List<MapOfData>) {

        fun findLowestLocation(): Long {
            return seedsGenerator.minOf { seed ->
                maps.fold(seed) { input, map -> map.mappingFor(input) }
            }
        }
    }

    private fun SeedsGenerator.minOf(block: (Long) -> Long): Long {
        var minValue = block(next())
        while(hasNext()) {
            minValue = min(minValue, block(next()))
        }
        return minValue
    }

    fun parseInput(seedsGeneratorFactory: (List<Long>) -> SeedsGenerator): Day5Input {
        return readFile("day5-input.txt").split("\n\n")
            .let {
                Day5Input(
                    seedsGenerator = seedsGeneratorFactory(extractNumbers(it.first.leaveOnlyNumbers())),
                    maps = it.drop(1).map { rawMap -> extractMap(rawMap) }
                )
            }
    }

    private fun extractMap(rawData: String): MapOfData {
        return rawData.split("\n")
            .drop(1)
            .filter { it.isNotBlank() }
            .map {
                extractNumbers(it).let { numbers ->
                    DestinationSourceData(
                        destinationStart = numbers[0],
                        sourceStart = numbers[1],
                        rangeSize = numbers[2]
                    )
                }
            }
            .let { MapOfData(it) }
    }

    private fun extractNumbers(string: String) = string.trim().split(" ").map { it.toLong() }

    private fun String.leaveOnlyNumbers() = substring(indexOf(":") + 1).trim()
}

fun main() {
    println("Part 1 = ${Part1.part1()}")
    println("Part 2 = ${Part2.part2()}")
}
