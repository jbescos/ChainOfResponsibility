package com.tododev.chain.node;

/**
 * Every node in chain must implement this interface.
 * @author jbescos
 *
 */
public interface ChainNode {

	/**
	 * Do the logic of the node, post objects to other ChainNodes and define if continue, stop or repeat.
	 * @return
	 */
	NodeResponse execute();
	
}
