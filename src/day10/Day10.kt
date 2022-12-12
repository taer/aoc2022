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

    fun Location.follow(head: Location) {
        val tail = this
        if (head == tail) {
            return
        }
        if (!head.touching(tail)) {
            if (head.x > tail.x) tail.x++
            if (head.x < tail.x) tail.x--
            if (head.y > tail.y) tail.y++
            if (head.y < tail.y) tail.y--
        }
    }

    fun part1(input: List<String>): Int {
        val tailLocations = hashSetOf<Location>()
        val snake = List(2) { Location() }
        input.forEach {
            val (direction, count) = it.split(" ")
            repeat(count.toInt()) {
                snake.windowed(2) {
                    it[0].move(direction)
                    it[1].follow(it[0])
                }
                tailLocations.add(snake.last().copy())
            }
        }
        return tailLocations.size
    }

    fun part2(input: List<String>): Int {
        val tailLocations = hashSetOf<Location>()
        val snake = List(10) { Location() }
        input.forEach {
            val (direction, count) = it.split(" ")
            repeat(count.toInt()) {
                snake.first().move(direction)
                snake.windowed(2) {
                    it[1].follow(it[0])
                }
                tailLocations.add(snake.last().copy())
            }
        }
        return tailLocations.size
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



