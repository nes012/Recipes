package nesty.anzhy.matkonim.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nesty.anzhy.matkonim.models.FoodRecipe
import nesty.anzhy.matkonim.models.Result

class RecipesTypeConverter {
    var gson = Gson()
    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe):String{
        return gson.toJson(foodRecipe)
    }
    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodRecipe{
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }


    //we need 2 functions. to convert recipe to string and string to recipe(result)..

    @TypeConverter
    fun resultToString(result: Result):String{
        return gson.toJson(result)
    }
    @TypeConverter
    fun stringToResult(data:String):Result{
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }

}