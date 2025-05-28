package cat.deim.asm_32.patinfly.data.datasource.database
import cat.deim.asm_32.patinfly.data.datasource.database.model.SystemPricingPlanDTO
import cat.deim.asm_32.patinfly.data.datasource.database.model.UserDTO
import cat.deim.asm_32.patinfly.data.datasource.database.model.BikeDTO
import cat.deim.asm_32.patinfly.data.datasource.database.Converters
import androidx.room.TypeConverters
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserDTO::class, BikeDTO::class, SystemPricingPlanDTO::class], version = 1)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDatasource(): UserDatasource
    abstract fun bikeDatasource(): BikeDatasource
    abstract fun systemPricingPlanDatasource(): SystemPricingPlanDatasource

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "patinfly_25_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}