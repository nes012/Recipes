package nesty.anzhy.matkonim.data.database

import kotlinx.coroutines.flow.Flow
import nesty.anzhy.matkonim.data.database.entities.FavoritesEntity
import nesty.anzhy.matkonim.data.database.entities.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
): LocalDataSourceInterface  {
    override fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    override fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>{
        return recipesDao.readFavoriteRecipes()
    }

     override suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    override suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    override suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    override suspend fun deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }
}