fun main() {
    fun part1(input: List<String>): Long = input.map { line -> line.map { it.digitToInt() } }
        .sumOf { batteryBank ->
            maxJoltage(batteryBank, 2)
        }

    fun part2(input: List<String>): Long = input.map { line -> line.map { it.digitToInt() } }
        .sumOf { batteryBank ->
            maxJoltage(batteryBank, 12)
        }

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

fun maxJoltage(batteryBank: List<Int>, batteryCount: Int): Long = (1..batteryCount)
    .fold(0 to 0L) { (bankIndex, joltage), i ->
        val (index, jolts) = batteryBank.withIndex().toList()
            .slice(bankIndex until (batteryBank.size - batteryCount + i))
            .maxBy { it.value }
        index + 1 to (joltage * 10 + jolts)
    }.second