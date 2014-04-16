package com.tododev.chain.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodeResponse {

	private final ResponseType responseType;
	private final List<Object> post;
	
	private NodeResponse(Builder builder){
		this.responseType = builder.responseType;
		this.post = Collections.unmodifiableList(builder.post);
	}
	
	public ResponseType getResponseType() {
		return responseType;
	}

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
