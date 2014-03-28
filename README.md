#ChainOfResponsibility

This is an implementation of chain of responsibility pattern. In this implementation, each node can notify objects to others. A node is any object that implements ChainOfResponsibility, and a sequence of nodes is a chain.
Just an example

ChainOfResponsibility validateEntry = new ValidateEntry(...some data);
ChainOfResponsibility saveInDb = new SaveInDB(...some data);
ChainOfResponsibility makeOutput = new MakeOutput(...some data);

ChainProccessor proccessor = new ChainProccessor(validateEntry, saveInDb, makeOutput);
proccessor.start();

##Features

1. Modularity. It's easy to move, add and remove any node.
2. Injection. Each node is instanced outside.
3. Notifications. Any node can post object/objects to others.
4. Easy. Create as many nodes as you want, and add them to the ChainProccessor. 

##Lets go with a full example

Each ChainOfResponsibility that needs other object from others, must be annotated as @SubscribedChain. Create a method annotated as @Subscribe where you want to receive the notified object.

We create a node that post "Hello World":

public class HelloWorldNotifier implements ChainOfResponsibility{

   private String phrase;

   @Override
   public void execute() {
      phrase = "Hello World";
   }

   @Override
   public boolean next() {
      return true; //We want to call next chain (if exists)
   }

   @Override
   public Set<Object> post() { // Post Hello World to other chains
      Set<Object> objectsToPost = new HashSet<Object>();
      objectsToPost.add(phrase);
      return objectsToPost;
   }
                
}

And now, other node that will receive that string.

@SubscribedChain
public class HelloWorldReceiver implements ChainOfResponsibility{
                
   private String phrase;

   @Subscribe
   public void setString(String object) {
      this.phrase = object; //Sets the subscribed object
   }

   @Override
   public void execute() {
      System.out.println(phrase); //Will print Hello World
   }

   @Override
   public boolean next() {
      return true; //We want to call next chain (if exists)
   }

   @Override
   public Set<Object> post() {
      return null; //We don't post anything
   }
                
}

Now we start the chain. The order of nodes in ChainProccessor's constructor is the order that they will be called.

ChainOfResponsibility chainPost = new HelloWorldNotifier();
ChainOfResponsibility chainSubscribed = new HelloWorldReceiver();

ChainProccessor proccessor = new ChainProccessor(chainPost, chainSubscribed);
proccessor.start();

The ChainProccessor will call methods in this order:

1. chainPost.execute()
2. chainPost.post()
3. chainSubscribed.setString("Hello World")
4. chainPost.next()
5. chainSubscribed.execute()
6. chainSubscribed.post()
7. chainSubscribed.next() 

Good practices

1. @Subscribed methods are invoked each time that any node post an object of the same type. Normally, you should construct that methods as setters.
2. Normally wrap your post JDK objects with other objects. For example, don't post an Integer, create a new IntegerWrapper?(Integer) and post it.
3. Create a @SubscribedChain Object at first node to receive the errors that post other nodes. Then when ChainProccessor finish, you can analyze its content.
4. Make sure you tests your ChainProccessor. Maybe you have some node that depends on other, but the other doesn't exists in this proccessor.
5. Minimize mutability of posted objects to guarantee that other nodes don't modify their content. 

Dependences

1. chain-of-responsibility_1.1.2.jar
2. guava-15.0.jar
3. commons-logging.jar 
