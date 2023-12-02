package day1

object Day1 {

    fun Pair<Int, Int>.convertToNumber() = first * 10L + second

    fun String.firstDigit() = first { it.isDigit() }.digitToInt()

    fun String.lastDigit() = last { it.isDigit() }.digitToInt()
}

fun main() {
    println("Part 1 = ${Part1.part1()}")
    println("Part 2 = ${Part2.part2()}")
}