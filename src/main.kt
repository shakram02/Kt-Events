fun main(args: Array<String>) {
    val e = Event<Int>()

    val handler: (Int) -> Unit = { arg -> println(arg) }

    e += handler
    e.invoke(4)

    e.clear()
    e.invoke(21312)
}