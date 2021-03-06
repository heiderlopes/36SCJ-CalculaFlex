package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.data.remote.mapper.NewUserFirebasePayloadMapper
import br.com.calculaflex.domain.entity.NewUser
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import br.com.calculaflex.domain.exceptions.UserNotLoggedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UserRemoteFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {
        return try {
            firebaseAuth.currentUser?.reload()
            val firebaseUser = firebaseAuth.currentUser
            val user = User(firebaseUser?.displayName ?: "")

            if (firebaseUser == null)
                RequestState.Error(UserNotLoggedException())
            else
                RequestState.Success(user)

        } catch (e: Exception) {
            RequestState.Error(e)
        }

    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(
                userLogin.email,
                userLogin.password
            ).await()

            val firebaseUser = firebaseAuth.currentUser
            val user = User(firebaseUser?.displayName ?: "")

            if (firebaseUser == null) {
                RequestState.Error(UserNotLoggedException())
            } else {
                RequestState.Success(user)
            }

        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(newUser.email, newUser.password).await()

            val newUserFirebasePayload =
                NewUserFirebasePayloadMapper.mapToNewUserFirebasePayload(newUser)

            val userId = firebaseAuth.currentUser?.uid
            if (userId == null) {
                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))
            } else {
                firebaseFirestore
                    .collection("users")
                    .document(userId)
                    .set(newUserFirebasePayload)
                    .await()
                RequestState.Success(NewUserFirebasePayloadMapper.mapToUser(newUserFirebasePayload))
            }
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }
}