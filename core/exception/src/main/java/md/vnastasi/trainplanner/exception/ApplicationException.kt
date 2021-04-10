package md.vnastasi.trainplanner.exception

class ApplicationException(
        val failureReason: FailureReason,
        override val cause: Throwable? = null
) : Exception(failureReason.message, cause)
