package com.capstone.invoicemanager

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capstone.invoicemanager.connection.CrudApp
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class ListInvoiceActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(InvoiceListActivity::class.java)


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
    fun checkAddButtonTest(){
        Espresso.onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(AddInvoiceActivity::class.java.name))
    }
}