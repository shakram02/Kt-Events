import kotlinx.coroutines.experimental.launch
import java.util.concurrent.Executors
import kotlin.concurrent.thread

/**
 * C# Style events
 */
open class Event<T> {
    protected val handlers = arrayListOf<(T) -> Unit>()
    operator fun plusAssign(handler: (T) -> Unit) {
        // TODO: run on separate threads or executors
        handlers.add(handler)
    }

    operator fun minusAssign(handler: (T) -> Unit) {
        handlers.remove(handler)
    }

    open operator fun invoke(value: T) {
        handlers.forEach { it(value) }
    }

    fun clear() {
        handlers.clear()
    }
}

class PooledEvent<T> : Event<T>() {
    override fun invoke(value: T) {
        handlers.forEach { h -> launch { h(value) } }
    }
}
