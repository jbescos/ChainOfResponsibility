#ChainOfResponsibility

This is an implementation of chain of responsibility pattern. In this implementation, each node can notify objects to others. A node is any object that implements ChainNode, and a sequence of nodes is a ChainOfResponsibility.
##Just an example
<pre>
ChainNode validateEntry = new ValidateEntry(...some data);
ChainNode saveInDb = new SaveInDB(...some data);
ChainNode makeOutput = new MakeOutput(...some data);

ChainOfResponsibility chain = new DefaultChain(validateEntry, saveInDb, makeOutput);
chain.start();
</pre>
##Features

1. Modularity. It's easy to move, add and remove any node.
2. Injection. Each node is instanced outside.
3. Notifications. Any node can post object/objects to others.
4. Easy. Create as many nodes as you want, and add them to the ChainProccessor. 

##Lets go with a full example

We create a node that post "Hello World":
<pre>
public class HelloWorldNotifier implements ChainNode{

	public static final String HELLO_WORLD = "Hello World";
	
	@Override
	public NodeResponse execute() {
		return new NodeResponse.Builder(ResponseType.Continue).post(HELLO_WORLD).build();
	}

}
</pre>
And now, other node that will receive that string.
<pre>
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
</pre>
Now we start the chain. The order of nodes in DefaultChain's constructor is the order that they will be called.
<pre>
HelloWorldNotifier notifier = new HelloWorldNotifier();
HelloWorldReceiver receiver = new HelloWorldReceiver();
ChainOfResponsibility chain = new DefaultChain(notifier, receiver);
chain.start();
</pre>
The ChainProccessor will call methods in this order:

1. notifier.execute()
2. receiver.setPhrase("Hello World")
3. receiver.execute() -> Prints "Hello World"

##Good practices

1. @Subscribed methods are invoked each time that any node post an object of the same type. Normally, you should construct that methods as setters.
2. Normally wrap your post JDK objects with other objects. For example, don't post an Integer, create a new IntegerWrapper?(Integer) and post it.
3. Create a ChainNode at first node to receive the errors that post other nodes. Then when DefaultChain finish, you can analyze its content.
4. Make sure you tests your chain. Maybe you have some node that depends on other, but the other doesn't exists in this proccessor.
5. Minimize mutability of posted objects to guarantee that other nodes don't modify their content. 

##Dependences
<ul>
<li><a href="http://code.google.com/p/guava-libraries/">com.google.guava 16.0.1</a></li>
<li><a href="http://commons.apache.org/proper/commons-logging/">commons-logging 1.1.3</a></li>
</ul>

##Download
<ul>
<li><a href="https://github.com/jbescos/ChainOfResponsibility/blob/master/dist/com.tododev.chain-2.0.0.jar">com.tododev.chain-2.0.0.jar</a></li>
</ul>
