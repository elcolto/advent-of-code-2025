fun main() {
    fun part1(input: List<String>): Int {
        val paperRollGrid = parseInput(input)
        val slotsWithLessThanFourNeighbors = findSlotsWithLessThanFourNeighbors(paperRollGrid)
        return slotsWithLessThanFourNeighbors.count()
    }

    fun part2(input: List<String>): Int {
        tailrec fun run(grid: List<List<PaperRollSlot>>, accumulator: Int): Int {
            val slotsToRemove = findSlotsWithLessThanFourNeighbors(grid)
            if (slotsToRemove.isEmpty()) {
                return accumulator
            }

            val slotsToRemoveSet = slotsToRemove.toSet()
            val nextGrid = grid.mapIndexed { r, row ->
                row.mapIndexed { c, slot ->
                    if (slotsToRemoveSet.contains(r to c)) PaperRollSlot.Empty else slot
                }
            }
            return run(nextGrid, accumulator + slotsToRemove.size)
        }

        return run(parseInput(input), 0)
    }

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): List<List<PaperRollSlot>> {
    return input.map { line ->
        line.map {
            when (it) {
                '@' -> PaperRollSlot.Filled
                '.' -> PaperRollSlot.Empty
                else -> error("not defined slot")
            }
        }
    }
}

private fun findSlotsWithLessThanFourNeighbors(paperRollGrid: List<List<PaperRollSlot>>): List<Pair<Int, Int>> {
    val filledSlots = paperRollGrid.asSequence()
        .flatMapIndexed { r, row ->
            row.asSequence().mapIndexedNotNull { c, slot ->
                if (slot == PaperRollSlot.Filled) r to c else null
            }
        }

    val neighborOffsets = (-1..1).flatMap { dr ->
        (-1..1).mapNotNull { dc ->
            if (dr == 0 && dc == 0) null else dr to dc
        }
    }

    return filledSlots.filter { (row, column) ->
        val filledNeighbors = neighborOffsets.count { (dr, dc) ->
            paperRollGrid.getOrNull(row + dr)?.getOrNull(column + dc) == PaperRollSlot.Filled
        }
        filledNeighbors < 4
    }.toList()
}

private enum class PaperRollSlot {
    Filled, Empty
}
