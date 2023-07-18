package com.jyoti.user.contacts.addcontact.ui

import androidx.lifecycle.viewModelScope
import com.jyoti.core.base.LoadState
import com.jyoti.core.base.MviViewModel
import com.jyoti.core.di.DispatcherModule
import com.jyoti.core.network.SealedResult
import com.jyoti.core.util.toEvent
import com.jyoti.user.contacts.addcontact.ui.model.AddContactUiModel
import com.jyoti.user.contacts.addcontact.ui.redux.AddContactIntent
import com.jyoti.user.contacts.addcontact.ui.redux.AddContactReduceAction
import com.jyoti.user.contacts.addcontact.ui.redux.AddContactViewState
import com.jyoti.user.contacts.domain.use_case.AddContactUseCase
import com.jyoti.user.contacts.utils.isValidText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    @DispatcherModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
) :
    MviViewModel<AddContactViewState, AddContactIntent, AddContactReduceAction>(initialState = AddContactViewState.initial) {

    private fun createUser(
        name: String,
        job: String
    ) {
        viewModelScope.launch(mainDispatcher) {
            when (val sealedResult = addContactUseCase(name, job).first()) {
                is SealedResult.Error -> handle(AddContactReduceAction.CreateUserError(sealedResult.error))
                is SealedResult.Response -> handle(
                    AddContactReduceAction.CreateUserSuccess(
                        sealedResult.response ?: AddContactUiModel()
                    )
                )
                SealedResult.Unknown -> handle(AddContactReduceAction.CreateUserError(""))
                SealedResult.Ignore -> {}
            }
        }
    }

    override fun executeIntent(mviIntent: AddContactIntent) {
        when (mviIntent) {
            is AddContactIntent.CreateUser -> {

                handle(AddContactReduceAction.NameError(!mviIntent.name.isValidText()))
                handle(AddContactReduceAction.JobError(!mviIntent.job.isValidText()))

                if (mviIntent.name.isValidText() && mviIntent.job.isValidText()) {
                    handle(AddContactReduceAction.CreateUserStarted)
                    createUser(mviIntent.name, mviIntent.job)
                }
            }
        }
    }

    override fun reduce(
        state: AddContactViewState,
        reduceAction: AddContactReduceAction
    ): AddContactViewState =
        when (reduceAction) {
            is AddContactReduceAction.CreateUserError -> state.copy(
                loadState = LoadState.ERROR,
                addUserError = reduceAction.message
            )
            AddContactReduceAction.CreateUserStarted -> state.copy(loadState = LoadState.LOADING)
            is AddContactReduceAction.CreateUserSuccess -> state.copy(
                loadState = LoadState.LOADED,
                navigateUp = true.toEvent(),
                name = reduceAction.uiModel.name
            )
            is AddContactReduceAction.JobError -> state.copy(jobError = reduceAction.isError)
            is AddContactReduceAction.NameError -> state.copy(nameError = reduceAction.isError)
        }

}