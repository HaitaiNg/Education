# Object-Oriented Design

Your test suite should cover these categories:

- happy path: the standard case that should obviously work.
- empathy / zero input: empty arrays, empty strings, zero values, null.
- single element: one item in a collection, one character in a string.
- boundary values: min/max integers, first/last index, exactly at a limit.
- duplicates: repeated values, duplicate keys, identical objects.
- invalid or unexpected input: negative inputs, wrong types, malformed data
- scale: if the problem has a performance component, a test with a larger input to confirm it doesn’t time out.

**Abstraction:** Simplify complex system by hiding unnecessdary details. It separates the “what” an object does from the “how” it does it. This allows users to interact objects through simplified interfaces.
Implementation: Use abstract classes and interfaces (e.g Shape.area() → Rectangle.area() | Square.area()).

**Encapsulation:** Bundling data attributes and logic as methods, and then putting related attributes / methods within a single unit called an object. The internal state stays hidden from the outside world, and access to the data or state is controlled through public methods.

- protects data integrity; controls access and improves security; modularity and reusability.

**Inheritance**: Use this for clear “is-a” relatioonships. Choose composition for a “has-a” relationship where you need flexible, swappable behaviors. Favor composition or loose coupling is key, as its preferred in modern design.

**Polymorphism**: When multiple classes need to perform the same action in different ways. Interfaces or superclasses ensure a consistent contract across implementations (e.g a media player is a good examples. All types of media have a ‘play, stop, skip’ button but the media form is different (e.g audio, video, streaming content). Each type of media has their own processing and rendering logic).

- **Method overloading:** compile-time polymorphism. Allows a class to have multiple methods with the same name but different parameters.
- **Runtime polymorphism**: a method name is used but a certain version takes precedence based on the object (e.g class Animal → sound() vs Dog → sound()

**Single Responsibility Principle:** A class should have only one reason to change, meaning it should have a single, well defined responsibility.

**Open / Closed Principle (OCP)**: Software entities (e.g classes, modules) should be open for extention but closed for modification. This promotes the idea of extending functionality without alterting existing code.

**Liskov Substitution Principle**: Subtypes (derived classes) must be substitutable for their base types (parent classes) without altering the correctness of the program.

**Interface Segregation Principle:** Clients should not be forced to depend on interfaces they don’t use. The principle encourages the creation of smaller, focused interfaces.

**Dependency Inversion Principle (DIP)**: High level modules should not depend on low level modules; both should depend on abstractions. This promotes the decoupling of components through abstractions and interfaces.

![Screenshot 2026-08-01 at 9.54.54 PM.png](Object-Oriented%20Design/Screenshot_2026-08-01_at_9.54.54_PM.png)

---

The strategy design pattern defines a family of algorithms, encapsulates each one in a separate class, and allows their objects to be interchangeable (e.g., payment methods for a payment object; each payment may be processed differently, but it's still a payment). This answers "which way should this object behave or perform this action?"

In the parking lot design, we have used the Strategy pattern to encapsulate pricing rules in the FareStrategy interface (e.g., BaseFareStrategy, PeakHoursFareStrategy), allowing FareCalculator to switch between rules dynamically without altering its core logic.

![Screenshot 2026-09-05 at 10.30.51 PM.png](Object-Oriented%20Design/Screenshot_2026-09-05_at_10.30.51_PM.png)

A SortedSet (e.g., TreeSet) is used for possibleValues to maintain sorted hand values, enabling O(log(n)) insertion and O(1) access to the lowest value. A HashSet offers O(1) insertion but lacks sorting, requiring O(N) to find the minimum. Similarly, a List could store values but would need O(N) for sorting or searching.

Use BigDecimal when dealing with money or financial transactions so you do not need to be concerned with float or double rounding errors.

**Observer pattern:** Defines a one-to-many relationship between a subject and its observers — when the subject's state changes, all registered observers are notified automatically. This is useful when the set of objects needing notification isn't known in advance or can change dynamically.

- *Example: Elevator System:* Hallway button presses notify the dispatch controller, decoupling request handling from dispatch logic and enabling efficient event-driven processing.
- *Example: News app:* Subscribers are notified automatically whenever a breaking story is published, without the publisher needing to know who they are.

**Factory pattern:** A creational pattern that centralizes object creation behind a single interface, letting subclasses decide which concrete type to instantiate. This answers the question "Which kind of object should I create?"

- *Example: Locker system:* A LockerFactory centralizes the logic for creating different locker types, making it easy to add new sizes (e.g., XLARGE) without modifying existing code.

Another exampe (e.g PaymentFactory). 

![Screenshot 2026-09-05 at 10.42.16 PM.png](Object-Oriented%20Design/Screenshot_2026-09-05_at_10.42.16_PM.png)

Composite pattern: this is used for hierachical structures or combinations of logic are required. Allows us to use multiple rules (e.g OR and AND) without hardcoing the logid. 

The Command is a behavioral design pattern that encapsulates a request as an independent object, containing all the details needed to carry it out. This encapsulation allows you to treat requests as parameters for methods, delay or schedule their execution.