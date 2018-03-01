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
}