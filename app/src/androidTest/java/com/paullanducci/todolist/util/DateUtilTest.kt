package com.paullanducci.todolist.util

import org.junit.Assert.assertTrue
import org.junit.Test

class DateUtilTest {

    @Test
    fun testGetCurrentDateAsString() {
        assertTrue(getCurrentDateAsString() != "")
        assertTrue(getCurrentDateAsString().length == 18)
    }
}