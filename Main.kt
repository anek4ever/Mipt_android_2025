package org.example

fun Array<Int>.sumCubes(): Int = this.filter { it % 2 != 0 }
    .sumOf { it * it * it }

fun main() {
    val numbers = arrayOf(1, 2, 3, 4)
    println(numbers.sumCubes())
}
