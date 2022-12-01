/*
 *  Uncomment lines that are active
 */
fun main() {
    println("Welcome to Joozd's AOC 2021 solution runner.")
    println("Please tell us what day you want to run: ")
    when (readLine()) {
        "0" -> day00.Day0().runTimed()
        "1" -> day01.Day1().runTimed()
        // "2" -> day02.Day2().runTimed()
        // "3" -> day03.Day3().runTimed()
        // "4" -> day04.Day4().runTimed()
        // "5" -> day05.Day5().runTimed()
        // "6" -> day06.Day6().runTimed()
        // "7" -> day07.Day7().runTimed()
        // "8" -> day08.Day8().runTimed()
        // "9" -> day09.Day9().runTimed()
        // "10" -> day10.Day10().runTimed()
        // "11" -> day11.Day11().runTimed()
        // "12" -> day12.Day12().runTimed()
        // "13" -> day13.Day13().runTimed()
        // "14" -> day14.Day14().runTimed()
        // "15" -> day15.Day15().runTimed()
        // "16" -> day16.Day16().runTimed()
        // "17" -> day17.Day17().runTimed()
        // "18" -> day18.Day18().runTimed()
        // "19" -> day19.Day19().runTimed()
        // "20" -> day20.Day20().runTimed()
        // "21" -> day21.Day21().runTimed()
        // "22" -> day22.Day22().runTimed()
        // "23" -> day23.Day23().runTimed()
        // "24" -> day24.Day24().runTimed()
        // "25" -> day25.Day25().runTimed()

        else -> println("Day not found, exiting")
    }
}