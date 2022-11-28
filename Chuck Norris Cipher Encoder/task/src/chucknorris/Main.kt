package chucknorris

import java.util.*
import kotlin.math.pow

val scanner = Scanner(System.`in`)

class ChunkNorrisCipherEncoder {
    fun cmd() {
        while (true) {
            println("Please input operation (encode/decode/exit):")
            when (val operation = scanner.nextLine()) {
                "encode" -> encode()

                "decode" -> decode()

                "exit" -> break

                else -> println("There is no '$operation' operation")
            }
        }
        println("Bye!")
    }

    private fun encode() {
        println("Input string:")
        val string = scanner.nextLine()
        val binary = string.binaryEncode()
        val blocks = mutableListOf<String>()
        var index = 0
        while (index < binary.length) {
            val firstBlock: String
            val secondBlock: String
            val lastIndex: Int
            if (binary[index] == '0') {
                firstBlock = "00"
                lastIndex = binary.indexOf('1', index)
            } else {
                firstBlock = "0"
                lastIndex = binary.indexOf('0', index)
            }
            val size = if (lastIndex == -1) binary.length - index else lastIndex - index
            secondBlock = "0".repeat(size)
            blocks.add("$firstBlock $secondBlock")
            index = if (lastIndex == -1) binary.length else lastIndex
        }
        val encoded = blocks.joinToString(" ")
        println("Encoded string:\n$encoded")
    }

    private fun decode() {
        println("Input encoded string:")
        val binary = scanner.nextLine()
        if (Regex("(0|00)\\s0+(\\s(0|00)\\s0+)*").matches(binary)) {
            val blocks = binary.split(" ")
            var decodedBin = ""
            for (i in 1..blocks.lastIndex step 2) decodedBin += "${if (blocks[i - 1] == "00") '0' else '1'}".repeat(
                blocks[i].length
            )
            if (decodedBin.length % 7 == 0) {
                val unicodes = mutableListOf<Int>()
                for (i in 0 until decodedBin.length / 7) {
                    val index = i * 7
                    val byte = decodedBin.substring(index..index + 6).toInt().byteToInt()
                    unicodes.add(byte)
                }
                var decoded = ""
                unicodes.forEach { decoded += it.toChar() }
                println("Decoded string:\n$decoded")
            } else println("Encoded string is not valid.")
        } else println("Encoded string is not valid.")
    }
}

private fun Int.byteToInt(): Int {
    var b = this
    var i = 0
    var c = 0
    while (b > 0) {
        i += (b % 10) * 2.0.pow(c++).toInt()
        b /= 10
    }
    return i
}

private fun String.binaryEncode(): String {
    val binaryCodes = mutableListOf<String>()
    this.forEach {
        val unicode = Integer.toBinaryString(it.code)
        val string = String.format("%7s", unicode).replace(" ", "0")
        binaryCodes.add(string)
    }
    return binaryCodes.joinToString("")
}

fun main() {
    val encoder = ChunkNorrisCipherEncoder()
    encoder.cmd()
}
