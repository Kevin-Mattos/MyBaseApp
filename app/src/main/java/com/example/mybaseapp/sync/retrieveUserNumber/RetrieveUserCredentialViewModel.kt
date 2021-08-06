package com.example.mybaseapp.sync.retrieveUserNumber

import android.os.Bundle
import com.example.mybaseapp.sync.IS_PHONE_KEY
import com.example.mybaseapp.util.base.BaseMVIViewModel

sealed class RetrieveCredentialIntent {
    data class UserCredential(val number: String): RetrieveCredentialIntent()
    data class InitArgs(val args: Bundle?): RetrieveCredentialIntent()
    object GetCredentialClick: RetrieveCredentialIntent()
}

sealed class RetrieveCredentialState {
    object InitialState: RetrieveCredentialState()
    data class RetrieveUserCredential(val isPhoneCredential: Boolean): RetrieveCredentialState()
    data class ShowUserCredential(val phone: String): RetrieveCredentialState()
    data class SetView(val isPhoneCredential: Boolean): RetrieveCredentialState()
}

class RetrieveUserCredentialViewModel : BaseMVIViewModel<RetrieveCredentialIntent, RetrieveCredentialState>() {

    override val initialState: RetrieveCredentialState = RetrieveCredentialState.InitialState
    private var isPhoneCredential = false

    override fun handle(intent: RetrieveCredentialIntent) {
        when(intent) {
            is RetrieveCredentialIntent.UserCredential -> _state.value = RetrieveCredentialState.ShowUserCredential(intent.number)
            RetrieveCredentialIntent.GetCredentialClick -> _state.value = RetrieveCredentialState.RetrieveUserCredential(isPhoneCredential)
            is RetrieveCredentialIntent.InitArgs -> getArgs(intent.args)
        }
    }

    private fun getArgs(agrs: Bundle?) {
        agrs?.let {
            isPhoneCredential = agrs.getBoolean(IS_PHONE_KEY, isPhoneCredential)
        }
        _state.value = RetrieveCredentialState.SetView(isPhoneCredential)
    }
}