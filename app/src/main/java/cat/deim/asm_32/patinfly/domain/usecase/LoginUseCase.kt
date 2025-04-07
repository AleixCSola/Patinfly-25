package cat.deim.asm_32.patinfly.domain.usecase

import cat.deim.asm_32.patinfly.domain.repository.IUserRepository

object LoginUseCase {
    class LoginUseCase(private var userRepository: IUserRepository)
}