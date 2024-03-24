import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock

object Constants {
    val UPDATE_LOCK = ReentrantLock()
    val TRANSACTION_LOCK = ReentrantLock()
    val GET_HOMEWORKS_BY_ID_LOCK = ReentrantLock()
    val INSERT_SYNC_BARRIER = CyclicBarrier(2)
}
