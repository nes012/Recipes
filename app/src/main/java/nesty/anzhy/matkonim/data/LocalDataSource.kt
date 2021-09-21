package nesty.anzhy.matkonim.data

import kotlinx.coroutines.flow.Flow
import nesty.anzhy.matkonim.data.database.RecipesDao
import nesty.anzhy.matkonim.data.database.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}