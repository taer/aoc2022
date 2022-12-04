package day03

import readInput


fun main() {

    fun oneContainsOther(first: IntRange, second: IntRange) =
        first.first <= second.first && first.last >= second.last

    fun parseInput(input: List<String>) = input.map {
        it.split(",")
    }.map {
        val first = it[0].split("-")
        val second = it[1].split("-")
        val firstRange = first[0].toInt()..first[1].toInt()
        val secondRange = second[0].toInt()..second[1].toInt()
        firstRange to secondRange
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).count { (first, second) ->
            oneContainsOther(first, second) || oneContainsOther(second, first)
        }
    }

    fun part2(input: List<String>): Int {
        return parseInput(input)
            .count { (first, second) ->
                first.intersect(second).isNotEmpty()
            }
    }

    val testInput = readInput("day04", true)
    val input = readInput("day04")

    check(part1(testInput) == 2)
    println(part1(input))

    check(part2(testInput) == 4)
    println(part2(input))
}
