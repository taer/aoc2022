package day12

import readInput

data class Point(val x: Int, val y: Int) {
    fun cardinals() =
        setOf(
            copy(x = x + 1),
            copy(x = x - 1),
            copy(y = y + 1),
            copy(y = y - 1),
        )
}

suspend fun main() {

    class TheMap(val data: Map<Point, Int>, val start:Point, val end:Point){

        fun travelShort(): Int {
            val starts = data.filter { it.value == 0 }.keys
            return starts.map { travelInner(it) }.min()

        }
        fun travel(): Int {
            return travelInner(start)
        }
        fun travelInner(theStart: Point): Int {
            class PathList(val point: Point, val cost:Int)

            val visited = mutableSetOf<Point>()
            val queue = mutableListOf(PathList(theStart, 0))

            while (queue.isNotEmpty()){
                val removeFirst = queue.removeFirst()
                if(removeFirst.point !in visited){
                    visited.add(removeFirst.point)
                    val potentials = removeFirst.point.cardinals()
                        .filter { it in data }
                        .filter {
                            data.getValue(it) - data.getValue(removeFirst.point) <= 1
                        }
                    if(potentials.any{ it == end }) return removeFirst.cost+1
                    queue.addAll(potentials.map { PathList(it, removeFirst.cost+1) })
                }
            }
            return Int.MAX_VALUE
        }
    }

    fun parse(list: List<String>): TheMap {
        var start:Point?=null
        var end:Point?=null
        val xx = list.flatMapIndexed { x, row ->
            row.mapIndexed{y, data ->
                val point = Point(x,y)
                point to when(data){
                    'S' -> {
                        start=point
                        0
                    }
                    'E' -> {
                        end=point
                        25
                    }
                    else-> data-'a'
                }
            }
        }.toMap()


        return TheMap(xx,requireNotNull(start),requireNotNull(end))
    }

    fun part1(input: List<String>): Int {
        val parse = parse(input)
        return parse.travel()
    }

    fun part2(input: List<String>): Int {
        val parse = parse(input)
        return parse.travelShort()
    }

    val testInput = readInput("day12", true)
    val input = readInput("day12")


    println(part1(input))
    println(part2(input))

//    check(part1(input) == 11960)
//    check(part2(input) == 11960)
}



