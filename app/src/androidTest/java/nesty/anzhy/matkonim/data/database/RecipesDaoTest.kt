package nesty.anzhy.matkonim.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import nesty.anzhy.matkonim.data.database.entities.RecipesEntity
import nesty.anzhy.matkonim.models.FoodRecipe
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class RecipesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: RecipesDatabase

    private lateinit var dao: RecipesDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.recipesDao()
    }

    @Test
    fun insertRecipeTesting() = runTest {
        val recipeResult = nesty.anzhy.matkonim.models.Result(
            aggregateLikes = 2,
            cheap = false,
            dairyFree = false,
            extendedIngredients = emptyList(),
            glutenFree = true,
            recipeId = 123,
            image = "",
            readyInMinutes = 2,
            sourceUrl = "",
            sourceName = "",
            title = "Chocolate",
            vegan = false,
            summary = "",
            vegetarian = false,
            veryHealthy = true
        )
        val exampleRecipe = RecipesEntity(
            FoodRecipe(
                arrayListOf(recipeResult)
            )
        )
        dao.insertRecipes(exampleRecipe)

        val recipeList = dao.readRecipes().first().first()
        assertThat(recipeList.foodRecipe.results[0].title).isEqualTo("Chocolate")
    }


    @After
    fun tearDown() {
        database.close()
    }
}