package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_32.patinfly.domain.repository.ISystemPricingPlanRepository
import cat.deim.asm_32.patinfly.domain.usecase.BikeRentDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BikeRentDetailViewModel(
    private val bikeUuid: String,
    private val userId: String,
    private val rentUseCase: BikeRentDetailUseCase,
    private val pricingPlanRepository: ISystemPricingPlanRepository
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
            val rented = rentUseCase.isBikeRentedByUser(bikeUuid, userId)
            _isRentedByUser.value = rented
            val bikeData = rentUseCase.getBikeById(bikeUuid)
            _bike.value = bikeData
            val plan = rentUseCase.getPricingPlan()
            _pricingPlan.value = plan
        }
    }

    fun toggleRent() {
        viewModelScope.launch {
            isLoading.value = true
            val result = rentUseCase.toggleRent(bikeUuid, userId)
            _rentToggled.value = result
            _isRentedByUser.value = result == true
            isLoading.value = false
        }
    }
}