package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.repository.UserRepository


class GetUserLoggedUseCase(
    private val userRepository: UserRepository
) {

    suspend fun getUserLogged() : RequestState<User> = userRepository.getUserLogged()

}