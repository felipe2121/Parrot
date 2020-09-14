package br.felipe.parrot.core

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.StringWrapper

sealed class ViewState {
    object NoState: ViewState()
    object LoadingState: ViewState()
    object EmptyState: ViewState()
    // class ErrorState(val message: StringWrapper): ViewState()
    class ErrorState(val message: ParrotException): ViewState()
}