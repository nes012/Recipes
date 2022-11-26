package nesty.anzhy.matkonim.data.network

import nesty.anzhy.matkonim.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

open class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) : RemoteDataSourceInterface {

    override suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }

    override suspend fun searchRecipes(searchQueryMap: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(searchQueryMap)
    }
}