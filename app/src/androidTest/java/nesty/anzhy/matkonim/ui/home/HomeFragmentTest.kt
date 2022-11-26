package nesty.anzhy.matkonim.ui.home

import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.util.launchFragmentInHiltContainer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
class HomeFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testClickOnSignInNavigateToSignInFragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment>(
            factory = FragmentFactory(),
        ) {
            Navigation.setViewNavController(
                view = requireView(),
                controller = navController
            )
        }
        Espresso.onView(ViewMatchers.withId(R.id.btnSignIn)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(R.id.action_navigation_home_to_signInFragment)
    }
}