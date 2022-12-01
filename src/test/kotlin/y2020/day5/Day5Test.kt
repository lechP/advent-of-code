package y2020.day5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day5Test : FreeSpec({
    "example data" {
        "FBFBBFFRLR".toSeatId() shouldBe 357
        "BFFFBBFRRR".toSeatId() shouldBe 567
        "FFFBBBFRRR".toSeatId() shouldBe 119
        "BBFFBBFRLL".toSeatId() shouldBe 820
    }


})
