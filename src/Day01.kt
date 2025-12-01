import kotlin.math.absoluteValue
import kotlin.math.sign

private const val DIAL_SIZE = 100
private const val DIAL_START = 50

fun main() {
    fun part1(input: List<String>): Int = input
        .map { splitLineToInstruction(it) }
        .runningFold(DIAL_START) { dial, (direction, distance) ->
            val value = when (direction) {
                Direction.L -> dial - distance
                Direction.R -> dial + distance
            }
            value.mod(DIAL_SIZE)
        }
        .count { it == 0 }

    fun part2(input: List<String>): Int = input
        .map { splitLineToInstruction(it) }
        .map { (direction, distance) ->
            when (direction) {
                Direction.L -> -1 * distance
                Direction.R -> distance
            }
        }
        .flatMap { distance -> List(distance.absoluteValue) { distance.sign } }
        .runningFold(DIAL_START) { dial, direction ->
            val i = dial + direction
            (i.mod(DIAL_SIZE) + DIAL_SIZE).mod(DIAL_SIZE)
        }
        .count { it == 0 }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun splitLineToInstruction(line: String): Pair<Direction, Int> {
    // line has format Lxx or Rxx
    val direction = Direction.valueOf(line.first().toString())
    val distance = line.drop(1).toInt()
    return direction to distance
}

private enum class Direction {
    L, R
}
