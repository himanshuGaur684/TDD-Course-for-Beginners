package dev.himanshu.testingcourse.part1

object EmailValidators {

    fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

}