package com.github.diegopacheco.java.pocs.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkerManager {
	
	private static WorkerManager instance;
	private ScheduledExecutorService executor           = Executors.newScheduledThreadPool(10);
	private ScheduledExecutorService executorRecurrent  = Executors.newScheduledThreadPool(1);
	
	public synchronized static WorkerManager getInstance() {
		if(instance==null) {
			instance=new WorkerManager();
		}
		return instance;
	}
	
	public void schedule(Task t){
		executor.scheduleAtFixedRate(() -> t.execute() , 0, 1, TimeUnit.SECONDS);
	}
	
	public void scheduleRecurrent(Task t,String group,long period, TimeUnit timeUnit){
		executorRecurrent.scheduleAtFixedRate(() ->  QueueManager.getInstance().enqueueTask(t, group) , 0, period, timeUnit);
	}
	
}
