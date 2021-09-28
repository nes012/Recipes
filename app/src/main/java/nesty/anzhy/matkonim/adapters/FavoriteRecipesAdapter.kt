package nesty.anzhy.matkonim.adapters

import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nesty.anzhy.matkonim.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.data.database.entities.FavoritesEntity
import nesty.anzhy.matkonim.databinding.FavoriteRecipesItemLayoutBinding
import nesty.anzhy.matkonim.ui.favoriterecipes.FavoriteRecipesFragmentDirections
import nesty.anzhy.matkonim.util.RecipesDiffUtil

class FavoriteRecipesAdapter
    (
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    private lateinit var mActionMode: ActionMode

    private var myViewHolder = arrayListOf<MyViewHolder>()

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    class MyViewHolder(val binding: FavoriteRecipesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritesEntity: FavoritesEntity) {
            //we are binding favorite entity from layout with our parameter.
            binding.favoriteEntity = favoritesEntity
            //this execute will update all our views
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FavoriteRecipesItemLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolder.add(holder)


        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        /**
         * Single click listener
         */
        holder.binding.favoriteRecipesItemLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionNavigationFavoriteRecipesToDetailsActivity(
                        currentRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        /**
         * Long click listener
         */
        holder.binding.favoriteRecipesItemLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                //multiSelection = false
                    applySelection(holder, currentRecipe)
                true
            }
        }
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        //here we need to inflate new menu which we have created
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        //we need to pass mode to global variable because we want to set title in other functions
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favorite_recipe) {

            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showToast("${selectedRecipes.size} Recipe/s removed")

            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolder.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    //we will you this function in onCreate action mode and on destroy action
    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity) {
        //we're going to use selected recipe to add or remove items from list
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.purple_200)
            applyActionModeTitle()
        }
    }

    //with this function we want to change item background(material card view)
    //this fun will be called inside applied selection two times in if and else block
    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.favoriteRecipesItemLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.cardViewRecipes.setBackgroundColor(
            ContextCompat.getColor(requireActivity, strokeColor)
        )
    }

    private fun showToast(message: String){
        Toast.makeText(requireActivity, message, Toast.LENGTH_SHORT).show()
    }

    //this fun will check if our contextual extra mode initialized or if it is visible
    //if it is visible then we're going to close that
    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
}