package cat.deim.asm_32.patinfly.domain.models

data class Credentials(
    val email: String,
    val password: String
) {
    fun isNotEmpty(): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}