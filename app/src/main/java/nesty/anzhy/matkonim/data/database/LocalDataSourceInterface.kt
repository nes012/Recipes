package nesty.anzhy.matkonim.data.database

import kotlinx.coroutines.flow.Flow
import nesty.anzhy.matkonim.data.database.entities.FavoritesEntity
import nesty.anzhy.matkonim.data.database.entities.RecipesEntity

interface LocalDataSourceInterface {
    fun readRecipes(): Flow<List<RecipesEntity>>

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>

    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity)

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    suspend fun deleteAllFavoriteRecipes()
}