package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import br.com.calculaflex.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun doLogin(userLogin: UserLogin): RequestState<User> =
        userRepository.doLogin(userLogin)

}
