package dev.himanshu.testingcourse

import org.junit.Assert
import org.junit.Test

class EmailValidatorsUnitTests {

    @Test
    fun whenEmailIsValid_shouldReturnTrue() {
        val email = "him@gmail.com"
        val result = EmailValidators.isValidEmail(email)
        Assert.assertTrue(result)
    }

    @Test
    fun whenEmailIsInValid_shouldReturnFalse() {
        val email = "himgmail.com"
        val result = EmailValidators.isValidEmail(email)
        Assert.assertFalse(result)
    }

}