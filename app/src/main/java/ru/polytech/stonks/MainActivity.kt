package ru.polytech.stonks

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import ru.polytech.stonks.presentation.feathers.catalog.CatalogFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)


    }
}