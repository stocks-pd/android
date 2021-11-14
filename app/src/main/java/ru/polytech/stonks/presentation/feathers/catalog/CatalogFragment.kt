package ru.polytech.stonks.presentation.feathers.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import ru.polytech.stonks.app.appComponent
import ru.polytech.stonks.presentation.core.base.BaseFragment
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogAction
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogEvent
import ru.polytech.stonks.presentation.feathers.catalog.ui.CatalogScreen
import javax.inject.Inject

class CatalogFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: CatalogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.actions.collect(::obtainAction)
        }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.obtainEvent(CatalogEvent.OnCreate)
    }

    private fun obtainAction(action: CatalogAction) {

    }
}