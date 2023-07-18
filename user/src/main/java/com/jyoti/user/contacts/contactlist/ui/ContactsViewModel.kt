package com.jyoti.user.contacts.contactlist.ui

import androidx.lifecycle.viewModelScope
import com.jyoti.core.base.LoadState
import com.jyoti.core.base.MviViewModel
import com.jyoti.core.di.DispatcherModule
import com.jyoti.core.network.SealedResult
import com.jyoti.user.contacts.contactlist.ui.model.ContactsUiModel
import com.jyoti.user.contacts.contactlist.ui.redux.ContactsIntent
import com.jyoti.user.contacts.contactlist.ui.redux.ContactsReduceAction
import com.jyoti.user.contacts.contactlist.ui.redux.ContactsViewState
import com.jyoti.user.contacts.domain.use_case.ContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsUseCase: ContactsUseCase,
    @DispatcherModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
) :
    MviViewModel<ContactsViewState, ContactsIntent, ContactsReduceAction>(initialState = ContactsViewState.initial) {

    private fun fetchUsers(
        page: Int,
    ) {
        viewModelScope.launch(mainDispatcher) {
            when (val sealedResult = contactsUseCase(page).first()) {
                is SealedResult.Error -> handle(ContactsReduceAction.FetchUserError(sealedResult.error))
                is SealedResult.Response -> handle(
                    ContactsReduceAction.FetchUserSuccess(
                        sealedResult.response ?: ContactsUiModel()
                    )
                )
                SealedResult.Unknown -> handle(ContactsReduceAction.FetchUserError(""))
                SealedResult.Ignore -> {}
            }
        }
    }

    override fun executeIntent(mviIntent: ContactsIntent) {
        when (mviIntent) {
            is ContactsIntent.FetchUsers -> {
                if (state.value.totalPages >= mviIntent.page) {
                    handle(ContactsReduceAction.FetchUserStarted)
                    fetchUsers(mviIntent.page)
                }
            }
            is ContactsIntent.SelectContact -> handle(
                ContactsReduceAction.ContactSelected(
                    mviIntent.id
                )
            )
        }
    }

    override fun reduce(
        state: ContactsViewState,
        reduceAction: ContactsReduceAction
    ): ContactsViewState =
        when (reduceAction) {
            is ContactsReduceAction.ContactSelected -> state.copy(selectedId = if (state.selectedId != reduceAction.id) reduceAction.id else -1)
            is ContactsReduceAction.FetchUserError -> state.copy(
                loadState = LoadState.ERROR,
                fetchUserError = reduceAction.message
            )
            ContactsReduceAction.FetchUserStarted -> state.copy(loadState = LoadState.LOADING, loadMore = false)
            is ContactsReduceAction.FetchUserSuccess -> {
                state.copy(
                    page = reduceAction.uiModel.page + 1,
                    totalPages = reduceAction.uiModel.totalPages,
                    contacts = state.contacts.toMutableList().apply {
                        addAll(reduceAction.uiModel.data)
                    },
                    loadMore = reduceAction.uiModel.data.isNotEmpty(),
                    loadState = LoadState.LOADED
                )
            }
        }
}