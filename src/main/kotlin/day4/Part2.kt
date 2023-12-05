package day4

import day4.Day4.convertToCards
import day4.Part1.ownedWinningNumbers
import utils.readNotEmptyLines

object Part2 {

    data class DuplicatedCard(val numberOfMatchingNumbers: Int, var numberOfDuplicates: Int)

    fun part2(): Int {
        return readNotEmptyLines("day4-input.txt")
            .convertToCards()
            .map { DuplicatedCard(numberOfMatchingNumbers = it.ownedWinningNumbers().size, numberOfDuplicates = 1) }
            .let { duplicatedCards ->
                duplicatedCards.forEachIndexed { index, duplicatedCard ->
                    copyCardsIfNumberOfMatchingNumbersIsPositive(index, duplicatedCard, duplicatedCards)
                }

                duplicatedCards.sumOf { it.numberOfDuplicates }
            }
    }

    private fun copyCardsIfNumberOfMatchingNumbersIsPositive(
        cardIndex: Int,
        card: DuplicatedCard,
        cards: List<DuplicatedCard>
    ) {
        if (card.numberOfMatchingNumbers <= 0) {
            return
        }

        val nextIndex = cardIndex + 1

        if (nextIndex == cards.size) {
            return
        }

        val maxIndex = minOf(nextIndex + card.numberOfMatchingNumbers, cards.size)

        for (index in nextIndex..<maxIndex) {
            cards[index].numberOfDuplicates += card.numberOfDuplicates
        }
    }
}
