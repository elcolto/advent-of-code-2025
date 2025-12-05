fun main() {
    fun part1(input: List<String>): Int {
        val (freshIdRanges, ingredientIds) = parseInput(input)
        return ingredientIds.count { id ->
            freshIdRanges.any { id in it }
        }
    }

    fun part2(input: List<String>): Long {
        val sortedFreshIdRanges = parseInput(input).first.sortedBy { it.first() }

        return sortedFreshIdRanges.drop(1)
            .fold(mutableListOf(sortedFreshIdRanges.first())) { mergedRanges: MutableList<LongRange>, range: LongRange ->
                val lastRange = mergedRanges.last()
                if (range.first <= lastRange.last + 1) {
                    mergedRanges[mergedRanges.lastIndex] = lastRange.first..maxOf(lastRange.last, range.last)
                } else if (range.first > lastRange.last) {
                    mergedRanges.add(range)
                }
                mergedRanges
            }
            .sumOf { range ->
                range.last - range.first + 1
            }

    }

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): Pair<List<LongRange>, List<Long>> {
    val blank = input.indexOfFirst { it.isBlank() }
    val freshIdRanges = input.subList(0, blank).map {
        val (start, end) = it.split("-")
        start.toLong()..end.toLong()
    }
    val ingredientIds = input.subList(blank + 1, input.size).map { it.toLong() }
    return freshIdRanges to ingredientIds
}
