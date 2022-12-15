package y2022.day15

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe


class Day15Test : FreeSpec({

    "integrate ranges" - {
        "add to empty list" {
            integrate(emptyList(), 1..2) shouldBe listOf(1..2)
        }

        "new range out of bounds of existing ranges" - {
            "add to nonempty list as last element" {
                integrate(listOf(-3..-2, 1..2), 5..6) shouldBe listOf(-3..-2, 1..2, 5..6)
            }
            "add to nonempty list as last neighboring element" {
                integrate(listOf(-3..-2, 1..4), 5..6) shouldBe listOf(-3..-2, 1..6)
            }
            "add to nonempty list as first element" {
                integrate(listOf(10..12, 15..17), 5..6) shouldBe listOf(5..6, 10..12, 15..17)
            }
            "add to nonempty list as first neighboring element" {
                integrate(listOf(10..12, 15..17), 5..9) shouldBe listOf(5..12, 15..17)
            }
        }

        "add non overlapping element in the middle of the list" {
            integrate(listOf(1..2, 7..9, 14..20), 4..5) shouldBe
                    listOf(1..2, 4..5, 7..9, 14..20)
        }

        "both ends in existing ranges" - {
            "add fully included element" {
                integrate(listOf(1..10), 3..5) shouldBe listOf(1..10)
            }
            "add element joining two elements" {
                integrate(listOf(1..5, 10..20, 30..40), 17..32) shouldBe
                        listOf(1..5, 10..40)
            }
            "add element overlapping fully existing range" {
                integrate(listOf(1..5, 10..15, 20..30, 35..40), 14..36) shouldBe
                        listOf(1..5, 10..40)
            }

        }


        "add overlapping element" {
            integrate(listOf(1..3), 3..5) shouldBe listOf(1..5)
        }

        // pomiedzy
        // nachodzi na jeden
        // nachodzi na 2
        // obejmuje 1
        // nachodzi na 2 i obejmuje 1

    }
})