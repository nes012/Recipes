package nesty.anzhy.matkonim.ui.recipes

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.data.DataStoreRepository
import nesty.anzhy.matkonim.util.Constants.Companion.DEFAULT_DIET_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.DEFAULT_MEAL_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_API_KEY
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_DIET
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_NUMBER
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_SEARCH
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_TYPE
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE
    private var context = application

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()


    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = context.getString(R.string.api_key)
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = context.getString(R.string.api_key)
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }


    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(
                getApplication(), "No Internet Connection.",
                Toast.LENGTH_SHORT
            ).show()
            //we want to pass true to save back online
            //when we lose Internet connection we want to set the value of back Online
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(
                    getApplication(), "We're back online.",
                    Toast.LENGTH_SHORT
                ).show()
                saveBackOnline(false)
            }
        }
    }
}