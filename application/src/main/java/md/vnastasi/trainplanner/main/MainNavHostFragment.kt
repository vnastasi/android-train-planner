package md.vnastasi.trainplanner.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import org.koin.android.ext.android.get

class MainNavHostFragment : NavHostFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = get<MainNavHostFragmentFactory>()
        super.onCreate(savedInstanceState)
    }
}
