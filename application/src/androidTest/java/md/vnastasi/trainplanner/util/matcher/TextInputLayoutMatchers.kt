package md.vnastasi.trainplanner.util.matcher

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

object TextInputLayoutMatchers {

    fun withErrorText(expectedText: CharSequence): Matcher<View> = ErrorTextMatcher(expectedText)

    fun withNoErrorText(): Matcher<View> = ErrorTextMatcher(null)
}

private class ErrorTextMatcher(
        private val expectedText: CharSequence?
) : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

    override fun describeTo(description: Description?) {
        if (expectedText == null) {
            description?.appendText("has no error text")
        } else {
            description?.appendText("has error text ")?.appendValue(expectedText)
        }
    }

    override fun matchesSafely(item: TextInputLayout?): Boolean =
            item?.error == expectedText
}
