package ru.polytech.stonks.presentation.core.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel<ViewState, Action, Event>(initState: ViewState) : ViewModel() {

    val state: MutableState<ViewState> = mutableStateOf(initState)

    val actions: MutableSharedFlow<Action> = MutableSharedFlow()

    abstract fun obtainEvent(event: Event)
}