package md.vnastasi.trainplanner.util.nav

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController

fun Fragment.replaceNavController(
        navController: TestNavHostController,
        @NavigationRes graph: Int,
        @IdRes currentDestination: Int
) {
    this.viewLifecycleOwnerLiveData.observeForever { owner ->
        if (owner != null) {
            navController.setGraph(graph)
            navController.setCurrentDestination(currentDestination)
            Navigation.setViewNavController(this.requireView(), navController)
        }
    }
}
