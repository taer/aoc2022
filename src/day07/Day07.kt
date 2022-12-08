package day07

import readInput


sealed class FS(){
    class Directory(val name: String, val parent: Directory?, val children: MutableList<FS> = mutableListOf()): FS(){
        override fun toString(): String {
            return "dir: $name"
        }
    }
    class File(val name: String, val size: Int): FS(){
        override fun toString(): String {
            return "file: $name"
        }
    }
}
fun FS.Directory.files(): List<FS.File>{
    return this.children.flatMap{
        when (it){
            is FS.Directory -> it.files()
            is FS.File -> listOf(it)
        }
    }
}
fun FS.Directory.dirs(): List<FS.Directory> {
    return children.flatMap {
        when (it) {
            is FS.Directory -> it.dirs() + it
            is FS.File -> listOf()
        }
    }
}
fun main() {

    val part1Limit = 100000

    fun parseMessy(input: List<String>): FS.Directory {
        val root = FS.Directory("/", null, mutableListOf())
        var curr = root
        input.drop(1).forEach {
            when (it.first()) {
                '$' -> {
                    if (it == "$ ls") {
                        //cheaty noop
                    } else if (it == "$ cd ..") {
                        curr = curr.parent!!
                    } else {
                        val target = it.replace("$ cd ", "")
                        curr = curr.children.filterIsInstance<FS.Directory>().find { it.name == target }!!
                    }

                }

                else -> {
                    if (it.startsWith("dir")) {
                        curr.children.add(FS.Directory(it.replace("dir ", ""), parent = curr))
                    } else {
                        val split = it.split(" ")
                        val size = split[0].toInt()
                        val name = split[1]
                        curr.children.add(FS.File(name, size))
                    }
                }
            }
        }
        return root
    }

    fun part1(input: List<String>): Int {
        val root = parseMessy(input)
        val xx = root.dirs()
            .map {
                it.files().sumOf { it.size }
            }.filter { it < part1Limit }
            .sum()
        return xx
    }


    val totalSize = 70000000
    val neededFreeSpace = 30000000
    fun part2(input: List<String>): Int {
        val root = parseMessy(input)
        val usedSapce = root.files().sumOf { it.size }
        val currentFreeSapace = totalSize-usedSapce
        val needToFree = neededFreeSpace - currentFreeSapace
        val xx = root.dirs()
            .map {
                it.files().sumOf { it.size }
            }
        return xx.first { it > needToFree }
    }

    val testInput = readInput("day07", true)
    val input = readInput("day07")



    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    println(part1(input))
    println(part2(input))
    check(part1(input) == 1306611)
    check(part2(input) == 13210366)
}
