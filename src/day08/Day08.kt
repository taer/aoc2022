package day08

import readInput

class Cell(val value: Int, var visible: Boolean = false){
    override fun toString(): String {
        return "$value $visible"
    }
}
fun main() {


    fun mySeq(
        rows: Iterable<Int>,
        columns: Iterable<Int>
    ) = sequence {
        rows.forEach { r ->
            columns.forEach { c ->
                yield(r to c)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val forest = input.map { it.map { Cell(it.digitToInt()) }.toTypedArray() }.toTypedArray()

        val rows = forest.indices
        val columns = forest.first().indices


        rows.forEach { r ->
            var max = -1
            columns.forEach { c ->
                val cell = forest[r][c]
                if (cell.value > max) {
                    max = cell.value
                    cell.visible = true
                }
            }
            max = -1
            columns.reversed().forEach { c ->
                val cell = forest[r][c]
                if (cell.value > max) {
                    max = cell.value
                    cell.visible = true
                }
            }
        }

        columns.forEach { c ->
            var max = -1
            rows.forEach { r ->
                val cell = forest[r][c]
                if (cell.value > max) {
                    max = cell.value
                    cell.visible = true
                }
            }
            max = -1
            rows.reversed().forEach { r ->
                val cell = forest[r][c]
                if (cell.value > max) {
                    max = cell.value
                    cell.visible = true
                }
            }
        }

        return forest.sumOf { it.count { it.visible } }
    }

    fun getValue(forest: Array<Array<Cell>>, row: Int, col: Int): Int {
        val columns = forest.first().indices
        val rows = forest.indices

        val ourHeight = forest[row][col].value
        var scoreUp = 0
        var scoreDown = 0
        var scoreL = 0
        var scoreR = 0
        for (x in row - 1 downTo 0) {
            scoreUp++
            if (ourHeight <= forest[x][col].value) {
                break;
            }
        }
        for (x in row + 1..rows.last) {
            scoreDown++
            if (ourHeight <= forest[x][col].value) {
                break;
            }
        }
        for (y in col + 1..columns.last) {
            scoreR++
            if (ourHeight <= forest[row][y].value) {
                break;
            }
        }
        for (y in col - 1 downTo 0) {
            scoreL++
            if (ourHeight <= forest[row][y].value) {
                break;
            }
        }

        return scoreUp * scoreDown * scoreL * scoreR
    }

    fun part2(input: List<String>): Int {
        val forest = input.map { it.map { Cell(it.digitToInt()) }.toTypedArray() }.toTypedArray()
        val rows = forest.indices
        val columns = forest.first().indices
        return rows.maxOf { row ->
            columns.maxOf  { col->
                getValue(forest, row, col)
            }
        }
    }

    val testInput = readInput("day08", true)
    val input = readInput("day08")
    check(part1(testInput) == 21){}
    check(part2(testInput) == 8)

    println(part1(input))
    println(part2(input))
    check(part1(input) == 1835)
    check(part2(input) == 263670)
}
