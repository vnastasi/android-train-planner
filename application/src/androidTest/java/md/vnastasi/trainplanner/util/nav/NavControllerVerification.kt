package md.vnastasi.trainplanner.util.nav

import androidx.annotation.IdRes
import androidx.navigation.testing.TestNavHostController
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

fun TestNavHostController.verifyCurrentDestination(@IdRes expectedDestination: Int) {
    assertThat(this.currentDestination?.id, equalTo(expectedDestination))
}
