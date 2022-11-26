package nesty.anzhy.matkonim.data.network

import nesty.anzhy.matkonim.models.FoodRecipe
import retrofit2.Response
import nesty.anzhy.matkonim.models.Result

class FakeRemoteDataSource : RemoteDataSourceInterface {
    override suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return Response.success(
            FoodRecipe(arrayListOf<Result>())
        )
    }

    override suspend fun searchRecipes(searchQueryMap: Map<String, String>): Response<FoodRecipe> {
        return Response.success(
            FoodRecipe(arrayListOf<Result>())
        )
    }

}