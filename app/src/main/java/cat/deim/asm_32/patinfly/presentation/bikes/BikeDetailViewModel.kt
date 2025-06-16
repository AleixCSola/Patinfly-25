package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.usecase.BikeDetailUseCase
import cat.deim.asm_32.patinfly.domain.usecase.UpdateBikeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class BikeDetailViewModel(
    private val bikeUuid: String,
    private val bikeDetailUseCase: BikeDetailUseCase,
    private val updateBikeUseCase: UpdateBikeUseCase
) : ViewModel() {
    private val _bike =MutableStateFlow<Bike?>(null)
    val bike: StateFlow<Bike?> = _bike

    init {
        viewModelScope.launch {
            val bike =bikeDetailUseCase(bikeUuid)
            _bike.value= bike
        }
    }
    fun toggleReservation(userId: String) {
        val currentBike = _bike.value ?: return
        val (updatedBike, operacio) = if (currentBike.isReserved && currentBike.userId == userId) {
            currentBike.copy(isReserved = false, userId = null) to 1
        } else {
            currentBike.copy(isReserved = true, userId = userId) to 0
        }
        viewModelScope.launch {
            updateBikeUseCase(updatedBike, operacio)
            _bike.value = updatedBike
        }
    }
}
class BikeDetailViewModelFactory(
    private val bikeUuid: String,
    private val bikeDetailUseCase: BikeDetailUseCase,
    private val updateBikeUseCase: UpdateBikeUseCase
) : ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BikeDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BikeDetailViewModel(bikeUuid, bikeDetailUseCase, updateBikeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}