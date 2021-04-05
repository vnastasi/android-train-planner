package md.vnastasi.trainplanner.exception

class ApplicationException(
        val failureReason: FailureReason,
        override val cause: Throwable? = null
) : Exception(message = failureReason.message, cause = cause)
