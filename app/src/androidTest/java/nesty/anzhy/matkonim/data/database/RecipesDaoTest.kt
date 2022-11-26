package nesty.anzhy.matkonim.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import nesty.anzhy.matkonim.data.database.entities.FavoritesEntity
import nesty.anzhy.matkonim.data.database.entities.RecipesEntity
import nesty.anzhy.matkonim.models.FoodRecipe
import nesty.anzhy.matkonim.util.getOrAwaitValue
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
        val exampleRecipe = RecipesEntity(
            FoodRecipe(
                arrayListOf(MockData.recipeResult)
            )
        )
        dao.insertRecipes(exampleRecipe)

        val recipeList = dao.readRecipes().first().first()
        assertThat(recipeList.foodRecipe.results[0].title).isEqualTo("Chocolate")
    }

    @Test
    fun onRemoveAllRecipesDBisEmpty() = runTest {
        val favoritesEntity = FavoritesEntity(
            id = 0,
            MockData.recipeResult
        )
        dao.insertFavoriteRecipe(
            favoritesEntity
        )
        dao.deleteAllFavoriteRecipes()
        val data = dao.readFavoriteRecipes().asLiveData().getOrAwaitValue()
        assertThat(data).isEmpty()
    }


    @After
    fun tearDown() {
        database.close()
    }
}