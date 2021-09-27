package nesty.anzhy.matkonim.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.ui.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesItemBinding {
    companion object{
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl:String){
            imageView.load(imageUrl){
                crossfade(600)
                    //if we haven't internet connection this photo will upload with out cache information
                error(R.drawable.error_loading)
            }
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean){
            if(vegan){
                when(view){
                    is TextView ->{
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView ->
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                }
            }
        }



        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeItemLayout: ConstraintLayout, result: Result){
            Log.d("onRecipeClickListener", "CALLED")
            recipeItemLayout.setOnClickListener{
                try{
                    val action = RecipesFragmentDirections.actionNavigationRecipesToDetailsActivity(result)
                    recipeItemLayout.findNavController().navigate(action)
                }
                catch (e: Exception){
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }

        //we're going to parse text.
        //we need to add nullable to second parameter string. cause it's sometimes can be nullable
        @BindingAdapter("parseHtml")
        @JvmStatic
       fun parseHtml(textview: TextView, description: String?){
            if(description!=null){
                //with this line of code we have parse our html text
                val desc = Jsoup.parse(description).text()
                textview.text = desc
            }
        }
    }
}