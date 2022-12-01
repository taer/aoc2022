import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun <T> Sequence<T>.split(predicate: (T) -> Boolean): Sequence<List<T>> {
    val iterator = this.iterator()
    val buffer = mutableListOf<T>()

    return sequence {
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (predicate.invoke(element)) {
                yield(buffer.toList())
                buffer.clear()
            } else {
                buffer.add(element)
            }
        }
        if (buffer.isNotEmpty()) {
            yield(buffer.toList())
        }
    }
}