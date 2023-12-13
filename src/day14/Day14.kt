package day14

import readInput


fun main() {

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("day13", true)
    val input = readInput("day13")

    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    println(part1(input))
    println(part2(input))
    check(part1(input) == 6076)
    check(part2(input) == 24805)
}




