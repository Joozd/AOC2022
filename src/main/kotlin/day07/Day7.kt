package day07

import common.Solution
import common.extensions.grabInts
import common.extensions.lastWord

class Day7: Solution(7) {
    override fun answer1(input: List<String>) = getAnswerWithDirStruct(input)

    override fun answer2(input: List<String>) = input


    private fun getAnswerWithDirStruct(lines: List<String>): Int{
        val root = Dir(null)
        val allDirs = mutableListOf(root)

        var currentDir = root
        lines.forEach {
            println(it)
            when{
                it == "\$ cd /" -> currentDir = root
                it == "\$ ls" -> {} // do nothing
                it == "\$ cd .." -> currentDir = currentDir.parent!!
                it.startsWith("$ cd") -> currentDir = currentDir[it.lastWord()]
                it[0] == 'd' -> allDirs.add (currentDir.addDir(it.lastWord()))
                it[0].isDigit() -> currentDir.addFile(it.lastWord(), it.grabInts().first())
            }
        }

        return root.size()
    }
}