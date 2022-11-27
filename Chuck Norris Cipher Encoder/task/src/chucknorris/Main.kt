package chucknorris

import java.util.*

val scanner = Scanner(System.`in`)

fun main() {
    println("Input string:")
    val input = scanner.nextLine()
    val letters = input.toList()
    println(letters.joinToString(" "))
}