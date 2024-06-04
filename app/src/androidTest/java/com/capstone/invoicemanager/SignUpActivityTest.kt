package com.capstone.invoicemanager



import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capstone.invoicemanager.connection.CrudApp
import com.capstone.invoicemanager.datas.User
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.not
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import okhttp3.MediaType
import okhttp3.ResponseBody

import org.junit.After


import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class  SignupActivityTest{

    @get:Rule
    val activity = ActivityScenarioRule(SignupActivity::class.java)

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
    fun test1_checkIfAllFieldsEmpty(){

        // Click the button
        onView(withId(R.id.btsignup)).perform(click())

        // Check for the Toast message
        onView(withText("All fields are required!"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))



    }

    @Test
    fun test2_checkIfUserNameEmpty(){

        onView(withId(R.id.et_email)).perform(typeText("test@gmail.com"))
        onView(withId(R.id.et_password)).perform(typeText("Password@123"), closeSoftKeyboard())
        onView(withId(R.id.et_confirm_password)).perform(typeText("Password@123"), closeSoftKeyboard())

        onView(withId(R.id.btsignup)).perform(click())

        onView(withText("All fields are required!"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))

    }

    @Test
    fun test3_checkIfEmailEmpty(){

        onView(withId(R.id.et_username)).perform(typeText("testname"))
        onView(withId(R.id.et_password)).perform(typeText("Password@123"), closeSoftKeyboard())
        onView(withId(R.id.et_confirm_password)).perform(typeText("Password@123"), closeSoftKeyboard())

        onView(withId(R.id.btsignup)).perform(click())

        onView(withText("All fields are required!"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))

    }


    @Test
    fun test3_checkIfPasswordEmpty(){

        onView(withId(R.id.et_username)).perform(typeText("testname"))
        onView(withId(R.id.et_email)).perform(typeText("test@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.et_confirm_password)).perform(typeText("Password@123"), closeSoftKeyboard())

        onView(withId(R.id.btsignup)).perform(click())

        onView(withText("All fields are required!"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))

    }
    @Test
    fun test4_checkIfConfirmPasswordEmpty(){

        onView(withId(R.id.et_username)).perform(typeText("testname"))
        onView(withId(R.id.et_email)).perform(typeText("test@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.et_password)).perform(typeText("Password@123"), closeSoftKeyboard())

        onView(withId(R.id.btsignup)).perform(click())

        onView(withText("All fields are required!"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))

    }



    @Test
    fun test5_SignupEmailExists() {
        val user = User("testname", "test@gmail.com", "Password@123")


        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(SignupActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }


            // Configure mock response
            val mockResponse: Response<Int> = Response.error(
                409, //
                ResponseBody.create(MediaType.parse("application/json"), "2")
            )
            `when`(mockCrudApp.createUser(user)).thenReturn(mockResponse)


            // Perform actions to trigger signup (type text, click button)
            onView(withId(R.id.et_username)).perform(typeText(user.userName), closeSoftKeyboard())
            onView(withId(R.id.et_email)).perform(typeText(user.email), closeSoftKeyboard())
            onView(withId(R.id.et_password)).perform(typeText(user.password), closeSoftKeyboard())
            onView(withId(R.id.et_confirm_password)).perform(
                typeText(user.password),
                closeSoftKeyboard()
            )
            onView(withId(R.id.btsignup)).perform(click())


            // Verify toast message for successful signup
            onView(withText("This email is already registered!"))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()))

        }
    }
    @Test
    fun test6_SignupUserExists() {
        val user = User("testname", "test@gmail.com", "Password@123")


        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(SignupActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }


            // Configure mock response
            val mockResponse: Response<Int> = Response.error(
                409, //
                ResponseBody.create(MediaType.parse("application/json"), "3")
            )
            `when`(mockCrudApp.createUser(user)).thenReturn(mockResponse)


            // Perform actions to trigger signup (type text, click button)
            onView(withId(R.id.et_username)).perform(typeText(user.userName), closeSoftKeyboard())
            onView(withId(R.id.et_email)).perform(typeText(user.email), closeSoftKeyboard())
            onView(withId(R.id.et_password)).perform(typeText(user.password), closeSoftKeyboard())
            onView(withId(R.id.et_confirm_password)).perform(
                typeText(user.password),
                closeSoftKeyboard()
            )
            onView(withId(R.id.btsignup)).perform(click())


            // Verify toast message for successful signup
            onView(withText("This username is already taken!"))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()))

        }
    }
//
    @Test
    fun test7_SignupSuccess() {
        val user = User("testname", "test@gmail.com", "Password@123")


        runBlockingTest { // Launch a coroutine for the test logic
            val activityScenario = ActivityScenario.launch(SignupActivity::class.java)
            activityScenario.onActivity { activity ->
                activity.injectCrudApp(mockCrudApp)  // Inject mock object before launch
            }


            // Configure mock response
            `when`(mockCrudApp.createUser(user)).thenReturn(Response.success(1))  // Mock successful creation


            // Perform actions to trigger signup (type text, click button)
            onView(withId(R.id.et_username)).perform(typeText(user.userName), closeSoftKeyboard())
            onView(withId(R.id.et_email)).perform(typeText(user.email), closeSoftKeyboard())
            onView(withId(R.id.et_password)).perform(typeText(user.password), closeSoftKeyboard())
            onView(withId(R.id.et_confirm_password)).perform(
                typeText(user.password),
                closeSoftKeyboard()
            )
            onView(withId(R.id.btsignup)).perform(click())


            // Verify toast message for successful signup
            onView(withText("Account created successfully!"))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()))
            intended(hasComponent(LoginActivity::class.java.name))
        }

    }



}
