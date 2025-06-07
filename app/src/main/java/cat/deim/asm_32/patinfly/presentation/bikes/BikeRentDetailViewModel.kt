package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm_32.patinfly.domain.usecase.BikeRentDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BikeRentDetailViewModel(
    private val bikeUuid: String,
    private val userId: String,
    private val rentUseCase: BikeRentDetailUseCase
) : ViewModel() {

    private val _rentToggled = MutableStateFlow<Boolean?>(null)
    val rentToggled: StateFlow<Boolean?> = _rentToggled

    val isLoading = MutableStateFlow(false)
    private val _isRentedByUser = MutableStateFlow(false)
    val isRentedByUser: StateFlow<Boolean> = _isRentedByUser

    init {
        viewModelScope.launch {
            val rented = rentUseCase.isBikeRentedByUser(bikeUuid, userId)
            _isRentedByUser.value = rented
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