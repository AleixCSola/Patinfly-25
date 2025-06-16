package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun BikeMapScreen(
    lat: Double,
    lon: Double,
    modifier: Modifier = Modifier
) {
    val position =LatLng(lat, lon)
    val cameraPositionState= rememberCameraPositionState {
        this.position = CameraPosition(position, 15f, 0f, 0f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = position),
            title ="Ubicació de la bici"
        )
    }
}