package a114w6077dicoding.develops.githubuserapp

import android.content.Context
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.tabs.TabLayout
import org.hamcrest.core.AllOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{
    private lateinit var instrumentalContext: Context
    private val username = "Elsapitalita"

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp(){
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext

        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun loadUser(){
        Espresso.onView(withId(R.id.rv_User))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loadDetailUser(){
        Espresso.onView(withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.supportsInputMethods(),
                ViewMatchers.isDescendantOfA(withId(R.id.search))
            )
        )
            .perform(ViewActions.typeText(username)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        delayThreeSecond()
        Espresso.onView(withId(R.id.rv_User))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_User))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))
        delayThreeSecond()
        Espresso.onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        delayThreeSecond()
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
    }


    @Test
    fun loadFavorite(){
        Espresso.onView(withId(R.id.favorite_menu)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rvUserFav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        delayThreeSecond()
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun loadAddDeleteFavorite(){
        Espresso.onView(withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.supportsInputMethods(),
                ViewMatchers.isDescendantOfA(withId(R.id.search))
            )
        )
            .perform(ViewActions.typeText(username)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        delayThreeSecond()
        Espresso.onView(withId(R.id.rv_User))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_User))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))
        delayThreeSecond()
        Espresso.onView(withId(R.id.toggleFavorite)).perform(ViewActions.click())
        delayThreeSecond()
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.favorite_menu)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rvUserFav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        delayThreeSecond()
        Espresso.onView(withId(R.id.rvUserFav))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))
        delayThreeSecond()
        Espresso.onView(withId(R.id.toggleFavorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        delayThreeSecond()
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
    }

    private fun delayThreeSecond() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = AllOf.allOf(
                ViewMatchers.isDisplayed(),
                ViewMatchers.isAssignableFrom(TabLayout::class.java)
            )

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

}