package com.tododev.chain.node.impl;

import com.google.common.eventbus.Subscribe;
import com.tododev.chain.node.ChainNode;
import com.tododev.chain.node.NodeResponse;
import com.tododev.chain.node.ResponseType;

public class HelloWorldReceiver implements ChainNode {

	private String phrase;

	@Override
	public NodeResponse execute() {
		System.out.println(phrase);
		return new NodeResponse.Builder(ResponseType.Continue).build();
	}

	@Subscribe
	public void setPhrase(String object) {
		this.phrase = object; // Sets the subscribed object
	}

	public String getPhrase() {
		return phrase;
	}

}
