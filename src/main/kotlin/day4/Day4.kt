package day4

object Day4 {
    data class Card(
        val winningNumbers: Set<Int>,
        val ownedNumbers: Set<Int>
    )

    fun List<String>.convertToCards(): List<Card> {
        return map { line ->
            line.substring(line.indexOf(":") + 1)
                .split("|")
                .map { numbersAsString ->
                    numbersAsString.trim()
                        .split(Regex("\\s+"))
                        .map { it.toInt() }
                        .toSet()
                }
                .let { (winningNumbers, ownedNumbers) -> Card(winningNumbers, ownedNumbers) }
        }
    }
}

fun main() {
    println("Part 1 = ${Part1.part1()}")
    println("Part 2 = ${Part2.part2()}")
}