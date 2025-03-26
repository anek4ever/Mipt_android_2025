package org.example

class MaxStack {
    private val stack = mutableListOf<Int>()
    private val maxStack = mutableListOf<Int>()

    fun push(value: Int) {
        stack.add(value)
        if (maxStack.isEmpty() || value >= maxStack.last()) {
            maxStack.add(value)
        }
    }

    fun pop(): Int? {
        if (stack.isEmpty()) return null
        val removed = stack.removeAt(stack.size - 1)
        if (removed == maxStack.last()) {
            maxStack.removeAt(maxStack.size - 1)
        }
        return removed
    }

    fun max(): Int? {
        return maxStack.lastOrNull()
    }
}

fun main() {
    val stack = MaxStack()
    stack.push(5)
    stack.push(1)
    stack.push(10)
    stack.push(3)

    println(stack.max()) // 10
    stack.pop()
    println(stack.max()) // 10
    stack.pop()
    println(stack.max()) // 5
}
