package day02

import readInput

enum class RPS(val value: Int) {
    ROCK(1),
    PAPER(2),
    Scissors(3)
}

fun translate(input: String): RPS {
    return when (input) {
        "A", "X" -> RPS.ROCK
        "B", "Y" -> RPS.PAPER
        "C", "Z" -> RPS.Scissors
        else -> throw RuntimeException("Dunno what $input is")
    }
}

sealed class Result(val points: Int) {
    object Win : Result(6)
    object Tie : Result(3)
    object Lose : Result(0)
}

fun score(us: RPS, them: RPS): Result {
    return if (us == them) {
        Result.Tie
    } else {
        when (us) {
            RPS.ROCK -> if (them == RPS.Scissors) Result.Win else Result.Lose
            RPS.Scissors -> if (them == RPS.PAPER) Result.Win else Result.Lose
            RPS.PAPER -> if (them == RPS.ROCK) Result.Win else Result.Lose
        }
    }
}
data class Moves(val their: RPS, val ours: RPS)
fun translateInput(input: List<String>): List<Moves> {
    return input.map { it.split(" ") }.map {
        val theirMove = translate(it[0])
        val ourMove = translate(it[1])
        Moves(their = theirMove, ours = ourMove)
    }
}
fun calcWin(input: List<Moves>) = input.sumOf { (theirMove, ourMove) ->
    val score = score(ourMove, theirMove)
    score.points + ourMove.value
}


fun main() {
    fun loseTo(input: RPS): RPS {
        return when (input){
            RPS.ROCK -> RPS.Scissors
            RPS.PAPER -> RPS.ROCK
            RPS.Scissors -> RPS.PAPER
        }
    }
    fun winTo(input: RPS): RPS {
        return when (input){
            RPS.ROCK -> RPS.PAPER
            RPS.PAPER -> RPS.Scissors
            RPS.Scissors -> RPS.ROCK
        }
    }
    fun part1(input: List<String>): Int {
        val inputData = translateInput(input)
        return calcWin(inputData)
    }

    fun part2(input: List<String>): Int {
        val inputData = translateInput(input)
        val newMoves = inputData.map {
            val desiredOutcome = when(it.ours){
                RPS.ROCK -> Result.Lose
                RPS.PAPER -> Result.Tie
                RPS.Scissors -> Result.Win
            }
            val ourNewMove = when (desiredOutcome) {
                Result.Lose -> loseTo((it.their))
                Result.Tie -> it.their
                Result.Win -> winTo(it.their)
            }
            it.copy(ours = ourNewMove)
        }
        return calcWin(newMoves)
    }

    val testInput = readInput("day02", true)
    val input = readInput("day02")

    check(part1(testInput) == 15)
    println(part1(input))


    check(part2(testInput) == 12)
    println(part2(input))
}
