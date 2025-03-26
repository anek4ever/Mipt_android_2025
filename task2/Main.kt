package org.example

class TreeNode(val value: Int, var left: TreeNode? = null, var right: TreeNode? = null)

fun insert(root: TreeNode?, value: Int): TreeNode {
    if (root == null) return TreeNode(value)
    if (value < root.value) {
        root.left = insert(root.left, value)
    } else {
        root.right = insert(root.right, value)
    }
    return root
}

fun buildBST(numbers: List<Int>): TreeNode? {
    var root: TreeNode? = null
    for (num in numbers) {
        root = insert(root, num)
    }
    return root
}

fun inorderTraversal(root: TreeNode?) {
    if (root != null) {
        inorderTraversal(root.left)
        print("${root.value} ")
        inorderTraversal(root.right)
    }
}

fun main() {
    val numbers = listOf(5, 3, 7, 2, 4, 6, 8)
    val bstRoot = buildBST(numbers)
    inorderTraversal(bstRoot) // Выведет: 2 3 4 5 6 7 8
}
