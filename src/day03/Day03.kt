package day03

import readInput


fun main() {
    val codeOfA = 'a'.code

    fun Set<Char>.priorityValue(): Int {
        val dupeChar = this.single()
        val upperCase = if (dupeChar.isUpperCase()) 26 else 0
        return dupeChar.lowercaseChar().code - codeOfA + upperCase + 1
    }

    fun part1(input: List<String>): Int {
        return input.asSequence()
            .map {
                val middle = it.length / 2
                it.substring(0, middle).toSet() to it.substring(middle).toSet()
            }.map {
                it.first.intersect(it.second)
            }.map {
                it.priorityValue()
            }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { it.toSet() }
            .chunked(3)
            .map {
                it.reduce { acc, chars ->
                    acc.intersect(chars)
                }
            }.map { it.priorityValue() }
            .sum()
    }

    val testInput = readInput("day03", true)
    val input = readInput("day03")

    check(part1(testInput) == 157)
    println(part1(input))

    check(part2(testInput) == 70)
    println(part2(input))
}
