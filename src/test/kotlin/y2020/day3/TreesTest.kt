package y2020.day3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class TreesTest : FreeSpec({
    "example data" {
        val input = listOf(
            "..##.......",
            "#...#...#..",
            ".#....#..#.",
            "..#.#...#.#",
            ".#...##..#.",
            "..#.##.....",
            ".#.#.#....#",
            ".#........#",
            "#.##...#...",
            "#...##....#",
            ".#..#...#.#"
        )
        Trees().count(input) shouldBe 7
    }
})
