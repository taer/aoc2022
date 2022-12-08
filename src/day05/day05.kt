package day05

import readInput
import split


fun main() {

    fun parseStacks(stacks: List<String>): List<MutableList<String>> {
        val simpler = stacks.dropLast(1).map {
            it.drop(1).mapIndexedNotNull { index, c ->
                if (index % 4 == 0)
                    c.toString()
                else
                    null
            }
        }
        val indexes = simpler.last()
        val realStacks = List(indexes.size) {
            mutableListOf<String>()
        }
        simpler.reversed().forEach {
            it.forEachIndexed { index, s ->
                if (s.isNotBlank()) {
                    realStacks[index].add(s)
                }
            }
        }
        return realStacks
    }

    data class Move(val count: Int, val from: Int, val to: Int)

    fun computerMoves(
        toList: List<List<String>>,
        moves: List<String>
    ): List<Move> {
        val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()
        return moves.map {

            val matchEntire = regex.matchEntire(it)
            requireNotNull(matchEntire)

            val (amount, from, to) = matchEntire.destructured
            Move(amount.toInt(), from.toInt(), to.toInt())
        }
    }

    fun dataParse(input: List<String>): Pair<List<MutableList<String>>, List<Move>> {
        val toList = input.asSequence().split { it == "" }.toList()
        val sta = parseStacks(toList[0])
        val moves = computerMoves(toList, toList[1])
        return Pair(sta, moves)
    }

    fun part1(input: List<String>): String {
        val (sta, moves) = dataParse(input)

        fun stackForMove(location: Int) = sta[location-1]
        moves.forEach {move ->
            repeat(move.count){
                val item = stackForMove(move.from).removeLast()
                stackForMove(move.to).add(item)
            }
        }
       return sta.joinToString("") { it.last() }

    }

    fun part2(input: List<String>): String {
        val (sta, moves) = dataParse(input)

        fun stackForMove(location: Int) = sta[location-1]
        moves.forEach {move ->
            val sourceStack = stackForMove(move.from)
            val item = sourceStack.takeLast(move.count)
            repeat(move.count){
                sourceStack.removeLast()
            }
            stackForMove(move.to).addAll(item)
            move.to
        }
        return sta.joinToString("") { it.last() }    }

    val testInput = readInput("day05", true)
    val input = readInput("day05")

    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")
    check(part1(input) == "GRTSWNJHH")
    check(part2(input) == "QLFQDBBHM")

    println(part1(input))
    println(part2(input))
}
