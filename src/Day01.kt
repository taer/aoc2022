fun main() {
    fun sumElves(input: List<String>): List<Int> {
        return input.asSequence()
            .split { it.isBlank() }
            .map { it.sumOf { a -> a.toInt()} }
            .toList()
    }

    fun part1(input: List<String>): Int {
        val elves = sumElves(input)
        return elves.max()
    }

    fun part2(input: List<String>): Int {
        val elves = sumElves(input)
        return elves.sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
