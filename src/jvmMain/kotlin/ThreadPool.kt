import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * creator:  lt  2019/7/11--11:07    lt.dygzs@qq.com
 * effect : 线程池工厂
 * warning:
 */
object ThreadPool {
    val singleThreadExecutor: ExecutorService = createSingleThreadPool() //单例线程池

    /**
     * 提交任务到单例线程池,如果有多个任务同时提交,则会按照顺序线性执行
     * 需要线性执行的任务或一般不会交叉执行的任务
     */
    fun submitToSingleThreadPool(runnable: Runnable): Future<*> =
        singleThreadExecutor.submit(runnable)

    fun <T> submitToSingleThreadPool(callable: Callable<T>): Future<T> =
        singleThreadExecutor.submit(callable)

    //创建单例线程池
    private fun createSingleThreadPool(): ExecutorService =
        Executors.newSingleThreadExecutor()
}