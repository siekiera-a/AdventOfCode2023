package day5

import day5.Day5.parseInput

object Part2 {
    private class SeedsAsRangesGenerator(numbers: List<Long>) : SeedsGenerator {

        private var currentRangeIndex = 0
        private var currentOffsetInRange = 0

        private val ranges =
            numbers.windowed(size = 2, step = 2) { (start, size) -> start..<(start + size) }
                .distinctRanges()

        private fun List<LongRange>.distinctRanges(): List<LongRange> {
            val sortedRanges = sortedBy { it.first }
            val ranges = mutableListOf(sortedRanges.first)
            for (i in 1..<sortedRanges.size) {
                val previousRange = ranges.last
                val currentRange = sortedRanges[i]

                if (currentRange.first < previousRange.last) {
                    if (currentRange.last > previousRange.last) {
                        ranges.removeLast().let { ranges.add(it.first..currentRange.last) }
                    }
                } else {
                    ranges.add(currentRange)
                }
            }
            return ranges.toList()
        }

        override fun hasNext(): Boolean {
            return when {
                currentRangeIndex < ranges.size - 1 -> true
                else -> ranges.last.let { range -> range.first + currentOffsetInRange <= range.last }
            }
        }

        override fun next(): Long {
            return ranges[currentRangeIndex].let { range ->
                val nextValue = range.first + currentOffsetInRange
                if (nextValue in range) {
                    currentOffsetInRange++
                    nextValue
                } else {
                    currentRangeIndex++
                    currentOffsetInRange = 1
                    ranges[currentRangeIndex].first
                }
            }
        }
    }

    fun part2(): Long {
        return parseInput { rawSeeds -> SeedsAsRangesGenerator(rawSeeds) }
            .findLowestLocation()
    }
}