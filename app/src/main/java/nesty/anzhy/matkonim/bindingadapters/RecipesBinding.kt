package nesty.anzhy.matkonim.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import nesty.anzhy.matkonim.data.database.RecipesEntity
import nesty.anzhy.matkonim.models.FoodRecipe
import nesty.anzhy.matkonim.util.NetworkResult

//binding class for our recipes fragment.
//if we haven't internet connection
class RecipesBinding {
     companion object{

         //require all to true - we want to make our compiler display a warning or an error
         @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
         @JvmStatic
         fun errorImageViewVisibility(
             //actual view on which we are going to use custom binding adapter
             imageView: ImageView,
             //our api response
             apiResponse: NetworkResult<FoodRecipe>?,
             database: List<RecipesEntity>?
         ){
             if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                 imageView.visibility = View.VISIBLE
             }
             else if (apiResponse is NetworkResult.Loading){
                 imageView.visibility = View.INVISIBLE
             }
             else if(apiResponse is NetworkResult.Success){
                 imageView.visibility = View.INVISIBLE
             }
         }

         @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
         @JvmStatic
         fun errorTextViewVisibility(
             //actual view on which we are going to use custom binding adapter
             textView: TextView,
             //our api response
             apiResponse: NetworkResult<FoodRecipe>?,
             database: List<RecipesEntity>?
         ){
             if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                 textView.visibility = View.VISIBLE
                 textView.text = apiResponse.message.toString()
             }
             else if (apiResponse is NetworkResult.Loading){
                 textView.visibility = View.INVISIBLE
             }
             else if(apiResponse is NetworkResult.Success){
                 textView.visibility = View.INVISIBLE
             }
         }
     }
}