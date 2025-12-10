package dev.himanshu.testingcourse.part3

interface UserRepository {
    fun getUserName(): Result<String>
}