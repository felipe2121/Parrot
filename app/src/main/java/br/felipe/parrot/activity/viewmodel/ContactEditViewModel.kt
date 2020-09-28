package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.data.dto.main.detailcontact.ContactUpdateDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.domain.usecase.EditContactUseCase
import kotlinx.coroutines.launch

class ContactEditViewModel(
    private val editContactUseCase: EditContactUseCase
): ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    fun updateContact(inputUpdateContactName: String, inputUpdateContactEmail: String, inputUpdateContactPhone: String) = viewModelScope.launch {

        val updateContactInput = ContactUpdateDTO(inputUpdateContactName, inputUpdateContactEmail, inputUpdateContactPhone)
        editContactUseCase(EditContactUseCase.ParamsUpdateContact(updateContactInput))
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