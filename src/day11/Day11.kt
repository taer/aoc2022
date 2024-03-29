package day11

import java.io.File
import java.lang.IllegalStateException

typealias Operation = (Long) -> Long
typealias Test = (Long) -> Boolean
typealias Target = (Boolean) -> Int

suspend fun main() {

    data class Monkey(val number: Int, val items: MutableList<Long>, val operation: Operation, val division: Long, val test: Test, val target: Target )
    val monkey = """\s*Monkey (\d+):$""".toRegex()
    val items = """\s*Starting items: (.*)$""".toRegex()
    val operation = """\s*Operation: new = old ([+*]) (.*)$""".toRegex()
    val test = """\s*Test: divisible by (\d+)$""".toRegex()
    val theTrue = """\s*If true: throw to monkey (\d+)$""".toRegex()
    val thefalse = """\s*If false: throw to monkey (\d+)$""".toRegex()
    fun parseMonket(it: String): Monkey{
        val lines = it.lines()
        println(lines[0])
        val (monkeyNum) =  monkey.matchEntire(lines[0])!!.destructured
        val (itemlist) = items.matchEntire(lines[1])!!.destructured
        val (operator, target   ) = operation.matchEntire(lines[2])!!.destructured
        val (divisibleBy) = test.matchEntire(lines[3])!!.destructured
        val (trueTaget) = theTrue.matchEntire(lines[4])!!.destructured
        val (falseTarget) = thefalse.matchEntire(lines[5])!!.destructured
        println("Done w/ monkey $monkeyNum ")

        val addOperator = { x: Long, y: Long -> x + y }
        val mulOperator = { x: Long, y: Long -> x * y }

        val op = when(operator){
            "+" -> addOperator
            "*" -> mulOperator
            else -> throw IllegalStateException("Unknown operator $operator")
        }
        val operation= if (target == "old") {
            { x: Long ->
                op(x, x)
            }
        } else {
            val toInt = target.toLong()
            ({ x: Long ->
                op(x, toInt)
            })
        }
        val regex = """,\s+""".toRegex()
        val items = itemlist.split(regex)
            .map { it.toLong() }
        val test = divisibleBy.toLong()

        val trueMonkey = trueTaget.toInt()
        val falseMonkey = falseTarget.toInt()
        val testFunc = {
                value: Long -> value % test == 0L
        }
        val results = {
                result: Boolean -> if(result)trueMonkey else falseMonkey
        }
        return Monkey(monkeyNum.toInt(), items.toMutableList(),operation, test , testFunc, results)
    }

    fun monkeyIteration(
        monkeys: List<Monkey>,
        iterations: Int,
        worryRuduction: Int,
    ): List<Long> {
        val magic = monkeys.map { it.division }.reduce{x,y->x*y}
        val inspections = List(monkeys.size) { 0L }.toMutableList()
        repeat(iterations) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    inspections[monkey.number]++
                    val item = monkey.items.removeFirst()
                    val worry = monkey.operation(item)
                    val reducedWorry = (worry % magic )/ worryRuduction

                    val result = monkey.test(reducedWorry)
                    val tossTo = monkey.target(result)
                    monkeys[tossTo].items.add(reducedWorry)
                }

            }
                println("After round $it, the monkeys are holding items with these worry levels:")
                monkeys.forEach { monkey ->
                    println("Monkey ${monkey.number}: ${monkey.items.joinToString()}")
                }
        }
        return inspections
    }

    fun parseMonkeys(input: String): List<Monkey> {
        val baseMonkey = input.split(System.lineSeparator().repeat(2))

        val monkeys = baseMonkey.map {
            parseMonket(it)
        }.sortedBy { it.number }
        return monkeys
    }

    suspend fun part1(input: String): Long {
        val monkeys = parseMonkeys(input)

        val inspections = monkeyIteration(monkeys, 20, 3)

        val top2 = inspections.toList().sortedDescending().take(2)
        val l = top2[0] * top2[1]
        return l
    }

    fun part2(input: String): Long {
        val monkeys = parseMonkeys(input)

        val inspections = monkeyIteration(monkeys, 10000, 1)

        val top2 = inspections.toList().sortedDescending().take(2)
        val l = top2[0] * top2[1]
        return l
    }

    val testInput = File(/* parent = */ "src/${"day11"}", /* child = */ "data_test.txt").readText()
    val input = File("src/${"day11"}", "data.txt").readText()
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)


    println(part1(input))
    println(part2(input))

//    check(part1(input) == 11960)
//    check(part2(input) == 11960)
}



