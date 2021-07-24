package md.vnastasi.trainplanner.login.usecase

import android.util.Base64

class EncodeCredentialsUseCase {

    fun execute(userName: String, password: String): String {
        val bytes = "   ${userName}:${password}".toByteArray(charset = Charsets.UTF_8)
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}
