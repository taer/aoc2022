package day08

import readInput


fun main() {
    fun part1(input: List<String>): Int {
        return 1
    }
    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("day08", true)
    val input = readInput("day08")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    println(part1(input))
    println(part2(input))
    check(part1(input) == 1306611)
    check(part2(input) == 13210366)
}
