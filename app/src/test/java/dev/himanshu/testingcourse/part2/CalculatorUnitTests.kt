package dev.himanshu.testingcourse.part2

import org.junit.Assert
import org.junit.Test

class CalculatorUnitTests {

    @Test
    fun whenTwoPositiveNumbersAdded_shouldReturnCorrectResult() {
        // Arrange
        val calculator = Calculator()

        // Act
        val result = calculator.add(1, 1)

        // Assert
        Assert.assertEquals(2, result)

    }

    // Given When Then
    @Test
    fun givenTwoPositiveIntegers_whenAdded_shouldReturnCorrectResult() {
        // Arrange
        val calculator = Calculator()

        // Act
        val result = calculator.add(1, 1)

        // Assert
        Assert.assertEquals(2, result)
    }

    // descriptive way
    @Test
    fun `should return correct result when added two positive numbers`() {
        // Arrange
        val calculator = Calculator()

        // Act
        val result = calculator.add(1, 1)

        // Assert
        Assert.assertEquals(2, result)
    }

}