package cat.deim.asm_32.patinfly.presentation.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import android.util.Log
import cat.deim.asm_32.patinfly.data.datasource.local.SystemPricingPlanDataSource
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.data.repository.BikeRepository
import cat.deim.asm_32.patinfly.data.repository.SystemPricingPlanRepository
import cat.deim.asm_32.patinfly.data.repository.UserRepository
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.BikeType
import cat.deim.asm_32.patinfly.domain.models.Information
import cat.deim.asm_32.patinfly.domain.models.PerKmPricing
import cat.deim.asm_32.patinfly.domain.models.PerMinPricing
import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_32.patinfly.domain.models.TextType
import cat.deim.asm_32.patinfly.domain.models.User
import java.util.Date
import cat.deim.asm_32.patinfly.presentation.bikes.BikeListActivity
import cat.deim.asm_32.patinfly.presentation.profile.ProfileActivity
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme
import cat.deim.asm_32.patinfly.data.datasource.database.AppDatabase
import androidx.lifecycle.lifecycleScope
import cat.deim.asm_32.patinfly.data.datasource.remote.BikeAPIDataSource
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatinflyTheme {
                MainScreen(
                    perfilClick = {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    },
                    bicisClick = {
                        startActivity(Intent(this, BikeListActivity::class.java))
                    }
                )
            }
        }
    }
    override fun onStart() {
        super.onStart()
        lab2Requeriment()
    }
    private fun lab2Requeriment() {
        val bikeType = BikeType(
            uuid = "550e8400-e29b-41d4-a716-446655440000",
            name = "Electric",
            type = "EB001"
        )

        val db = AppDatabase.getDatabase(applicationContext)

        val bikeDao = db.bikeDatasource()
        val userDao = db.userDatasource()
        val planDao = db.systemPricingPlanDatasource()

        val planLocalDataSource = SystemPricingPlanDataSource.getInstance(applicationContext)
        val pricingPlanRepository = SystemPricingPlanRepository(planDao, planLocalDataSource)
        val bikeAPIDataSource = BikeAPIDataSource.getInstance(applicationContext)
        val bikeRepository = BikeRepository(bikeDao, BikeAPIDataSource.getService())
    }
}