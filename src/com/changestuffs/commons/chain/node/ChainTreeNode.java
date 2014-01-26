package com.changestuffs.commons.chain.node;

import java.util.Collection;

public interface ChainTreeNode extends ChainNode{

	Collection<ChainNode> enqueueChain();
	
}
