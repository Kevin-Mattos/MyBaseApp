package com.example.mybaseapp.test

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mybaseapp.util.base.BaseMVIViewModel
import com.example.mybaseapp.util.extensions.onCollect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


sealed class State {
    data class IsLoading(val loading: Boolean): State()
    data class ShowCep(val cep: Any): State()
    data class ShowError(val error: String?): State()
}

sealed class Intent {
    object GetCep: Intent()
}

class mVM: BaseMVIViewModel<Intent, State>() {

    override val initialState: State
        get() = State.IsLoading(true)

    fun cep() {
        viewModelScope.launch {
            getCep().onCollect(
                onCollect = {
                    Log.d("this", it.toString())
                    _state.value = State.ShowCep(it)
                },
                onLoading = {
                    _state.value = State.IsLoading(it)
                            },
                onError = {
                    _state.value = State.ShowError(it.toString())
                }
            )
        }
    }

    override fun handle(intent: Intent) {
        when(intent){
            Intent.GetCep -> cep()
        }
    }
}

fun getCep(): Flow<Any> {
    return flow {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val cep = retrofit.create(api::class.java).getCep("01001000")
        emit(cep)
    }
}


interface api {

    @GET("{route}/json/")
    suspend fun getCep(@Path("route") cpf: String): Any

}
