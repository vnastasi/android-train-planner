package md.vnastasi.trainplanner.exception

import java.io.Serializable

interface FailureReason: Serializable {

    val code: String

    val message: String
}
