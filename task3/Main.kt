package org.example

fun filterDigits(str: String, allowed: Set<Char>): String {
    return str.filter { it in allowed }
}

fun main() {
    val str = "1234567890798"
    val allowed = setOf('1', '3', '5', '7', '9')

    val result = filterDigits(str, allowed)
    println(result)
}
