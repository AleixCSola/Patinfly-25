package cat.deim.asm_32.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_32.patinfly.domain.models.User
import cat.deim.asm_32.patinfly.domain.models.Credentials
import cat.deim.asm_32.patinfly.domain.repository.IUserRepository


class LoginUseCase(private var userRepository: IUserRepository) {
    suspend fun execute(credentials: Credentials): Boolean{
        return try{
            //Check if user exist
            val user = userRepository.getUserByEmail(credentials.email)
            var retorn: Boolean = false
            //Fetch or Save data if needed
            user?.let {
                retorn = user.hashedPassword == credentials.password
            }
            retorn // return value: default false
        } catch (error:Exception){
            Log.e("LoginUsecase", "Error in login process", error)
            false //return value
        }
    }
}