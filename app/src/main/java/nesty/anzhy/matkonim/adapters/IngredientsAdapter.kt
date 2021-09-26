package nesty.anzhy.matkonim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
        holder.binding.ivIngredient.load(BASE_IMAGE_URL + ingredientList[position].image)
        holder.binding.tvIngredientName.text = ingredientList[position].name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        holder.binding.tvIngredientConsistency.text = ingredientList[position].consistency
        holder.binding.ivIngredientOriginal.text = ingredientList[position].original
        holder.binding.tvIngredientUnit.text = ingredientList[position].unit
        holder.binding.tvIngredientAmount.text = ingredientList[position].amount.toString()

    }

    override fun getItemCount(): Int = ingredientList.size


    fun setData(newIngredients: List<ExtendedIngredient>){
        val recipesDiffUtil = RecipesDiffUtil(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}