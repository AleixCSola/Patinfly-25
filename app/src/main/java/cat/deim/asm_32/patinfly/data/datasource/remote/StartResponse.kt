package cat.deim.asm_32.patinfly.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class StartResponse(
    @SerializedName("success") val success: Boolean
)