package day13

import readInput

sealed class Day07Parse: Comparable<Day07Parse>{
    class ListItem(val items: List<Day07Parse>): Day07Parse(){
        override fun compareTo(other: Day07Parse): Int {
            return when (other) {
                is ListItem -> {
                    items.zip(other.items)
                        .map { it.first.compareTo(it.second) }
                        .firstOrNull { it != 0 } ?: items.size.compareTo(other.items.size)
                }

                is IntItem -> compareTo(ListItem(listOf(other)))
            }
        }

    }
    class IntItem(val item: Int): Day07Parse(){
        override fun compareTo(other: Day07Parse): Int {
            return when (other){
                is ListItem -> ListItem(listOf(this)).compareTo(other)
                is IntItem -> item.compareTo(other.item)
            }
        }

    }
}

suspend fun main() {




    fun parseLine(iterator: Iterator<Char>): Day07Parse{
        val data = mutableListOf<Day07Parse>()
        val ints = mutableListOf<Char>()
        fun clearBuffer() {
            if (ints.isNotEmpty()) {
                data.add(Day07Parse.IntItem(ints.joinToString("").toInt()))
                ints.clear()
            }
        }
        while(iterator.hasNext()){
            val next = iterator.next()
            when(next){
                '[' -> {
                    clearBuffer()
                    data.add(parseLine(iterator))
                }
                ']' -> {
                    clearBuffer()
                    return Day07Parse.ListItem(data.toList())
                }
                ',' -> clearBuffer()
                '0', '1','2','3','4','5','6','7','8','9'->{
                    ints.add(next)
                }
            }

        }
        clearBuffer()
        return Day07Parse.ListItem(data.toList())
    }
    fun parseLine(line: String): Day07Parse{
        val parseLine = parseLine(line.removeSurrounding("[", "]").iterator())
        return parseLine
    }
    fun parse(input: List<String>): List<Pair<Day07Parse, Day07Parse>> {

        val chunked = input.chunked(3)
        val ss = chunked.map {
            parseLine(it[0]) to parseLine(it[1])
        }

        return ss
    }

    fun part1(input: List<String>): Int {
        val parse = parse(input)
        val ss = parse.mapIndexed {   index, pair ->

            if( pair.first < pair.second)
                index+1
            else
                0

        }.sum()
        return ss
    }

    fun part2(input: List<String>): Int {
        val startPacket = parseLine("[[2]]")
        val endPacket= parseLine("[[6]]")
        val parse = parse(input)
        val packets = parse.flatMap { listOf(it.first, it.second) } + startPacket + endPacket
        val pp = packets.sorted()

        val first = pp.indexOfFirst {
            it == startPacket
        }
        val last = pp.indexOfFirst {
            it == endPacket
        }
        return (first+1)*(last+1)
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




