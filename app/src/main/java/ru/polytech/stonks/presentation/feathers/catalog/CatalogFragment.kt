package ru.polytech.stonks.presentation.feathers.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import ru.polytech.stonks.app.appComponent
import ru.polytech.stonks.presentation.core.base.BaseFragment
import ru.polytech.stonks.presentation.feathers.catalog.ui.CatalogScreen
import javax.inject.Inject

class CatalogFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: CatalogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CatalogScreen(
                    modelState = viewModel.state,
                    consumer = viewModel::obtainEvent
                )
            }
        }
    }
}