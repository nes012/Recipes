package nesty.anzhy.matkonim.ui.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nesty.anzhy.matkonim.util.Constants.Companion.API_KEY
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_API_KEY
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_DIET
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_NUMBER
import nesty.anzhy.matkonim.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

    }
    val text: LiveData<String> = _text


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}