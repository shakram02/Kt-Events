import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock

class EventTest {

    @Test
    operator fun invoke() {
        val e = Event<Int>()
        var receivedValue = -1

        val handler: (Int) -> Unit = { arg -> receivedValue = arg }

        e += handler
        e(4)    // Invoke the event


        Assert.assertEquals(4, receivedValue)
    }

    @Test
    fun testNotify() {
        var isNotified = false
        var isNotifiedByLambda = false

        val notificationHandler: (Unit) -> Unit = { isNotified = true }
        val notification = Event<Unit>()
        notification += notificationHandler

        notification += { isNotifiedByLambda = true }
        notification(Unit)  // Invoke the event with unit

        Assert.assertTrue(isNotified)
        Assert.assertTrue(isNotifiedByLambda)
    }

    @Test
    fun clear() {
        val e = Event<Int>()
        var receivedValue = -1

        val handler: (Int) -> Unit = { arg -> receivedValue = arg }

        e += handler
        e.clear()
        e(21312)    // Nothing happens here

        Assert.assertEquals(-1, receivedValue)
    }

    @Test
    fun pooledEvent() {
        val pooledEvent = PooledEvent<Int>()
        val lockObject = Unit
        var checks = 0
        val locked = AtomicBoolean(true)

        val maxCount = 10

        for (i in 1..maxCount) {
            pooledEvent += { _ ->
                val timeout = System.nanoTime() % 10
                Thread.sleep(timeout)
                synchronized(lockObject) {
                    checks++
                }

                if (checks == maxCount) {
                    locked.set(false)
                }
            }
        }

        pooledEvent(322)    // Fire an event

        while (locked.get()) {
            Thread.sleep(10)
        }

        Assert.assertEquals(10, checks)
    }
}