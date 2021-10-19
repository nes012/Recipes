package nesty.anzhy.matkonim.models


import com.google.gson.annotations.SerializedName


data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>,
    )