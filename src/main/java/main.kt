fun main(args: Array<String>) {
    val e = Event<Int>()

    val handler: (Int) -> Unit = { arg -> println(arg) }

    e += handler
    e(4)    // Invoke the event

    e.clear()
    e(21312)


    val notificationHandler: (Unit) -> Unit = { println("Notified") }
    val notification = Event<Unit>()

    notification += notificationHandler
    notification(Unit)  // Invoke the event with unit

    val pooledEvent = PooledEvent<Int>()

    for (i in 1..10) {
        pooledEvent += { _ ->
            // run{} is used just to type code on multiple lines
            run {
                val timeout = System.nanoTime() % 10000
                Thread.sleep(timeout)
                println(Thread.currentThread().name + " " + timeout)
            }
        }
    }

    pooledEvent(322)    // Fire an event
    Thread.sleep(10000) // Wait for threads to complete
}