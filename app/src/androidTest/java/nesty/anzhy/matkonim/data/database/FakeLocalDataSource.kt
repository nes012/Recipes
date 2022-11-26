package nesty.anzhy.matkonim.data.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import nesty.anzhy.matkonim.data.database.entities.FavoritesEntity
import nesty.anzhy.matkonim.data.database.entities.RecipesEntity
import nesty.anzhy.matkonim.models.FoodRecipe
import nesty.anzhy.matkonim.models.Result

class FakeLocalDataSource : LocalDataSourceInterface {

    private val recipes = mutableListOf<RecipesEntity>()
    private val recipesLiveData = MutableLiveData<List<RecipesEntity>>(recipes)


    private val favRecipes = mutableListOf<FavoritesEntity>()
    private val favRecipesLiveData = MutableLiveData<List<FavoritesEntity>>(favRecipes)

    override fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesLiveData.asFlow()
    }

    override fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return emptyFlow()
    }

    override suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        val res = Result(
            aggregateLikes = 1,
            cheap = false,
            dairyFree = false,
            extendedIngredients = emptyList(),
            glutenFree = true,
            recipeId = 1,
            image = "",
            readyInMinutes = 12,
            sourceName = "",
            sourceUrl = "",
            summary = "",
            title = "",
            vegan = false,
            vegetarian = false,
            veryHealthy = true
        )
        recipes.add(
            1,
            RecipesEntity(
                FoodRecipe(
                    arrayListOf(res)
                )
            )
        )
        refreshData()
    }

    override suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        favRecipes.add(favoritesEntity)
        refreshFavoriteData()
    }

    override suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        favRecipes.remove(favoritesEntity)
        refreshFavoriteData()
    }

    override suspend fun deleteAllFavoriteRecipes() {

    }

    private fun refreshData() {
        recipesLiveData.postValue(recipes)
    }

    private fun refreshFavoriteData() {
        favRecipesLiveData.postValue(favRecipes)
    }

}