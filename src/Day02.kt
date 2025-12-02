fun main() {
    fun part1(input: List<String>): Long = findInvalidIds(input, true)

    fun part2(input: List<String>): Long = findInvalidIds(input, false)


    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun findInvalidIds(input: List<String>, justDoubleRepetitions: Boolean): Long = input
    .flatMap { it.split(",") }
    .flatMap { range ->
        val (start, end) = range.split("-").map { it.toLong() }
        start..end
    }
    .filter { id ->
        hasRepeatingPattern(id, justDoubleRepetitions = justDoubleRepetitions)
    }
    .sum()

private fun hasRepeatingPattern(number: Long, justDoubleRepetitions: Boolean): Boolean {
    val numStr = number.toString()
    val len = numStr.length


    return when {
        len < 2 -> false

        justDoubleRepetitions -> if (len % 2 != 0) {
            false
        } else {
            val patternLength = len / 2
            val firstHalf = numStr.take(patternLength)
            val secondHalf = numStr.drop(patternLength)
            firstHalf == secondHalf
        }

        else -> {
            val repeated = numStr + numStr
            repeated.substring(1, repeated.length - 1).contains(numStr)
        }
    }

}
