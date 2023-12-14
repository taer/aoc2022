package day14

import Point
import readInput


fun main() {

    fun makeLine(it: String): List<Point> {
        val split = it.split(" -> ")
        return split.map { it.split(",") }.map { Point(it[0].toInt(), it[1].toInt()) }
    }

    fun lineOf(from: Point, to: Point): Sequence<Point> {
        val mover = if(from.x == to.x){
            if(from.y < to.y){
                { p:Point -> p.copy(y =p.y +1)}
            }else{
                { p:Point -> p.copy(y =p.y -1)}
            }
        }else{
            if(from.x < to.x){
                { p:Point -> p.copy(x =p.x +1)}
            }else{
                { p:Point -> p.copy(x =p.x -1)}
            }
        }
        return sequence {
            var current = from
            while (current != to){
                yield(current)
                current = mover(current)
            }
            yield(to)
        }


    }

    val sandOrigin = Point(500,0)
    val sandDown = { p:Point -> p.copy(y=p.y+1)}
    val sandDownLeft = { p:Point -> p.copy(x=p.x-1, y=p.y+1)}
    val sandDownRight = { p:Point -> p.copy(x=p.x+1, y=p.y+1)}
    fun sandMove(grid: Array<Array<Char>>, location: Point): Point? {
        val d = sandDown(location)
        if (grid[d.y][d.x] == '.') {
            return d
        }
        val l = sandDownLeft(location)
        if (grid[l.y][l.x] == '.') {
            return l
        }
        val r = sandDownRight(location)
        if (grid[r.y][r.x] == '.') {
            return r
        }
        return null
    }


    fun setPoint(grid: Array<Array<Char>>, it: Point, toWhat: Char) {
        grid[it.y][it.x] = toWhat
    }
    fun prinGrid(grid: Array<Array<Char>>, mixX: Int) {
        grid.forEach {
            println(it.drop(mixX - 1).joinToString(""))
        }
    }
    fun dropSand(grid: Array<Array<Char>>, maxY: Int) {

        while(true){
            var sandMan =  sandMove(grid, sandOrigin)
            var deadMan:Point = sandOrigin
            if(sandMove(grid, sandOrigin)==null){
                setPoint(grid, sandOrigin, 'o')
                return
            }
            while(sandMan != null){
                deadMan = sandMan
                sandMan = sandMove(grid, sandMan)
                if(sandMan?.y == maxY){
                    return
                }
            }
            if(deadMan.y == maxY){
                return
            }
            setPoint(grid, deadMan, 'o')
        }
    }




    fun part1(input: List<String>): Int {
        val zz = input.flatMap {
            makeLine(it)
        }
        val maxY = zz.maxOf { it.y }
        val maxX = zz.maxOf { it.x }
        val mixX = zz.minOf { it.x }

        val grid = Array(maxY+10){
            Array(maxX+10){
                '.'
            }
        }
        val xx = input.forEach {
            val makeLine = makeLine(it)
            for(i in 1 until makeLine.size){
                val from = makeLine[i-1]
                val to = makeLine[i]
                 lineOf(from, to).forEach {
                     setPoint(grid, it, '#')
                 }

            }
        }
//        prinGrid(grid, mixX)

        dropSand(grid, maxY+1)

        println()
        println()
        println()
//        prinGrid(grid, mixX)

        return grid.sumOf { it.count { it == 'o' } }
    }

    fun part2(input: List<String>): Int {
        val zz = input.flatMap {
            makeLine(it)
        }
        val maxY = zz.maxOf { it.y }
        val maxX = zz.maxOf { it.x }
        val mixX = zz.minOf { it.x }

        val grid = Array(maxY+10){
            Array(maxX+1000){
                '.'
            }
        }
        val xx = input.forEach {
            val makeLine = makeLine(it)
            for(i in 1 until makeLine.size){
                val from = makeLine[i-1]
                val to = makeLine[i]
                lineOf(from, to).forEach {
                    setPoint(grid, it, '#')
                }

            }
        }
        grid[maxY+2].fill('#')
//        prinGrid(grid, mixX)

        dropSand(grid, maxY+10)

        println()
        println()
        println()
//        prinGrid(grid, mixX-20)

        return grid.sumOf { it.count { it == 'o' } }
    }

    val testInput = readInput("day14", true)
    val input = readInput("day14")

    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    println(part1(input))
    println(part2(input))
    check(part1(input) == 578)
    check(part2(input) == 24805)
}




