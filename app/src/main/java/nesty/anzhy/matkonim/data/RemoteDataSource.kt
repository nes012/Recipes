package nesty.anzhy.matkonim.data

import nesty.anzhy.matkonim.data.network.FoodRecipesApi
import nesty.anzhy.matkonim.ui.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }
}