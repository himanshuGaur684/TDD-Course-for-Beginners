package dev.himanshu.testingcourse.part4

interface CounterRepository {
    fun getCount(): Int
    fun increment(): Int
    fun decrement(): Int
    suspend fun reset()
    suspend fun saveCountInDb(count: Int)
    suspend fun loadFromNetwork(callback: suspend (Int) -> Unit)
}
