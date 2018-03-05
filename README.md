# Kt-Events
[![Build Status](https://travis-ci.org/shakram02/Kt-Events.svg?branch=master)](https://travis-ci.org/shakram02/Kt-Events)
[![](https://jitpack.io/v/shakram02/Kt-Events.svg)](https://jitpack.io/#shakram02/Kt-Events)



C# Style event for kotlin [source](https://discuss.kotlinlang.org/t/c-style-events/2076)

Support for pooled events is enabled using [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)

Check [test class](https://github.com/shakram02/Kt-Events/blob/master/src/test/java/EventTest.kt)

- Use the `+=` operator to add a handler
- Use the `-=` operator to delete a handler
- Use `clear()` to delete all handlers

```kotlin
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
            val timeout = System.nanoTime() % 10000
            Thread.sleep(timeout)
            println(Thread.currentThread().name + " " + timeout)
        }
    }

    pooledEvent(322)    // Fire an event
    Thread.sleep(10000) // Wait for threads to complete
```


Interested in Kotlin coroutines? check [this](https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md#your-first-coroutine) out