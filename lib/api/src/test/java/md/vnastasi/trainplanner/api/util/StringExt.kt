package md.vnastasi.trainplanner.api.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun String.toOffsetDateTime(pattern: String = "yyyy-MM-dd'T'HH:mmX"): OffsetDateTime = OffsetDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))
