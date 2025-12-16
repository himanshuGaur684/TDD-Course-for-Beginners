package dev.himanshu.testingcourse.part4

import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import io.mockk.spyk
import io.mockk.unmockkConstructor
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.verifyOrder
import io.mockk.verifySequence
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class CounterUseCaseUnitTest {

    @Test
    fun whenIncrementCalled_shouldTriggerTheIncrementOfRepository() {
        // Arrange
        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        every { repository.increment() } returns 10

        // Act
        useCase.increase()

        // Assertion
        verify(exactly = 1) { repository.increment() }
    }

    @Test
    fun whenResetCalled_shouldTriggerTheResetOfRepository() = runTest {
        // Arrange
        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        coEvery { repository.reset() } returns Unit

        // Action
        useCase.resetCounter()

        // Assertion
        coVerify(exactly = 1) { repository.reset() }

    }

    @Test
    fun whenIncrementAndDecreseCalled_shouldTriggerTheIncrementAndDecrementOfRepositoryInOrder() {
        // Arrange
        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        every { repository.increment() } returns 1
        every { repository.decrement() } returns 0
        every { repository.getCount() } returns 12

        // Act
        useCase.increase()
        useCase.decrease()

        // Assertion

        verifyOrder {
            repository.increment()
            repository.decrement()
        }
    }

    @Test
    fun whenIncrementAndDecreseCalled_shouldTriggerTheIncrementAndDecrementOfRepositoryInSequence() {
        // Arrange
        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        every { repository.increment() } returns 1
        every { repository.decrement() } returns 0
        every { repository.getCount() } returns 12

        // Act
        useCase.increase()
        useCase.decrease()

        // Assertion

        verifySequence {
            repository.increment()
            repository.decrement()
        }
    }

    @Test
    fun whenConditionResetIsCalled_shouldTriggerResetOfRepository() = runTest {
        // Arrange
        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        mockkObject(AppConfig)
        coEvery { repository.reset() } returns Unit
        every { AppConfig.shouldAutoReset } returns true

        // Act
        useCase.conditionalReset()

        // Assertion
        coVerify(exactly = 1) { repository.reset() }

        unmockkObject(AppConfig)
    }


    @Test
    fun whenComputeWithHelperCalled_shouldReturnCorrectValue() {
        // Arrange
        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        mockkConstructor(CounterHelper::class)

        every { repository.getCount() } returns 10

        every { anyConstructed<CounterHelper>().multiply(any()) } returns 999

        // Act

        val result = useCase.computeWithHelper()

        // Assertion
        Assert.assertEquals(999, result)

        unmockkConstructor(CounterHelper::class)
        clearMocks(repository)

    }


    @Test
    fun whenGetCountFromNetworkAndPersistCalled_shouldReturnCorrectValue() = runTest {

        // Arrange
        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        coEvery { repository.loadFromNetwork(any()) } coAnswers {
            val callBack = arg<suspend (Int) -> Unit>(0)
            callBack(200)
        }

        var networkCount = -1

        // Action
        val result = useCase.getCountFromNetworkAndPersist {
            networkCount = it
        }

        // Assertion
        Assert.assertEquals(200, networkCount)
    }

    @Test
    fun whenComplexComputationCalled_shouldReturnTheCorrectValue() {

        val repository = mockk<CounterRepository>()
        val useCase = CounterUseCase(repository)

        val spy = spyk<CounterHelper>()

        mockkConstructor(CounterHelper::class)

        every { repository.getCount() } returns 10

        every { anyConstructed<CounterHelper>().subtract(any()) } answers {
            spy.subtract(firstArg())
        }

        every { anyConstructed<CounterHelper>().added(any()) } answers {
            spy.added(firstArg())
        }

        // Action
        val result = useCase.complexComputation()

        // Assertion

        verify { spy.subtract(10) }
        verify { spy.added(0) }

        Assert.assertEquals(10, result)


    }


}