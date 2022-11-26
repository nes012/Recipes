package nesty.anzhy.matkonim.data.network

import nesty.anzhy.matkonim.models.FoodRecipe
import retrofit2.Response

interface RemoteDataSourceInterface {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>

    suspend fun searchRecipes(searchQueryMap: Map<String, String>): Response<FoodRecipe>
}