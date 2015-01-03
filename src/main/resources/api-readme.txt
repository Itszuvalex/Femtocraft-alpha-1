////////////////////////////////////////////
For aspiring modders looking to use the API
////////////////////////////////////////////


Hey!  Thanks for showing interest.  What follows will be a quick guide on how to use this API and (eventually) how to package it with your code.





For Java coders -
http://lampwww.epfl.ch/~michelou/scala/using-scala-from-java.html

This is a nice little guide on how to utilze Scala compiled .class es from Java code.
It's actually a lot simpler than you would think, even though it may seem a little imposing.  It's the '$'s, I think.

Anyways, I've done my best to keep it clean for you.

Some of my own design choices:
    1.  If you see a I(Something) interface, and a following (Something) static class, with (Something)$ and (Something)$Class, don't be frightened.
            You can safely assume all the code you don't see only relies on the I(Something) class.  The other one is a default implementation which you are
            welcome to use.  To use the default implementation, just implement the (Something) interface, instead of the I(Something) interface,
            and forward all calls to (Something)$Class functions with the same name, passing the current object as the first parameter.
            Keep in mind that if you do use the default implementation, it's probably a good idea to just forward
            all of the available calls to the (Something)$Class static functions.  They may set some behavior and are expecting
            that same behavior in other calls.
    2. Enumerations and Annotations are still in Java.  It's just easier that way.
    3. At the time I'm writing this, the api comes with a scaladoc jar.  If you're not using it as Scala but as Java, then
            you may think that this is completely worthless.  It's not.  Just unzip the scaladoc .jar somewhere and open up
            the file called 'index' in your web browser of choice.  Even though the classes you see are the scala equivalents,
            it will still come in handy, seeing as the concrete classes are still the same, as well as the functions you will need
            to call.

Some hints:
    1.  Don't be intimidated by the larger number of classes.  You will probably only ever need to touch three varieties
            (if they even exist).  These are (SomeClass), (SomeClass)$, or in certain circumstances (SomeClass)$Class.
            Everything else is just Scala generated classes to house some of its more functional traits.  You should never
            have to worry about talking to anything in those classes at all.
