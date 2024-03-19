package day7;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static day7.Part1.part1;
import static day7.Part2.part2;
import static utils.FileUtilsKt.readNotEmptyLines;

public class Day7 {

    record HandWithBid(
        Hand hand,
        int bid
    ) {}

    static abstract class Hand implements Comparable<Hand> {

        protected final String chars;

        Hand(String chars) {
            this.chars = chars;
        }

        abstract HandType handType();

        @Override
        public int compareTo(@NotNull Hand other) {
            for (int i = 0; i < chars.length(); i++) {
                var myCardLabelValue = labelValueAt(i);
                var otherCardLabelValue = other.labelValueAt(i);

                var comparisonResult = Integer.compare(myCardLabelValue, otherCardLabelValue);

                if (comparisonResult != 0) {
                    return comparisonResult;
                }
            }

            return 0;
        }

        protected int labelValueAt(int pos) {
            var c = Character.toUpperCase(chars.charAt(pos));
            return switch (c) {
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    yield Integer.parseInt(Character.toString(c)) - 1;
                case 'T':
                    yield 9;
                case 'J':
                    yield 10;
                case 'Q':
                    yield 11;
                case 'K':
                    yield 12;
                case 'A':
                    yield 13;
                default:
                    throw new IllegalArgumentException(
                        String.format("Invalid character at pos: %d in string %s", pos, chars)
                    );
            };
        }

        @Override
        public String toString() {
            return chars;
        }
    }

    enum HandType implements Comparable<HandType> {
        FIVE_OF_KIND(1),
        FOUR_OF_KIND(2),
        FULL_HOUSE(3),
        THREE_OF_KIND(4),
        TWO_PAIR(5),
        ONE_PAIR(6),
        HIGH_CARD(7);

        public final int order;

        HandType(int order) {
            this.order = order;
        }
    }

    static long calculateTotalWinnings(List<HandWithBid> handWithBids) {
        var handGroupedByTypes = handWithBids.stream()
            .collect(
                Collectors.groupingBy(
                    (hand) -> hand.hand().handType(),
                    Collectors.toList()
                )
            );

        var sortedHands = handGroupedByTypes.keySet().stream()
            .sorted((first, second) -> second.order - first.order)
            .map((type) -> {
                var hands = new ArrayList<>(handGroupedByTypes.get(type));
                hands.sort(Comparator.comparing(Day7.HandWithBid::hand));
                return hands;
            })
            .flatMap(Collection::stream)
            .toList();

        long sum = 0;

        for (int rank = 1; rank <= sortedHands.size(); rank++) {
            sum += sortedHands.get(rank - 1).bid() * (long) rank;
        }

        return sum;
    }

    @FunctionalInterface
    interface StringToHandConverter {
        Hand convert(String raw);
    }

    static List<HandWithBid> readHands(StringToHandConverter handConverter) {
        var fileName = "day7-input.txt";

        return readNotEmptyLines(fileName).stream()
            .map((line) -> {
                var cardsAndBid = line.split("\\s+");
                return new HandWithBid(handConverter.convert(cardsAndBid[0]), Integer.parseInt(cardsAndBid[1]));
            })
            .toList();
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 = %d\n", part1());
        System.out.printf("Part 2 = %d", part2());
    }
}
