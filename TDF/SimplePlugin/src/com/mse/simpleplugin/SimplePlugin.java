package com.mse.simpleplugin;

import com.solipsys.system.Environment;
import com.solipsys.system.Environment.DynamicModule;

public class SimplePlugin implements DynamicModule {
	
	protected String name;
	protected Object state;

	public SimplePlugin() {
		name = "SimplePlugin";
		state = UNINITIALIZED;
		System.out.println("Constructing " + name);
	}
	@Override
	public void destroy() {
		System.out.println("Destroying" + name);
		state = DESTROYED;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public Object getState() {
		return state;
	}

	@Override
	public void init(Environment arg0) {
		System.out.println("Initializing " + name);
	}

	@Override
	public void start() {
		System.out.println("Starting " + name);
		state = STARTED;
	}

	@Override
	public void stop() {
		System.out.println("Stopping " + name);
		state = STOPPED;
	}

}
