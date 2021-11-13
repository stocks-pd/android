package ru.polytech.stonks.presentation.core.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ViewState, Action, Event>(initState: ViewState) : ViewModel() {

    val state: MutableState<ViewState> = mutableStateOf(initState)

    val actions: MutableSharedFlow<Action> = MutableSharedFlow()

    abstract fun obtainEvent(event: Event)

    protected fun updateState(newState: ViewState) {
        state.value = newState
    }

    protected fun callAction(action: Action) = viewModelScope.launch {
        actions.emit(action)
    }
}