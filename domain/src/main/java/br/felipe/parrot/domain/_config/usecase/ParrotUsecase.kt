package br.felipe.parrot.domain._config.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.onFailure
import br.felipe.parrot.core.util.onSuccess
import kotlinx.coroutines.*

interface IParrotUseCase<PARAMS, RESULT, LIVERESULT> {
    suspend fun liveResult(params: PARAMS? = null): LIVERESULT
    suspend fun execute(params: PARAMS? = null): ParrotResult<RESULT>
}

abstract class ParrotUseCase<PARAMS, RESULT, out EXECUTOR : ParrotUseCase.UseCaseExecutor<PARAMS, RESULT>, LIVERESULT> private constructor() : IParrotUseCase<PARAMS, RESULT, LIVERESULT> {

    abstract class UseCaseExecutor<PARAMS, RESULT>(var params: PARAMS? = null): CoroutineScope by MainScope() {
        abstract suspend fun execute()
    }

    abstract class Completable<PARAMS, RESULT, LIVERESULT> : ParrotUseCase<PARAMS, RESULT, Completable<PARAMS, RESULT, LIVERESULT>.CompletableUseCaseExecutor, LIVERESULT>() {

        inner class CompletableUseCaseExecutor : UseCaseExecutor<PARAMS, RESULT>() {

            private var _onStarted = { }
            private var _onSuccess: (RESULT) -> Unit = { }
            private var _onFailure: (ParrotException) -> Unit = { }
            private var _onFinish = { }

            fun onStarted(callback: () -> Unit) = apply { _onStarted = callback }

            fun onSuccess(callback: (RESULT) -> Unit) = apply { _onSuccess = callback }

            fun onFailure(callback: (ParrotException) -> Unit) = apply { _onFailure = callback }

            fun onFinish(callback: () -> Unit) = apply { _onFinish = callback }

            override suspend fun execute() {

                launch {
                    _onStarted()
                    try{
                        withContext(Dispatchers.Default) { execute(params) }
                            .onSuccess {
                                _onSuccess(it)
                            }
                            .onFailure {
                                if (it is ParrotException) _onFailure(it) else _onFailure(ParrotException(cause = it))
                            }
                    } catch (e: Exception) {
                        if (e is ParrotException) _onFailure(e) else _onFailure(ParrotException(cause = e))
                    }
                    _onFinish()
                }
            }
        }

        sealed class State {
            object Idle : State()
            object Executing : State()
            class Success(val hasContent: Boolean) : State()
            sealed class Error(val exception: ParrotException) : State() {
                class Connection(exception: ParrotException) : Error(exception)
                class Generic(exception: ParrotException) : Error(exception)
            }
        }

        override val useCaseExecutor = CompletableUseCaseExecutor()
    }

    abstract val useCaseExecutor: EXECUTOR

    override suspend fun liveResult(params: PARAMS?): LIVERESULT {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun execute(params: PARAMS?): ParrotResult<RESULT> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    operator fun invoke(params: PARAMS? = null): EXECUTOR {
        return useCaseExecutor.apply { this.params = params }
    }
}