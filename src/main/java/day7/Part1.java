package day7;

import day7.Day7.Hand;
import day7.Day7.HandType;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static day7.Day7.HandType.FIVE_OF_KIND;
import static day7.Day7.HandType.FOUR_OF_KIND;
import static day7.Day7.HandType.FULL_HOUSE;
import static day7.Day7.HandType.HIGH_CARD;
import static day7.Day7.HandType.ONE_PAIR;
import static day7.Day7.HandType.THREE_OF_KIND;
import static day7.Day7.HandType.TWO_PAIR;
import static day7.Day7.readHands;

class Part1 {

    static class HandWithForbiddenJoker extends Hand {

        HandWithForbiddenJoker(String chars) {
            super(chars);
        }

        @Override
        HandType handType() {
            var sortedCharOccurrences = sortedDescendingCharOccurrences(chars);

            return switch (sortedCharOccurrences.getFirst()) {
                case 5 -> FIVE_OF_KIND;
                case 4 -> FOUR_OF_KIND;
                case 3 -> {
                    var secondHighestRepetitions = sortedCharOccurrences.get(1);
                    yield secondHighestRepetitions == 2 ? FULL_HOUSE :
                        THREE_OF_KIND;
                }
                case 2 -> {
                    var secondHighestRepetitions = sortedCharOccurrences.get(1);
                    yield secondHighestRepetitions == 2 ? TWO_PAIR :
                        ONE_PAIR;
                }
                default -> HIGH_CARD;
            };
        }

        private List<Integer> sortedDescendingCharOccurrences(String chars) {
            return chars.chars()
                .mapToObj((c) -> (char) c)
                .collect(
                    Collectors.groupingBy(Function.identity(), Collectors.counting())
                )
                .values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .map(Long::intValue)
                .toList();
        }
    }

    static long part1() {
        return Day7.calculateTotalWinnings(readHands(HandWithForbiddenJoker::new));
    }
}
