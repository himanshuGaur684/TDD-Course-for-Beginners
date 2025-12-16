package dev.himanshu.testingcourse.part4

class CounterUseCase(
    private val repository: CounterRepository
) {
    fun loadCount(): Int = repository.getCount()

    fun increase(): Int {
        val result = repository.increment()
        loadCount()
        return result
    }

    fun decrease(): Int = repository.decrement()

    suspend fun resetCounter() {
        repository.reset()
    }

    suspend fun persist(count: Int) {
        repository.saveCountInDb(count)
    }

    suspend fun getCountFromNetworkAndPersist(onResult: (Int) -> Unit) {
        repository.loadFromNetwork { count -> onResult(count) }
    }

    fun computeWithHelper(): Int {
        val helper = CounterHelper()
        val current = repository.getCount()
        return helper.multiply(current)
    }

    fun complexComputation(): Int {
        val helper = CounterHelper()
        val current = repository.getCount()
        val sub = helper.subtract(current)
        val added = helper.added(sub)
        return added
    }

    suspend fun conditionalReset() {
        if (AppConfig.shouldAutoReset) {
            repository.reset()
        }
    }
}
