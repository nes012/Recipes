package nesty.anzhy.matkonim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nesty.anzhy.matkonim.databinding.RecipesItemLayoutBinding
import nesty.anzhy.matkonim.models.FoodRecipe
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.util.RecipesDiffUtil

class RecipesAdapter:RecyclerView.Adapter<RecipesAdapter.VH>() {

    private var recipes = emptyList<Result>()

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
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }


    fun setData(newData: FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}