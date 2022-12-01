package y2020.day1

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Sum2020Test : FreeSpec({

    "sum 2020" - {

        "should work for simple case" {
            Sum2020().find(
                listOf(
                    1000,
                    1020
                )
            ) shouldBe 1020000
        }

        "should work for example" {
            Sum2020().find(
                listOf(
                    1721,
                    979,
                    366,
                    299,
                    675,
                    1456
                )
            ) shouldBe 514579
        }
    }

    "sum 2020 three" - {
        "should work for example" {
            Sum2020().findThree(
                listOf(
                    1721,
                    979,
                    366,
                    299,
                    675,
                    1456
                )
            ) shouldBe 241861950
        }
    }

})