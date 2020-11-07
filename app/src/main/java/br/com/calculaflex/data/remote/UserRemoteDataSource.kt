package br.com.calculaflex.data.remote

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User

interface UserRemoteDataSource {

    suspend fun getUserLogged(): RequestState<User>

}