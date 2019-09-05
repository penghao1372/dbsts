package com.cy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/***
 *从线程池中获取线程的调优
 */
public class TestThread02 {
	static BlockingQueue<Runnable> workQueue =new ArrayBlockingQueue<>(1);
	
	static ThreadFactory factory=new ThreadFactory() {
		static final String prefix="CGB1903";//前缀
		private AtomicInteger aa=new AtomicInteger(0);//新建AtomicInteger实例从0开始
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r,prefix+"-"+aa.getAndIncrement());//调用getAndIncrement方法自增长
		}
	};
	static ThreadPoolExecutor tpool= new ThreadPoolExecutor(
			3, //corePoolSize核心线程数
			5, //maximumPoolSize最大线程数
			30,//keepAliveTime空闲时间 
			TimeUnit.SECONDS,//unit时间单位
			workQueue,//workQueuev工作队列
			factory);
	static void doTask(int n) {
		tpool.execute(new Runnable() {
			
			@Override
			public void run() {
				String name=Thread.currentThread().getName();
				System.out.println(name+" execute task "+n);
				try{Thread.sleep(3000);}catch(Exception e) {}
			}
		});
	}
		public static void main(String[] args) {
			doTask(1);
			doTask(2);
			doTask(3);
			doTask(4);
			doTask(5);
			doTask(6);
		}
}
