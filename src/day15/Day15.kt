package day15

import Point
import readInput


fun main() {


    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1

    }
    val testInput = readInput("day15", true)
    val input = readInput("day15")

    check(part1(testInput) == 24)
//    check(part2(testInput) == 93)

    println(part1(input))
    println(part2(input))
    check(part1(input) == 578)
    check(part2(input) == 24805)
}




