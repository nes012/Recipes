package nesty.anzhy.matkonim.ui.recipes

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.util.launchFragmentInHiltContainer

@MediumTest
@HiltAndroidTest
class RecipesFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromRecipesFragmentToBottomSheet() {
        val navController = Mockito.mock(NavController::class.java)
        val fragmentArgs = bundleOf("backFromBottomSheet" to false)

        launchFragmentInHiltContainer<RecipesFragment>(
            factory = FragmentFactory(),
            fragmentArgs = fragmentArgs
        ) {
            Navigation.setViewNavController(
                view = requireView(),
                controller = navController
            )
        }
        Espresso.onView(ViewMatchers.withId(R.id.fabRecipesFragment)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(R.id.action_navigation_recipes_to_recipesBottomSheet)
    }

 /*   @Test
    fun testSearchField(){
        val navController = Mockito.mock(NavController::class.java)
        val fragmentArgs = bundleOf("backFromBottomSheet" to false)

        launchFragmentInHiltContainer<RecipesFragment>(
            factory = FragmentFactory(),
            fragmentArgs = fragmentArgs
        ) {
            Navigation.setViewNavController(
                view = requireView(),
                controller = navController
            )
        }
        Espresso.onView(ViewMatchers.withId(R.id.menu_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
            .perform(ViewActions.typeText("Chocolate cake"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }*/

}