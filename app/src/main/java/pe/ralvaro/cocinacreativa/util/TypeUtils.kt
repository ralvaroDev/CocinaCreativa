package pe.ralvaro.cocinacreativa.util

fun <T>List<T>.shuffledIf(test: Boolean) = if (test) this else shuffled()

fun Int.toHourMinuteFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours == 0) {
        "$minutes m"
    } else "$hours h $minutes m"
}
