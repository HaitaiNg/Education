# Notes from: C++ Primer & Software Design & Algorithms

### Encapsulation
- Protect object states by keeping data members private.
- Changes to implementation only require updates to the class itself, minimizing user-level code changes.
- Private data reduces unintended data corruption and helps localize bugs.

### Static Members
- Declared with the `static` keyword; can be `public` or `private`.
- Static data members are not part of object instances but can be accessed through objects, references, or pointers.

### Data Abstraction
- Focuses on interfaces rather than internal representation.
- Encourages clean design and simplifies code maintenance.

### Polymorphism
- Enables multiple types to behave similarly through inheritance.
- Virtual functions are resolved at runtime.

### Refactoring
- Moving operations or data between classes to improve hierarchy.

### Abstract Base Class
- Contains one or more pure virtual functions; cannot be instantiated directly.

### Dynamic Binding
- Resolves virtual function calls at runtime.

### Templates
- Blueprints for generating classes or functions with generic types.
- Class templates require explicit type specification.

### Containers
- **Associative Containers:** Use keys for storage and retrieval (e.g., `map`, `set`).
- **Sequential Containers:** Store elements in linear order.

### Smart Pointers
- Manage dynamic memory safely with automatic deletion:
  - `shared_ptr`: Shared ownership; deleted when no references remain.
  - `unique_ptr`: Sole ownership; deleted when destroyed.

### Dynamic Memory Management
- Common issues include:
  1. Forgetting to delete memory (memory leaks).
  2. Using deleted objects (nullifying pointers helps reduce this risk).
  3. Deleting memory twice (multiple references to the same object).

### Tree Traversals
- **PreOrder:** root -> left -> right
- **InOrder:** left -> root -> right
- **PostOrder:** left -> right -> root

### Recursive Fibonacci Algorithm
```cpp
int fib(int n) {
    if (n <= 0) return 0;
    else if (n == 1) return 1;
    else return fib(n - 1) + fib(n - 2);
}
```

### Static in `main()`
- `static` in `main()` indicates no calling object; methods must be accessed directly or through objects.

### Overloading Methods
- Multiple methods with the same name but differing parameter lists.
```cpp
public SimpleCoordinates() { lat = 100; log = 500; }

public SimpleCoordinates(int latitude, int longitude) {
    mLat = latitude;
    mLog = longitude;
}
```

### Binary Search
- Efficient algorithm requiring sorted data; compares against the median to identify the correct half.

### Inheritance
- Encourages shared attributes and operations.
- **Ensure derived classes are substitutable for base classes** or consider composition instead.

### Abstraction
- Reduces complexity by hiding non-essential details.

### Aggregation & Composition
- **Aggregation:** Part-of relationship with less strict ownership.
- **Composition:** Strong ownership where objects are managed by their parent.

### Design Principles
- Follow the **Single Responsibility Principle:** Each class, function, or variable should serve one distinct purpose.

