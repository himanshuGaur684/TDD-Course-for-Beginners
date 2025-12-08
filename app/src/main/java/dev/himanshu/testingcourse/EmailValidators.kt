package dev.himanshu.testingcourse

object EmailValidators {

    fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

}