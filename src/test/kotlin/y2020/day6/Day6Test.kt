package y2020.day6

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day6Test : FreeSpec({
    "example data" {
        val input = listOf(
            "abc",
            "",
            "a",
            "b",
            "c",
            "",
            "ab",
            "ac",
            "",
            "a",
            "a",
            "a",
            "a",
            "",
            "b"
        )
        countAnswers(input) shouldBe 11
    }

    "example data part two" {
        val input = listOf(
            "abc",
            "",
            "a",
            "b",
            "c",
            "",
            "ab",
            "ac",
            "",
            "a",
            "a",
            "a",
            "a",
            "",
            "b"
        )
        countEveryoneAnswers(input) shouldBe 6
    }
})
