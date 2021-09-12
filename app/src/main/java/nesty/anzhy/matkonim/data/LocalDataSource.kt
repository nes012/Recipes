package nesty.anzhy.matkonim.data

import nesty.anzhy.matkonim.data.database.RecipesDao
import nesty.anzhy.matkonim.data.database.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
){

    fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }
}