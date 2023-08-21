package com.paul.todolist.util

import org.junit.Assert.assertTrue
import org.junit.Test

class DateUtilKtTest {

    @Test
    fun testGetCurrentDateAsString() {
        assertTrue(getCurrentDateAsString() != "")
    }
}