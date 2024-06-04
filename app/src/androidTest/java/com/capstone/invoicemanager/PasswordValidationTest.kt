package com.capstone.invoicemanager;


import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PasswordValidationTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testPasswordValid() {
        val scenario = ActivityScenario.launch(SignupActivity::class.java)
        scenario.onActivity { activity ->
            // Test a valid password
            assertTrue("Valid password should return true", activity.isValidPassword("Password1!"))
        }
    }

    @Test
    fun testPasswordNoUpperCase() {
        val scenario = ActivityScenario.launch(SignupActivity::class.java)
        scenario.onActivity { activity ->
            // Test password with no uppercase letter
            assertFalse("Password should contain at least one uppercase letter", activity.isValidPassword("password1!"))
        }
    }

    @Test
    fun testPasswordNoLowerCase() {
        val scenario = ActivityScenario.launch(SignupActivity::class.java)
        scenario.onActivity { activity ->
            // Test password with no lowercase letter
            assertFalse("Password should contain at least one lowercase letter", activity.isValidPassword("PASSWORD1!"))
        }
    }

    @Test
    fun testPasswordNoNumber() {
        val scenario = ActivityScenario.launch(SignupActivity::class.java)
        scenario.onActivity { activity ->
            // Test password with no number
            assertFalse("Password should contain at least one number", activity.isValidPassword("Password!"))
        }
    }

    @Test
    fun testPasswordNoSpecialCharacter() {
        val scenario = ActivityScenario.launch(SignupActivity::class.java)
        scenario.onActivity { activity ->
            // Test password with no special character
            assertFalse("Password should contain at least one special character", activity.isValidPassword("Password1"))
        }
    }

    @Test
    fun testPasswordTooShort() {
        val scenario = ActivityScenario.launch(SignupActivity::class.java)
        scenario.onActivity { activity ->
            // Test password that is too short
            assertFalse("Password should be at least 4 characters long", activity.isValidPassword("P1!"))
        }
    }
}

