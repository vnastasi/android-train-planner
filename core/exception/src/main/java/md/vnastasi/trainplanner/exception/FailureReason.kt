package md.vnastasi.trainplanner.exception

import java.io.Serializable

interface FailureReason: Serializable {

    val code: String

    val message: String

    companion object {

        @JvmStatic
        fun unknown(): FailureReason = object : FailureReason {
            override val code: String = "UNKNOWN"
            override val message: String = "Unknown failure"
        }
    }
}
