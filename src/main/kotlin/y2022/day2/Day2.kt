package y2022.day2

import common.printSolutions

enum class RPS {
    Rock, Paper, Scissors;

    fun value() = when (this) {
        Rock -> 1
        Paper -> 2
        Scissors -> 3
    }
}

fun fromString(input: String): RPS = when (input) {
    "A" -> RPS.Rock
    "X" -> RPS.Rock
    "B" -> RPS.Paper
    "Y" -> RPS.Paper
    "C" -> RPS.Scissors
    "Z" -> RPS.Scissors
    else -> throw RuntimeException()
}

fun outcomeForPlayer2(player1: RPS, player2: RPS): Int =
    if (player1 == player2)
        3
    else when (player2) {
        RPS.Rock -> if (player1 == RPS.Paper) 0 else 6
        RPS.Scissors -> if (player1 == RPS.Rock) 0 else 6
        RPS.Paper -> if (player1 == RPS.Scissors) 0 else 6
    }

fun scoreForPlayer2(player1: RPS, player2: RPS): Int = player2.value() + outcomeForPlayer2(player1, player2)


fun task1(input: List<String>) =
    input.sumOf { line -> line.split(" ").let { scoreForPlayer2(fromString(it[0]), fromString(it[1])) } }

fun pickHandForOutcome(player1: RPS, expectedOutcome: String): RPS =
    when (expectedOutcome) {
        "Y" -> { //draw
            player1
        }

        "X" -> { // loose
            when (player1) {
                RPS.Rock -> RPS.Scissors
                RPS.Scissors -> RPS.Paper
                RPS.Paper -> RPS.Rock
            }
        }

        "Z" -> { // win
            when (player1) {
                RPS.Rock -> RPS.Paper
                RPS.Scissors -> RPS.Rock
                RPS.Paper -> RPS.Scissors
            }
        }

        else -> throw RuntimeException()
    }


fun task2(input: List<String>) =
    input.sumOf { line -> line.split(" ").let { scoreForPlayer2(fromString(it[0]), pickHandForOutcome(fromString(it[0]), it[1])) } }

fun main() = printSolutions(2, 2022, { input -> task1(input) }, { input -> task2(input) })