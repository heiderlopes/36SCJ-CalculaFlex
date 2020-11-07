package br.com.calculaflex.data.remote

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import kotlinx.coroutines.delay

class UserRemoteFakeDataSourceImpl : UserRemoteDataSource {


    override suspend fun getUserLogged(): RequestState<User> {
        delay(3000)
        return RequestState.Success(User("Heider"))
    }

}