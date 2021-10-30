package md.vnastasi.trainplanner.login.repository

import md.vnastasi.trainplanner.exception.FailureReason

enum class AuthenticationFailureReason(override val message: String) : FailureReason {

    EMPTY_USER_NAME("User name value is empty"),
    EMPTY_PASSWORD("Password value is empty"),
    INVALID_CREDENTIALS("User name and password combination not found")
    ;

    override val code: String = name
}
