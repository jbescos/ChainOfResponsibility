package com.changestuffs.commons.chain.node;

import java.util.Set;

public interface ChainNode {

	boolean next();
	void execute();
	Set<Object> post();
	
}
