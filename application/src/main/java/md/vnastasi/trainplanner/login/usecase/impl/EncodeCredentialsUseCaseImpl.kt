package md.vnastasi.trainplanner.login.usecase.impl

import android.util.Base64
import md.vnastasi.trainplanner.login.usecase.EncodeCredentialsUseCase

internal class EncodeCredentialsUseCaseImpl : EncodeCredentialsUseCase {

    override fun execute(userName: String, password: String): String {
        val bytes = "${userName}:${password}".toByteArray(charset = Charsets.UTF_8)
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}
