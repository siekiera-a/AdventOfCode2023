package day7;

import day7.Day7.Hand;
import day7.Day7.HandType;

import java.util.List;
import java.util.Map;
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

class Part2 {

    static class HandWithAllowedJoker extends Hand {

        private final char JOKER = 'J';

        HandWithAllowedJoker(String chars) {
            super(chars);
        }

        @Override
        HandType handType() {
            var sortedCharOccurrences = charOccurrencesSortedDesc();

            int jokerOccurrences = sortedCharOccurrences.stream()
                .filter((entry) -> entry.getKey() == JOKER)
                .findFirst()
                .map((entry) -> entry.getValue().intValue())
                .orElse(0);

            if (jokerOccurrences == 5) {
                return FIVE_OF_KIND;
            }

            var notJokersCards = sortedCharOccurrences.stream()
                .filter((entry) -> entry.getKey() != JOKER)
                .limit(2)
                .toList();

            int firstCardOccurrencesAmongWithJoker =
                notJokersCards.getFirst().getValue().intValue() + jokerOccurrences;

            return switch (firstCardOccurrencesAmongWithJoker) {
                case 5 -> FIVE_OF_KIND;
                case 4 -> FOUR_OF_KIND;
                case 3 -> {
                    var secondCardOccurrences = notJokersCards.get(1).getValue().intValue();
                    yield secondCardOccurrences == 2 ? FULL_HOUSE : THREE_OF_KIND;
                }
                case 2 -> {
                    var secondCardOccurrences = notJokersCards.get(1).getValue().intValue();
                    yield secondCardOccurrences == 2 ? TWO_PAIR : ONE_PAIR;
                }
                default -> HIGH_CARD;
            };
        }

        private List<Map.Entry<Character, Long>> charOccurrencesSortedDesc() {
            return chars.chars().mapToObj((c) -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .toList();
        }

        @Override
        protected int labelValueAt(int pos) {
            if (Character.toUpperCase(chars.charAt(pos)) == JOKER) {
                return 0;
            }

            return super.labelValueAt(pos);
        }
    }

    static long part2() {
        return Day7.calculateTotalWinnings(readHands(HandWithAllowedJoker::new));
    }
}
