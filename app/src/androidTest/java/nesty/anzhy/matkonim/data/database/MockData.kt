package nesty.anzhy.matkonim.data.database

class MockData {
    companion object{
        val recipeResult = nesty.anzhy.matkonim.models.Result(
            aggregateLikes = 2,
            cheap = false,
            dairyFree = false,
            extendedIngredients = emptyList(),
            glutenFree = true,
            recipeId = 123,
            image = "",
            readyInMinutes = 2,
            sourceUrl = "",
            sourceName = "",
            title = "Chocolate",
            vegan = false,
            summary = "",
            vegetarian = false,
            veryHealthy = true
        )
    }
}