package y2020.day2

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class PasswordPolicyTest : FreeSpec({

    "password policy" - {
        "example passwords" {
            PasswordPolicy().countValid(listOf("1-3 a: abcde")) shouldBe 1
            PasswordPolicy().countValid(listOf("1-3 b: cdefg")) shouldBe 0
            PasswordPolicy().countValid(listOf("2-9 c: ccccccccc")) shouldBe 1
        }
    }

    "second password policy" - {
        "example passwords" {
            PasswordPolicy().countValid2(listOf("1-3 a: abcde")) shouldBe 1
            PasswordPolicy().countValid2(listOf("1-3 b: cdefg")) shouldBe 0
            PasswordPolicy().countValid2(listOf("2-9 c: ccccccccc")) shouldBe 0
        }
    }

})