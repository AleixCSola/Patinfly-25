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

        val ejemploPlan = SystemPricingPlan(
            lastUpdated = "2024-02-27T12:34:56Z",
            ttl = "24h",
            version = "1.0",
            dataPlan = Information(
                planId = "plan2025",
                name = TextType("Patinfly Bike Pricing Test", "en"),
                currency = "EUR",
                price = 1.00,
                isTaxable = true,
                description = TextType("1€ unlock fee, 0€ per kilometer and 0.25 per minute.", "en"),
                perKmPricing = PerKmPricing(0.0, 0.0, 1.0),
                perMinPricing = PerMinPricing(0.0, 0.25, 1.0)
            )
        )

        val db = AppDatabase.getDatabase(applicationContext)

        val bikeDao = db.bikeDatasource()
        val userDao = db.userDatasource()
        val planDao = db.systemPricingPlanDatasource()

        val planLocalDataSource = SystemPricingPlanDataSource.getInstance(applicationContext)
        val pricingPlanRepository = SystemPricingPlanRepository(planDao, planLocalDataSource)
        BikeAPIDataSource.getInstance(applicationContext)
        val bikeRepository = BikeRepository(bikeDao, BikeAPIDataSource.getService())

        lifecycleScope.launch {
            /*userRepository.setUser(ejemploUsu)
            val usuario = userRepository.getById(ejemploUsu.uuid)
            Log.d("MainActivity", "Usuari: ${usuario?.name}")*/

            planLocalDataSource.loadPricingData()
            pricingPlanRepository.insert(ejemploPlan)
            val plan = pricingPlanRepository.getById(ejemploPlan.dataPlan.planId)
            Log.d("MainActivity", "Plan: ${plan?.dataPlan?.name?.text}")

            //totes les bikes del api, com que no deserialitza be les inserto manualment
            val sharedPrefs = getSharedPreferences("session", Context.MODE_PRIVATE)
            val token = sharedPrefs.getString("token", null)
            if (token!=null){
                val bikes = bikeRepository.getAll(token)
                if (bikes.isEmpty()) {
                    Log.d("MainActivity", "Insertant bikes manualment")
                    val ejemploBike1 = Bike(
                        uuid = "5e8d4746-b022-4526-b3c8-7eda5c8fb6c4",
                        name = "T-002",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.131776532716586,
                        lon = 1.2450067067107917
                    )

                    val ejemploBike2 = Bike(
                        uuid = "c7b22994-1c74-497d-bc11-c55ed0915abf",
                        name = "T-003",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.131821,
                        lon = 1.2443993
                    )

                    val ejemploBike3 = Bike(
                        uuid = "1383188a-30b1-4d7f-87dc-3b56c5f3358a",
                        name = "T-004",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.130649070872614,
                        lon = 1.2438353697082147
                    )

                    val ejemploBike4 = Bike(
                        uuid = "7a729d9b-f488-4f13-8554-8188166b710d",
                        name = "T-005",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.15493469023851,
                        lon = 1.108742719641211
                    )

                    val ejemploBike5 = Bike(
                        uuid = "b7196c61-81c7-45b3-ac21-90ac935de539",
                        name = "T-006",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.15493469023851,
                        lon = 1.1051878390272956
                    )

                    val ejemploBike6 = Bike(
                        uuid = "48235109-5fd0-4e2a-b2a3-f103b99c4eee",
                        name = "T-007",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.15493469023851,
                        lon = 1.2433505
                    )

                    val ejemploBike7 = Bike(
                        uuid = "ab0ade62-e356-4fcc-97fc-64414b50c21e",
                        name = "T-008",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.15493469023851,
                        lon = 1.2433845
                    )

                    val ejemploBike8 = Bike(
                        uuid = "ccba7189-8a39-49bb-88ae-8a2665f4d22f",
                        name = "T-009",
                        typeUuid = "EB001",
                        isDisabled = true,
                        isReserved = false,
                        isRented = false,
                        lat = 41.15493469023851,
                        lon = 1.243389
                    )

                    val ejemploBike9 = Bike(
                        uuid = "78afc6e9-c949-4449-af96-de5c9778e3df",
                        name = "T-010",
                        typeUuid = "EB001",
                        isDisabled = true,
                        isReserved = false,
                        isRented = false,
                        lat = 41.15493469023851,
                        lon = 1.2451878390272955
                    )

                    val ejemploBike10 = Bike(
                        uuid = "05663793-ff7e-4a85-97e1-6aaf072470b0",
                        name = "R-001",
                        typeUuid = "RB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 0.0,
                        lon = 1.244950388989212
                    )

                    val ejemploBike11 = Bike(
                        uuid = "067b42be-95d8-4280-a16b-a8a2f37d8bce",
                        name = "R-002",
                        typeUuid = "RB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.2450067067107917
                    )

                    val ejemploBike12 = Bike(
                        uuid = "528ff376-5f5d-48c4-a987-a1e267510f6f",
                        name = "R-003",
                        typeUuid = "RB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.2443993
                    )

                    val ejemploBike13 = Bike(
                        uuid = "02a4a705-dcdf-4374-9ac6-d759ad22699f",
                        name = "R-004",
                        typeUuid = "RB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.2438353697082147
                    )

                    val ejemploBike14 = Bike(
                        uuid = "3f0ac6e3-544f-48e2-be5e-31c2f3d6a878",
                        name = "R-005",
                        typeUuid = "RB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.108742719641211
                    )

                    val ejemploBike15 = Bike(
                        uuid = "6f62f682-14c8-4062-b741-628dce892ade",
                        name = "R-006",
                        typeUuid = "RB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.2433505
                    )

                    val ejemploBike16 = Bike(
                        uuid = "0a428244-17b8-4edd-adc6-36c768b33b27",
                        name = "R-007",
                        typeUuid = "RB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.2433845
                    )

                    val ejemploBike17 = Bike(
                        uuid = "a423021a-07bf-4a35-9588-57c9bb7639c6",
                        name = "R-008",
                        typeUuid = "RB001",
                        isDisabled = true,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.243389
                    )

                    val ejemploBike18 = Bike(
                        uuid = "74438910-a4af-473b-8f9c-f492d96466ba",
                        name = "R-009",
                        typeUuid = "RB001",
                        isDisabled = true,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.2451878390272955
                    )

                    val ejemploBike19 = Bike(
                        uuid = "db51e784-e56e-4b44-a8a3-d6a52f082cb2",
                        name = "SC-001",
                        typeUuid = "SCOOTER001",
                        isDisabled = true,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.128742719641211
                    )

                    val ejemploBike20 = Bike(
                        uuid = "86b4e1a3-bc10-469c-b8ec-48655d055825",
                        name = "SC-002",
                        typeUuid = "SCOOTER001",
                        isDisabled = true,
                        isReserved = false,
                        isRented = false,
                        lat = 41.13017817516442,
                        lon = 1.138742719641211
                    )

                    val ejemploBike21 = Bike(
                        uuid = "76fd4101-ce46-473e-898c-0f77034c1bec",
                        name = "T-001",
                        typeUuid = "EB001",
                        isDisabled = false,
                        isReserved = false,
                        isRented = false,
                        lat = 41.131715934924685,
                        lon = 1.244950388989212
                    )
                    bikeRepository.insert(ejemploBike1)
                    bikeRepository.insert(ejemploBike2)
                    bikeRepository.insert(ejemploBike3)
                    bikeRepository.insert(ejemploBike4)
                    bikeRepository.insert(ejemploBike5)
                    bikeRepository.insert(ejemploBike6)
                    bikeRepository.insert(ejemploBike7)
                    bikeRepository.insert(ejemploBike8)
                    bikeRepository.insert(ejemploBike9)
                    bikeRepository.insert(ejemploBike10)
                    bikeRepository.insert(ejemploBike11)
                    bikeRepository.insert(ejemploBike12)
                    bikeRepository.insert(ejemploBike13)
                    bikeRepository.insert(ejemploBike14)
                    bikeRepository.insert(ejemploBike15)
                    bikeRepository.insert(ejemploBike16)
                    bikeRepository.insert(ejemploBike17)
                    bikeRepository.insert(ejemploBike18)
                    bikeRepository.insert(ejemploBike19)
                    bikeRepository.insert(ejemploBike20)
                    bikeRepository.insert(ejemploBike21)
                }
            }

        }

    }

}