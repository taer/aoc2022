package day06

import java.io.File


fun main() {

    fun findWindowed(input: String, i: Int): Int {
        input.windowed(i).forEachIndexed { index, s ->
            if (s.toSet().size == i) {
                return i + index
            }
        }
        return 0
    }

    fun part1(input: String): Int {
        return findWindowed(input, 4)

    }

    fun part2(input: String): Int {
        return findWindowed(input, 14)
    }

    val input = File("src/day06", "data.txt").readText()

    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)


    check(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)
//
    check(part1(input) == 1723)
    check(part2(input)==3708)
    println(part1(input))
    println(part2(input))
}
