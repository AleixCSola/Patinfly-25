package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_32.patinfly.domain.repository.ISystemPricingPlanRepository
import cat.deim.asm_32.patinfly.domain.usecase.BikeRentDetailUseCase
import cat.deim.asm_32.patinfly.domain.usecase.UpdateBikeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BikeRentDetailViewModel(
    private val bikeUuid: String,
    private val userId: String,
    private val rentUseCase: BikeRentDetailUseCase,
    private val pricingPlanRepository: ISystemPricingPlanRepository,
    private val updateBikeUseCase: UpdateBikeUseCase
) : ViewModel() {

    private val _rentToggled = MutableStateFlow<Boolean?>(null)
    val rentToggled: StateFlow<Boolean?> = _rentToggled

    val isLoading = MutableStateFlow(false)
    private val _isRentedByUser = MutableStateFlow(false)
    val isRentedByUser: StateFlow<Boolean> = _isRentedByUser

    private val _bike = MutableStateFlow<Bike?>(null)
    val bike: StateFlow<Bike?> = _bike

    private val _pricingPlan = MutableStateFlow<SystemPricingPlan?>(null)
    val pricingPlan: StateFlow<SystemPricingPlan?> = _pricingPlan

    init {
        viewModelScope.launch {
            _isRentedByUser.value = rentUseCase.isBikeRentedByUser(bikeUuid, userId)
            _bike.value = rentUseCase.getBikeById(bikeUuid)
            _pricingPlan.value = rentUseCase.getPricingPlan()
        }
    }

    fun toggleRent() {
        val currentBike = _bike.value ?: return
        val currentUserId = userId

        val (updatedBike, operacio) = if (currentBike.isRented && currentBike.userId == userId) {
            currentBike.copy(isRented = false, userId = null) to 3
        } else {
            currentBike.copy(isRented = true, userId = userId) to 2
        }

        viewModelScope.launch {
            updateBikeUseCase(updatedBike, operacio)
            _bike.value = updatedBike
            _isRentedByUser.value = updatedBike.isRented && updatedBike.userId == userId
            _rentToggled.value = true
        }
    }
}

class BikeRentDetailViewModelFactory(
    private val bikeUuid: String,
    private val userId: String,
    private val rentUseCase: BikeRentDetailUseCase,
    private val pricingPlanRepository: ISystemPricingPlanRepository,
    private val updateBikeUseCase: UpdateBikeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BikeRentDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BikeRentDetailViewModel(
                bikeUuid,
                userId,
                rentUseCase,
                pricingPlanRepository,
                updateBikeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
