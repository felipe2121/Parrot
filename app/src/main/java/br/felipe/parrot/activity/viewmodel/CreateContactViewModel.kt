package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.data.dto.main.createcontact.CreateContactSendDTO
import br.felipe.parrot.domain.usecase.CreateContactUseCase
import br.felipe.parrot.domain.usecase.CreateContactUseCase.ParamsCreateContact
import kotlinx.coroutines.launch

class CreateContactViewModel(
    private val createContactUseCase: CreateContactUseCase
):ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    fun createContact(inputNewContactName: String, inputNewContactEmail: String, inputNewContactPhone: String) = viewModelScope.launch {

        val newContactInput =
            CreateContactSendDTO(
                inputNewContactName,
                inputNewContactEmail,
                inputNewContactPhone
            )
        createContactUseCase(ParamsCreateContact(newContactInput))
            .onStarted {
                _viewState.value = ViewState.LoadingState
            }.onSuccess {
                _viewState.value = ViewState.IdleState
            }.onFailure {
                _viewState.value = ViewState.ErrorState(it)
            }.onFinish {

            }.execute()
    }
}