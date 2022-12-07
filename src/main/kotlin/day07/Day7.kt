package day07

import common.Solution
import common.extensions.grabFirstIntOrNull
import common.extensions.lastWord

class Day7: Solution(7) {
    //caching this as building the dir tree is very much the most work
    lateinit var allDirs: List<Dir>

    //runs in 0.043ms
    override fun answer1(input: List<String>): Int{
        allDirs = buildDirTree(input)
        return allDirs.sumOf { it.size.takeIf { s -> s <= MAX_SIZE_Q1 } ?: 0 }
    }

    // runs in 0.0015 ms
    override fun answer2(input: List<String>): Int {
        val currentUsed = allDirs[0].size
        val overfill = (TOTAL_DISK_SPACE - NEEDED_SPACE - currentUsed) * -1

        // This is faster than a single reduce (almost twice as fast), not sure why
        return allDirs.filter { it.size >= overfill }.minBy { it.size }.size
    }

    // Return a list of all Dirs.
    // First is root.
    private fun buildDirTree(lines: List<String>): List<Dir>{
        val root = Dir(null)
        val allDirs = mutableListOf(root)

        var currentDir = root

        lines.forEach {
            when{
                it == "\$ cd /" -> currentDir = root
                it == "\$ ls" -> {} // do nothing
                it == "\$ cd .." -> currentDir = currentDir.parent!!
                it.startsWith("$ cd") -> currentDir = currentDir[it.lastWord()]
                it[0] == 'd' -> allDirs.add (currentDir.addDir(it.lastWord()))
                it[0].isDigit() -> currentDir.addFile(it.grabFirstIntOrNull()!!)
            }
        }
        return allDirs
    }

    companion object{
        private const val MAX_SIZE_Q1 = 100000
        private const val TOTAL_DISK_SPACE = 70000000
        private const val NEEDED_SPACE = 30000000
    }
}