package nesty.anzhy.matkonim.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.util.Constants

//also we need to modify recipes type converter because we are going to use Result class in this entity.
@Entity(tableName = Constants.FAVORITES_RECIPES_TABLE)
class FavoritesEntity(
    //we need to annotate autoGenerate true because we're going to have multiple
    // favorite recipes inside our favorite recipes table
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)