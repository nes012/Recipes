package nesty.anzhy.matkonim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.databinding.IngredientsItemLayoutBinding
import nesty.anzhy.matkonim.models.ExtendedIngredient
import nesty.anzhy.matkonim.util.Constants.Companion.BASE_IMAGE_URL
import nesty.anzhy.matkonim.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: IngredientsItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IngredientsItemLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.ivIngredient.load(BASE_IMAGE_URL + ingredientList[position].image) {
            crossfade(600)
            error(R.drawable.error_loading)
        }
        holder.binding.tvIngredientName.text = ingredientList[position].name.toString()
            .uppercase(Locale.getDefault())
        holder.binding.tvIngredientAmount.text = ingredientList[position].amount.toString()
        holder.binding.tvIngredientUnit.text = ingredientList[position].unit
        holder.binding.tvIngredientConsistency.text = ingredientList[position].consistency
        holder.binding.tvIngredientOriginal.text = ingredientList[position].original
    }

    override fun getItemCount(): Int = ingredientList.size


    fun setData(newIngredients: List<ExtendedIngredient>){
        val recipesDiffUtil = RecipesDiffUtil(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }


}


