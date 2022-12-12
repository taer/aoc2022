package day09

import readInput
import java.io.File
import kotlin.math.abs

fun main() {
    data class Location(var x: Int = 0, var y: Int = 0)

    fun Location.move(direction: String) {
        when (direction) {
            "U" -> y++
            "D" -> y--
            "L" -> x--
            "R" -> x++
        }
    }

    fun Location.touching(other: Location): Boolean {
        return abs(this.x - other.x) <= 1 && abs(this.y - other.y) <= 1
    }

    fun Location.follow(whoToFollow: Location) {
        val me = this
        if (!whoToFollow.touching(me)) {
            if (whoToFollow.x > me.x) me.x++
            if (whoToFollow.x < me.x) me.x--
            if (whoToFollow.y > me.y) me.y++
            if (whoToFollow.y < me.y) me.y--
        }
    }

    fun simulateRealSnake(input: List<String>, length: Int): Int {
        val tailLocations = hashSetOf<Location>()
        val snake = List(length) { Location() }
        input.forEach { cmd ->
            val (direction, count) = cmd.split(" ")
            repeat(count.toInt()) { _ ->
                snake.first().move(direction)
                snake.windowed(2) {
                    it[1].follow(it[0])
                }
                tailLocations.add(snake.last().copy())
            }
        }
        return tailLocations.size
    }

    fun part1(input: List<String>): Int {
        return simulateRealSnake(input, 2)
    }

    fun part2(input: List<String>): Int {
        return simulateRealSnake(input, 10)
    }

    val testInput = readInput("day09", true)
    val testInput2 = File("src/${"day09"}", "data_test2.txt").readLines()
    val input = readInput("day09")
    check(part1(testInput) == 13)
    check(part2(testInput2) == 36)


    println(part1(input))
    println(part2(input))
    check(part1(input) == 6522)
    check(part2(input) == 2717)
}



