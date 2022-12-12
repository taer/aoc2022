package day10

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.runBlocking
import readInput

suspend fun main() {

    fun simulator(input: List<String>): Flow<Int> {
        val dataValue = flow {
            var data = 1
            input.forEach { cmd ->
                emit(data)
                when (cmd) {
                    "noop" -> {}
                    else -> {
                        emit(data)
                        val amount = cmd.split(" ")[1].toInt()
                        data += amount
                    }
                }
            }
        }
        return dataValue
    }

    suspend fun part1(input: List<String>): Int {
        val dataValue = simulator(input)
        return dataValue.withIndex().filter { iv ->
            val cycle = iv.index + 1
            (cycle - 20) % 40 == 0
        }.map {
            (it.index + 1) * it.value
        }.reduce { x, y ->
            x + y
        }
    }

    fun part2(input: List<String>): Flow<String> {
        val dataValue = simulator(input)

        return flow {
            dataValue.withIndex().collect { iv ->
                val cycle = iv.index + 1
                val pencilLocation = (cycle - 1) % 40
                val xReg = iv.value

                val sprintLocation = xReg - 1..xReg + 1
                val toDraw = if (pencilLocation in sprintLocation) "#" else "."
                emit(toDraw)

                if (cycle % 40 == 0) {
                    emit("\n")
                }
            }
        }
    }

    val testInput = readInput("day10", true)
    val input = readInput("day10")
    check(part1(testInput) == 13140)
    part2(testInput).collect(::print)
    println()


    println(part1(input))
    part2(input).collect(::print)

    check(part1(input) == 11960)
    val part2 = part2(input).toList().joinToString("")
    check(
        part2 == """
    ####...##..##..####.###...##..#....#..#.
    #.......#.#..#.#....#..#.#..#.#....#..#.
    ###.....#.#....###..#..#.#....#....####.
    #.......#.#....#....###..#.##.#....#..#.
    #....#..#.#..#.#....#....#..#.#....#..#.
    ####..##...##..#....#.....###.####.#..#.
    
    """.trimIndent()
    )
}



