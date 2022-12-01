package y2016.day5

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

class MD5Password {

    fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))

    fun find(input: String) {
        var increment: Long = 0
        var password = "xxxxxxxx".toCharArray()
        while (password.count { it == 'x'} > 0) {
            val hash = md5("$input$increment").toHex()
            if (increment % 10000 == 0L) println("$increment $hash ${String(password)}")
            if (hash.startsWith("00000") && hash[5].isDigit() && hash[5].toString().toInt() < 8 && password[hash[5].toString().toInt()] == 'x') {
                password[hash[5].toString().toInt()] = hash[6]
                println(String(password))
            }
            increment++
        }
    }

}

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

fun main() {
    MD5Password().find("cxdnnyjw")
}