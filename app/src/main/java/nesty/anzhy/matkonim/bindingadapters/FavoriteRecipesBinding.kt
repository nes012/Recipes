package nesty.anzhy.matkonim.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nesty.anzhy.matkonim.adapters.FavoriteRecipesAdapter
import nesty.anzhy.matkonim.data.database.entities.FavoritesEntity

class FavoriteRecipesBinding {
    companion object {

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        //viewVisibility will refer to favoritesEntity and setData to adapter
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
            mAdapter: FavoriteRecipesAdapter?
        ){
            //here we want to check if our recipes table is empty or not
            if(favoritesEntity.isNullOrEmpty()){
                when(view){
                    is ImageView ->{
                        view.visibility = View.VISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView ->{
                        view.visibility = View.INVISIBLE
                    }
                }
            } else {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoritesEntity)
                    }
                }
            }
        }
    }
}