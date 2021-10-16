package nesty.anzhy.matkonim.ui.activities.detailactivity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import nesty.anzhy.matkonim.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.adapters.PagerAdapter
import nesty.anzhy.matkonim.data.database.entities.FavoritesEntity
import nesty.anzhy.matkonim.databinding.ActivityDetailsBinding
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.ui.activities.detailactivity.fragments.IngredientFragment
import nesty.anzhy.matkonim.ui.activities.detailactivity.fragments.InstructionsFragment
import nesty.anzhy.matkonim.ui.activities.detailactivity.fragments.OverviewFragment

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    //when we open details activity we initialize pagerAdapter

    private val args by navArgs<DetailsActivityArgs>()

    //we will call this vm in save to fav function
    private val mainViewModel: MainViewModel by viewModels() //lazy initialize

    private var recipeSaved = false
    private var savedRecipeId = 0


    private lateinit var menuItem: MenuItem

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ar = args.result

        setSupportActionBar(binding.toolBar)
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")


        val resultBundle = Bundle()
        val res: Result = args.result
        resultBundle.putParcelable("recipeBundle", res)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        //
        menuItem = menu?.findItem(R.id.save_to_favorites_menu)!!
        checkSavedRecipes(menuItem)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this, { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.recipeId == args.result.recipeId) {
                        changeItemMenuColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu && !recipeSaved) {
            //this function should have only one parameter and that is menu item
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            0,
            //result of our selected recipe
            args.result
            //then we need to call insert favorite recipes
        )
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        //after insert recipe to fav db we need to change color of star icon
        changeItemMenuColor(item, R.color.yellow)

        showToast("Recipe saved")

        recipeSaved = true

    }


    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeItemMenuColor(item, R.color.white)
        showToast("Removed from Favorites")
        recipeSaved = false
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this@DetailsActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun changeItemMenuColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeItemMenuColor(menuItem, R.color.white)
    }

}

