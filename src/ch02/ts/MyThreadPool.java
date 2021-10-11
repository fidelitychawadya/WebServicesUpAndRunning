package ch02.ts;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool extends ThreadPoolExecutor {
    private static final int poolSize = 10;
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unPaused = pauseLock.newCondition();

    public MyThreadPool(){
        super(poolSize //core pool size
                , poolSize //maximum pool
                , 0L //keep alive time for idle thread
                , TimeUnit.SECONDS //tim unit for keep-alive setting
                ,new LinkedBlockingQueue<Runnable>(poolSize));
    }

    protected void beforeExecute(Thread thread, Runnable runnable){
        super.beforeExecute(thread, runnable);
        pauseLock.lock();
        try{
            while(isPaused)
                unPaused.await();
        }
        catch (InterruptedException e){
            thread.interrupt();
        }
        finally {
            pauseLock.unlock();
        }
    }
    public void resume(){
        pauseLock.lock();
        try {
            isPaused = false;
            unPaused.signalAll();
        }
        finally {
            pauseLock.unlock();
        }
    }
}
