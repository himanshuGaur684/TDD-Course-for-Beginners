package dev.himanshu.testingcourse.part3

import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class UserRepositorySuccessImpl : UserRepository {
    override fun getUserName(): Result<String> {
        return Result.success("Himanshu")
    }
}

class GetUserNameUseCaseUnitTest {

    @Test
    fun `should return correct result when getUserName is called`() {
        // Arrange
        val repository = UserRepositorySuccessImpl()
        val useCase = GetUserNameUseCase(repository)

        // Act
        val result = useCase.invoke()

        // Assert
        Assert.assertEquals("Himanshu", result.getOrNull())
    }

    @Test
    fun `should return correct result when getUserName is called mockk`() {
        // Arrange
        val repository = mockk<UserRepository>()
        coEvery { repository.getUserName() } returns Result.success("Himanshu")

        val useCase = GetUserNameUseCase(repository)

        // Act
        val result = useCase.invoke()

        // Assert
        Assert.assertEquals("Himanshu", result.getOrNull())
    }

    @Test
    fun `should return failure result when getUserName is called mockk`() {
        // Arrange
        val repository = mockk<UserRepository>()
        coEvery { repository.getUserName() } returns Result.failure(
            Exception("Error")
        )

        val useCase = GetUserNameUseCase(repository)

        // Act
        val result = useCase.invoke()

        // Assert
        Assert.assertEquals("Error", result.exceptionOrNull()?.message.toString())
    }


}