package me.keepkam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.IntStream.range;

@Slf4j
class FractionalKnapsack {
    private static class Attempt_1 {
        private static double solution(List<Pair> items, int weight) {
            var itemsSortedByValueWeightRatio = new PriorityQueue<>(comparingDouble(Pair::getRatio).reversed());
            itemsSortedByValueWeightRatio.addAll(items);

            Sack sack = new Sack(new ArrayList<>(items.size()), weight);

            while (!sack.isFull() && !itemsSortedByValueWeightRatio.isEmpty()) {
                var highestValItem = itemsSortedByValueWeightRatio.poll(); // greedy pick

                if (highestValItem.weight <= sack.getRemainingWeight()) {
                    sack.add(highestValItem);
                } else {
                    double splitWeight = highestValItem.weight - sack.getOverflow(highestValItem.weight);
                    double ratio = splitWeight / highestValItem.weight;

                    var splitItem = new Pair(highestValItem.weight * ratio, highestValItem.value * ratio);
                    sack.add(splitItem);

                    var updatedItem = new Pair(highestValItem.weight - splitItem.weight, highestValItem.value - splitItem.value);
                    itemsSortedByValueWeightRatio.add(updatedItem);
                }
            }

//        log.info("{}", sack);
            return sack.getValue();
        }

        private static class Sack {
            public Sack(List<Pair> items, int maxWeight) {
                this.items = new ArrayList<>(items);
                this.maxWeight = maxWeight;
                update();
            }

            @Override
            public String toString() {
                return "Sack{" + "items=" + items + ", weight=" + weight + ", maxWeight=" + maxWeight + ", value=" + value + '}';
            }

            public List<Pair> items;
            private double weight;
            private final int maxWeight;
            private double value;

            public double getWeight() {
                return weight;
            }

            public double getValue() {
                return value;
            }

            private void update() {
                weight = items.stream().mapToDouble(p -> p.weight).sum();
                value = items.stream().mapToDouble(p -> p.value).sum();
            }

            public void add(Pair item) {
                assert weight + item.weight <= maxWeight;
                items.add(item);
                update();
            }

            public double getRemainingWeight() {
                return maxWeight - weight;
            }

            public double getOverflow(double w) {
                assert w > getRemainingWeight();
                return w - getRemainingWeight();
            }

            public boolean isFull() {
                return weight == maxWeight;
            }
        }

    }

    @ParameterizedTest
    @MethodSource("provideInputs")
    void solve(Input input) {
        int[] weights = input.weights;
        int[] values = input.values;
        int weight = input.weight;

        List<Pair> items = range(0, weights.length).mapToObj(i -> new Pair(weights[i], values[i])).toList();
        double maxWeight = Attempt_1.solution(items, weight);

        log.info("Max weight: {}", maxWeight);
    }

    private static class Pair {
        public double weight;
        public double value;
        public double ratio;

        public Pair(double w, double v) {
            weight = w;
            value = v;
            ratio = (v / w);
        }


        @Override
        public String toString() {
            return "Pair{" + "weight=" + weight + ", value=" + value + ", ratio=" + getRatio() + '}';
        }

        public double getRatio() {
            return ratio;
        }
    }

    private record Input(int[] weights, int[] values, int weight) {
    }

    private static List<Input> provideInputs() {
        return List.of(new Input(new int[]{6, 1, 5, 3}, new int[]{3, 6, 1, 4}, 10), new Input(new int[]{20, 24, 36, 40, 42}, new int[]{12, 35, 41, 25, 32}, 100), new Input(new int[]{50, 40, 90, 120, 10, 200}, new int[]{40, 50, 25, 100, 30, 45}, 200));
    }
}
