package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.data.remote.datasource.UserRemoteDataSource
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import kotlinx.coroutines.delay

class UserRemoteFakeDataSourceImpl :
    UserRemoteDataSource {


    override suspend fun getUserLogged(): RequestState<User> {
        delay(3000)
        //return RequestState.Success(User("Heider"))
        return RequestState.Error(Exception("Usuário não logado"))
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return RequestState.Success(User("Heider"))
    }

}