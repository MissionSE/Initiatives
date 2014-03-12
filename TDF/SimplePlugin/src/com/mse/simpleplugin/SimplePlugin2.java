package com.mse.simpleplugin;

import com.solipsys.dynamicloader.Plugin;

public class SimplePlugin2 implements Plugin {
	private String name;
	
	public SimplePlugin2() {
		name = "SimplePlugin2";
		System.out.println("Constructing Plugin " + name);
		
	}
	
}
