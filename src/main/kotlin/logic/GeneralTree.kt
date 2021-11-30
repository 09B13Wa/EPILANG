package logic

import java.util.*
import kotlin.collections.ArrayList

class GeneralTree<TreeType> (key: TreeType) {
    private var key: TreeType
    private var children: MutableList<GeneralTree<TreeType>>
    private var numberOFChildren: Int

    val value: TreeType
        get() = key

    val size: Int
        get() = numberOFChildren

    val isEmpty: Boolean
        get() = numberOFChildren == 0

    val isLeaf: Boolean
        get() = numberOFChildren == 0

    // constructor
    init {
        this.key = key
        children = ArrayList()
        numberOFChildren = 0
    }

    //constructor with children
    constructor(key: TreeType, children: MutableList<GeneralTree<TreeType>>) : this(key) {
        this.children = children
        this.numberOFChildren = children.size
    }

    fun addChild(child: GeneralTree<TreeType>) {
        children.add(child)
        numberOFChildren++
    }

    fun getChildren(): MutableList<GeneralTree<TreeType>> {
        return children
    }

    fun getKey(): TreeType {
        return key
    }

    fun getNumberOfChildren(): Int {
        return numberOFChildren
    }

    fun getChildByKey(key: TreeType): GeneralTree<TreeType>? {
        for (child in children) {
            if (child.getKey() == key) {
                return child
            }
        }
        return null
    }

    fun getChildByIndex(index: Int): GeneralTree<TreeType> {
        return children[index]
    }

    //remove child
    fun removeChild(child: GeneralTree<TreeType>) {
        children.remove(child)
        numberOFChildren--
    }

    //add child with value
    fun addChild(value: TreeType) {
        children.add(GeneralTree(value))
        numberOFChildren++
    }

    //remove child with value
    fun removeChild(value: TreeType) {
        for (child in children) {
            if (child.getKey() == value) {
                children.remove(child)
                numberOFChildren--
                break
            }
        }
    }

    //depth-first-search with prefix, intermediate and suffix functions as parameters
    fun dfs(prefix: (TreeType) -> Unit, intermediate: (TreeType) -> Unit, suffix: (TreeType) -> Unit) {
        prefix(key)
        //iterate through children except last
        for (i in 0 until numberOFChildren - 1) {
            children[i].dfs(prefix, intermediate, suffix)
            intermediate(key)
        }

        children[numberOFChildren - 1].dfs(prefix, intermediate, suffix)
        suffix(key)
    }

    //breadth-first-search with function as parameter using queue class
    fun bfs(function: (TreeType) -> Unit) {
        val queue: Queue<GeneralTree<TreeType>> = Queue()
        queue.enqueue(this)
        while (!queue.isEmpty()) {
            val current = queue.dequeue()
            function(current.key)
            for (child in current.children) {
                queue.enqueue(child)
            }
        }
    }

    //test whether tree equals another tree recursively
    fun equals(other: GeneralTree<TreeType>): Boolean {
        if (this.key != other.key) {
            return false
        }
        if (this.numberOFChildren != other.numberOFChildren) {
            return false
        }
        for (i in 0 until numberOFChildren) {
            if (!this.children[i].equals(other.children[i])) {
                return false
            }
        }
        return true
    }

}