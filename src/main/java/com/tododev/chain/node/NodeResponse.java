package com.tododev.chain.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Define the response of ChainNode.
 * @author jbescos
 *
 */
public class NodeResponse {

	private final ResponseType responseType;
	private final List<Object> post;
	
	private NodeResponse(Builder builder){
		this.responseType = builder.responseType;
		this.post = ImmutableList.copyOf(builder.post);
	}
	
	/**
	 * Gets the ResponseType of a ChainNode.
	 * @return
	 */
	public ResponseType getResponseType() {
		return responseType;
	}

	/**
	 * Unmodificable list of objects to post in other ChainNodes.
	 * @return
	 */
	public List<Object> getPost() {
		return post;
	}

	public static class Builder{
		
		private final ResponseType responseType;
		private final List<Object> post = new ArrayList<Object>();
		
		public Builder(ResponseType responseType){
			this.responseType = responseType;
		}
		
		public Builder post(Object object){
			post.add(object);
			return this;
		}
		
		public NodeResponse build(){
			return new NodeResponse(this);
		}
		
	}

	@Override
	public String toString() {
		return "NodeResponse [responseType=" + responseType + ", post=" + post
				+ "]";
	}
	
}
