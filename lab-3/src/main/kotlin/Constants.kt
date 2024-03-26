import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock

object Constants {
    val UPDATE_LOCK = ReentrantLock()
    val OUTPUT_LOCK = ReentrantLock()
    val INSERT_SYNC_BARRIER = CyclicBarrier(2)
}
