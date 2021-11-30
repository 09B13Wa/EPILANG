package logic

class Queue<ValueType> {
    private val queue: MutableList<ValueType> = mutableListOf()

    fun enqueue(value: ValueType) {
        queue.add(value)
    }

    fun dequeue(): ValueType {
        return queue.removeAt(0)
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }
}