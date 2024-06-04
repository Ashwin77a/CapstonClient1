package com.capstone.invoicemanager

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capstone.invoicemanager.connection.CrudApp
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class  LoginActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Mock
    private lateinit var mockCrudApp: CrudApp

    private lateinit var decorView: View


    @Before
    fun setUp() {
        Intents.init()
        MockitoAnnotations.initMocks(this)
        activity.scenario.onActivity { activity ->
            decorView = activity.window.decorView
        }
    }

    @After
    fun tearDown() {
        Intents.release() // Release Espresso Intents after the test
    }


    @Test
    fun test1_checkIfAllFieldsEmpty() {

        // Click the button
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

        // Check for the Toast message
        Espresso.onView(ViewMatchers.withText("Please check username and password!"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun test2_checkIfUsernameEmpty() {

        val userName = ""
        val password = "Ash@123"
        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }

            // Configure mock response
            val mockResponse: Response<Int> = Response.error(
                404, //
                ResponseBody.create(MediaType.parse("application/json"), "404")
            )
            Mockito.`when`(mockCrudApp.getUser(userName, password)).thenReturn(mockResponse)

            //typing username and password
            Espresso.onView(ViewMatchers.withId(R.id.etUsername))
                .perform(ViewActions.typeText(userName), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.etPassword))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())

            // Click the button
            Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

            // Check for the Toast message
            Espresso.onView(ViewMatchers.withText("Username cannot be empty!"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    @Test
    fun test3_checkIfUsernameWrong() {

        val userName = "Ashw"
        val password = "Ash@123"
        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }

            // Configure mock response
            val mockResponse: Response<Int> = Response.error(
                404, //
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
            Mockito.`when`(mockCrudApp.getUser(userName, password)).thenReturn(mockResponse)

            //typing username and password
            Espresso.onView(ViewMatchers.withId(R.id.etUsername))
                .perform(ViewActions.typeText(userName), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.etPassword))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())

            // Click the button
            Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

            // Check for the Toast message
            Espresso.onView(ViewMatchers.withText("Username is incorrect!"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    @Test
    fun test4_CheckIfPasswordEmpty() {
        val userName = "ASH"
        val password = ""
        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }

            // Configure mock response
            val mockResponse: Response<Int> = Response.error(
                401, //
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
            Mockito.`when`(mockCrudApp.getUser(userName, password)).thenReturn(mockResponse)

            //typing username and password
            Espresso.onView(ViewMatchers.withId(R.id.etUsername))
                .perform(ViewActions.typeText(userName), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.etPassword))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())

            // Click the button
            Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

            // Check for the Toast message
            Espresso.onView(ViewMatchers.withText("Password cannot be empty!"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

    }

    @Test
    fun test5_CheckIfPasswordWrong() {
        val userName = "ASH"
        val password = "Ash!23"
        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }

            // Configure mock response
            val mockResponse: Response<Int> = Response.error(
                401, //
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
            Mockito.`when`(mockCrudApp.getUser(userName, password)).thenReturn(mockResponse)

            //typing username and password
            Espresso.onView(ViewMatchers.withId(R.id.etUsername))
                .perform(ViewActions.typeText(userName), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.etPassword))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())

            // Click the button
            Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

            // Check for the Toast message
            Espresso.onView(ViewMatchers.withText("Password is incorrect!"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(decorView)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

    }

    @Test
    fun test6_SuccessfulLogin() {
        val userName = "ASH"
        val password = "Ash@123"
        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }

            // Configure mock response
            Mockito.`when`(mockCrudApp.getUser(userName, password)).thenReturn(Response.success(1))

            //typing username and password
            Espresso.onView(ViewMatchers.withId(R.id.etUsername))
                .perform(ViewActions.typeText(userName), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.etPassword))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())

            // Click the button
            Espresso.onView(ViewMatchers.withId(R.id.loginButton)).
        }
    }
}
