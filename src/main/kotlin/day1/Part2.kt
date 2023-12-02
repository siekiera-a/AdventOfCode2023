package day1

import day1.Day1.convertToNumber
import day1.Day1.firstDigit
import day1.Day1.lastDigit
import utils.readNotEmptyLines

object Part2 {

    fun part2(): Long {
        return readNotEmptyLines("day1-input.txt")
            .map { line ->
                val wordsInLine = digitsAsWord.map { (word) -> line.allPositionsOfWord(word) }.flatten()

                if (wordsInLine.isEmpty()) {
                    line.firstDigit() to line.lastDigit()
                } else {
                    selectFirstDigit(line, wordsInLine) to selectLastDigit(line, wordsInLine)
                }
            }
            .sumOf { it.convertToNumber() }
    }

    private fun String.allPositionsOfWord(word: String): List<WordPosition> {
        var startIndex = 0
        var indexOfWord = indexOf(word, startIndex)

        val elements = mutableListOf<WordPosition>()

        while(indexOfWord >= 0) {
            elements.add(WordPosition(word, indexOfWord))
            startIndex = indexOfWord + 1
            indexOfWord = indexOf(word, startIndex)
        }

        return elements
    }

    private fun selectFirstDigit(line: String, wordsInLine: List<WordPosition>): Int {
        val indexOfFirstDigit = line.indexOfFirst { it.isDigit() }
        return when {
            indexOfFirstDigit < 0 -> wordsInLine.firstWordAsDigit().let { digitsAsWord.getValue(it.word) }
            else -> {
                val firstWordAsDigit = wordsInLine.firstWordAsDigit()
                if (indexOfFirstDigit < firstWordAsDigit.index) {
                    line[indexOfFirstDigit].digitToInt()
                } else {
                    digitsAsWord.getValue(firstWordAsDigit.word)
                }
            }
        }
    }

    private fun List<WordPosition>.firstWordAsDigit() = minByOrNull { it.index }!!

    private fun selectLastDigit(line: String, wordsInLine: List<WordPosition>): Int {
        val indexOfLastDigit = line.indexOfLast { it.isDigit() }
        return when {
            indexOfLastDigit < 0 -> wordsInLine.lastWordAsDigit().let { digitsAsWord.getValue(it.word) }
            else -> {
                val lastWordAsDigit = wordsInLine.lastWordAsDigit()
                if (indexOfLastDigit > lastWordAsDigit.index) {
                    line[indexOfLastDigit].digitToInt()
                } else {
                    digitsAsWord.getValue(lastWordAsDigit.word)
                }
            }
        }
    }

    private fun List<WordPosition>.lastWordAsDigit() = maxByOrNull { it.index }!!

    data class WordPosition(
        val word: String,
        val index: Int
    )

    private val digitsAsWord = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
}