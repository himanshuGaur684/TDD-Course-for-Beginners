package dev.himanshu.testingcourse.part3

class GetUserNameUseCase(private val repository: UserRepository) {
    operator fun invoke() = repository.getUserName()
}