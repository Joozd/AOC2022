package day02

import common.Solution

/*
 * I wanted to use modulo operations for deciding who wins because it saves a lot of `when` statements.
 * It does introduce 1-off problems though, since the card values are (1..3) and the modulo gives (0..2).
 * This is fixed by adding 1 at the end of the 'score' functions.
 */

/*
 * * GAME EXPLANATION *
 *
 * an RPS game is won by the player whose value is 1 higher than the other.
 * Same value means a draw.
 * Since the game is a circle, we can just do mod(3) and this will work for all values.
 * All comments assume mod(3) for all game-related values. (so 1 also means 4 and 1 higher equals 2 lower)
 */
class Day2: Solution(2) {
    override fun answer1(input: List<String>) = input.sumOf{ score(it) }
    override fun answer2(input: List<String>) = input.sumOf{ scorePartTwo(it) }

    // a [match] String is like "B Z"
    private fun score(match: String): Int{
        val elfMove = match[0] - ELF_OFFSET
        val myMove: Int = match[2] - MY_OFFSET

        val matchResult = myMove - elfMove // see GAME EXPLANATION
        return 1 + myMove + (matchResult + 1).mod(3) * 3
        // (matchResult + 1) gives 0 for loss, 1 for draw and 2 for win. Multiplied by 3 is win score.
    }

    private fun scorePartTwo(match: String): Int{
        val elfMove = match[0] - ELF_OFFSET
        val matchResult = match[2] - MY_OFFSET // 0 = lose, 1 = draw, 2 = win
        return 1 + matchResult * 3 + (elfMove + (matchResult-1)).mod(3)
        // (matchResult-1) gives the choice needed for the match to get this result;
        // add 2 for lose, 0 for draw and 1 for win
    }

    companion object{
        const val ELF_OFFSET = 'A'
        const val MY_OFFSET = 'X'
    }
}