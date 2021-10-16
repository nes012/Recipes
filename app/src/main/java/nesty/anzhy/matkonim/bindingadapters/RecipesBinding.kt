package nesty.anzhy.matkonim.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import nesty.anzhy.matkonim.data.database.entities.RecipesEntity
import nesty.anzhy.matkonim.models.FoodRecipe
import nesty.anzhy.matkonim.util.NetworkResult

//binding class for our recipes fragment.
//if we haven't internet connection
class RecipesBinding {
    companion object{

        //require all to true - we want to make our compiler display a warning or an error
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataError(
            //actual view on which we are going to use custom binding adapter
            view: View,
            //our api response
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
            when(view){
                is ImageView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }

    }
}