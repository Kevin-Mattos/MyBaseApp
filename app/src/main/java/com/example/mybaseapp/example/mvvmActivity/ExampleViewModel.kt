package com.example.mybaseapp.example.mvvmActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ExampleViewModel: ViewModel() {

    private val cep: MutableLiveData<String> = MutableLiveData()
    val cepLiveData: LiveData<String>
        get() = cep

    fun teste() = viewModelScope.launch {
        getCep2()
            .handleErrors()
            .collect {
                cep.value = it.toString()
            }
    }

}


fun getCep2(cep: String = "01001000"): Flow<Any> = flow {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val cepResponse = retrofit.create(MyApi::class.java).getCep(cep)
        emit(cepResponse)
    }

fun<T> Flow<T>.handleErrors() = catch {
    Log.d("flow", it.javaClass.name)
}


interface MyApi {

    @GET("{route}/json/")
    suspend fun getCep(@Path("route") cpf: String): Any

}