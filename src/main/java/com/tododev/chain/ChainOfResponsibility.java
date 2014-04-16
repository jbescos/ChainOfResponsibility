package com.tododev.chain;

/**
 * Define the contract of any chain of responsibility processor
 * @author jbescos
 *
 */
public interface ChainOfResponsibility {

	/**
	 * The point to start the chain process
	 */
	void start();
	
}
