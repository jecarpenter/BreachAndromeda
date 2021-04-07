

Object Oriented Programming Concept Questions

As you should know by now, there are 4 principles (pillars) of Object Oriented Programming.

Please write a 1-3 paragraphs explaining these 4 concepts further. Please provide a sufficient enough explanation about these pillars, as well as some examples to illustrate the practical use cases of these principles. Please be aware, that any copy / pasting from websites can be easily detected. Don't do that, but rather, explain the concepts in your own words, while providing code examples of each concept.

    Encapsulation

Encapsulation, the most important of the OOP pillars, is the idea that data inside an object should only be accessed 
through a public interface.
Without encapsulation, we risk parts of our application, completely unrelated to the object, making changes to the 
objects data or methods.  This can be a big risk depending on the size and complexity of your application.

"when we use a method, we only need to know what result the method will produce – we don’t need to know details about the object’s internals in order to use it. We could switch to using another object which is completely different on the inside, and not have to change any code because both objects have the same interface."

This is in short, what OOP is all about.  Making a large application out of small, easy to replace parts.  If our 
objects are all tightly coupled, and not encapsulated, that goes directly against the foundation of OOP, and it 
starts with Encapsulation.

Source - "https://press.rebus.community/programmingfundamentals/chapter/encapsulation/#:~:text=Encapsulation%20is%20one%20of%20the,parties'%20direct%20access%20to%20them."

        

    Inheritance

Inheritance is a pretty straightforward pillar of OOP.  It's all about class hierarchy, and creating a broad 
blueprint for other classes to inherit when necessary.  This concept is more to make the life of the programmers a 
bit easier, since you can write a parent class, then have child classes inherit its methods and key traits.

As an example, there are a lot of different kinds of animals.  Writing a class for every animal would be very 
difficult if you looked at each individual animal from scratch. The way to implement inheritance in this situation 
would be to create a broad "Animal" class, and evaluate common characteristics that are shared among all the 
animals who need dedicated classes.  You could include methods such as feed(), sleep(), hydrate(), and any other 
common tasks or characteristics that are shared.

Now with a common parent, or "super" class to inherit from, it can be a more streamlined process of building out 
child classes, where all you will need to do is evaluate what additional methods or characteristics would set them 
apart from their parent class.


    Abstraction

Abstraction is a bit more of a tricky piece to cover in the OOP space, because to me it's mostly the same as 
Encapsulation.
Abstraction as a concept is all about "hiding the implementation detail". 

For example: when you want to use your coffee maker, you need to know how much water to put in, how many beans, and  
how to select what type of coffee you would like to brew.  You don't need to know how each mechanism of the machine 
operates on the inside.  That's implementation detail, and it's hidden from the user by design.  This is abstraction.

We follow the abstraction principle because as developers, you don't need your whole application to know every piece 
of the details of an object.  You just need the necessary components that will be using the object, to know just how 
to use it, and nothing more.

Source - "https://stackify.com/oop-concept-abstraction/"

    Polymorphism

Polymorphism describes the concept that different classes can inherit the same interface, but each of the different 
classes have different implementations.  There are multiple different types of Polymorphism in Java.  

The first being Static polymorphism, which is overloading a class's method with different parameters.  The method that gets 
run depends solely on which parameters you pass in.  For instance, you can have two methods with the same name, but 
different parameter requirements.  It could be that one method takes two parameters, the other takes 3, or maybe it 
also takes two parameters of different data types.  The only difference being the implementation detail.

The second type of polymorphism in Java is called Dynamic polymorphism.  It occurs when inheriting a method from a 
class's parent, or "super" class.  When you override a method inherited, you instantiate the subclass, and the JVM 
will always call the overridden method.


pol·y·mor·phism - "the condition of occurring in several different forms."

The name is reflective, and carefully chosen to describe this principle.  

Polymorphism doesn't fully work without the other pillars of OOP.  That's why there are four of them.  To practice 
OOP properly, you must keep in mind these four pillars, and put them to practice achieving true OOP. 

Source - "https://stackify.com/oop-concept-polymorphism/"