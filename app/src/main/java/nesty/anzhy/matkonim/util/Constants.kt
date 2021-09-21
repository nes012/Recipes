package nesty.anzhy.matkonim.util

class Constants {

    companion object{
        const val API_KEY = "32c27d995c3f4bfd819eaeab50fce371"
        const val BASE_URL = "https://api.spoonacular.com/"

        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room db
        const val DATABASE_NAME = "recipes_db"
        const val RECIPES_TABLE = "recipes_table"
    }
}