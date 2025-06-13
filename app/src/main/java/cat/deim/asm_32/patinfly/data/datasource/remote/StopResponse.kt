package cat.deim.asm_32.patinfly.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class StopResponse(
    @SerializedName("success") val success: Boolean
)