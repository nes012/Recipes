package nesty.anzhy.matkonim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nesty.anzhy.matkonim.databinding.RecipesItemLayoutBinding
import nesty.anzhy.matkonim.ui.models.FoodRecipe
import nesty.anzhy.matkonim.ui.models.Result
import nesty.anzhy.matkonim.util.RecipesDiffUtil

class RecipesAdapter:RecyclerView.Adapter<RecipesAdapter.VH>() {

    private var recipe = emptyList<Result>()

    class VH(private val binding: RecipesItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(result: Result){
                binding.result = result
                binding.executePendingBindings()
            }

        companion object{
            fun from(parent: ViewGroup):VH{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesItemLayoutBinding.inflate(layoutInflater, parent, false)
                return VH(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       return VH.from(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentResult = recipe[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    fun setData(newData: FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipe, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipe = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}