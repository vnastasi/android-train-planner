package md.vnastasi.trainplanner.login.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.textfield.TextInputEditText
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.util.matcher.TextInputLayoutMatchers.withErrorText
import md.vnastasi.trainplanner.util.matcher.TextInputLayoutMatchers.withNoErrorText
import org.hamcrest.Matchers.allOf

inline fun loginScreen(block: LoginScreen.() -> Unit) = LoginScreen().apply(block)

class LoginScreen {

    fun typeUserName(value: String) {
        onView(allOf(isAssignableFrom(TextInputEditText::class.java), isDescendantOfA(withId(R.id.input_user_name)))).perform(typeText(value), closeSoftKeyboard())
    }

    fun typePassword(value: String) {
        onView(allOf(isAssignableFrom(TextInputEditText::class.java), isDescendantOfA(withId(R.id.input_password)))).perform(typeText(value), closeSoftKeyboard())
    }

    fun clickOnLogin() {
        onView(withId(R.id.btn_login)).perform(click())
    }

    fun hasUserNameError(expectedErrorText: CharSequence) {
        onView(allOf(withId(R.id.input_user_name), withErrorText(expectedErrorText))).check(matches(isDisplayed()))
    }

    fun hasNoUserNameError() {
        onView(allOf(withId(R.id.input_user_name), withNoErrorText())).check(matches(isDisplayed()))
    }

    fun hasPasswordError(expectedErrorText: CharSequence) {
        onView(allOf(withId(R.id.input_password), withErrorText(expectedErrorText))).check(matches(isDisplayed()))
    }

    fun hasNoPasswordError() {
        onView(allOf(withId(R.id.input_password), withNoErrorText())).check(matches(isDisplayed()))
    }
}
