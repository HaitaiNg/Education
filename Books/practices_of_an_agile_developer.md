# Practices of an Agile Developer

## Core Practices

### Tackle Small Problems First
- **Make mistakes early.**  
- Avoid quick hacks; invest the energy to keep code **clean** and **organized**.  

### Criticize Ideas, Not People
- Focus on arriving at the **best solution** rather than proving whose idea is better.  
- Foster a culture where teams openly discuss the merits and drawbacks of multiple solutions.  

### Surround Yourself with Better Developers
- If you're the best on the team, you may lack motivation to improve.  
- Working with stronger developers pushes you to **level up** and grow faster.  

---

## Project Management

### Always Maintain a Releasable State
- Ensure your project is always:
  - **Compilable**
  - **Runnable**
  - **Tested**
  - **Ready to deploy**  

### Integration Best Practices
- Integrate **early** and **often** to reduce risk.  
- Develop in increments and release minimal yet **usable** chunks of functionality.  

---

## Testing

### Unit Testing
- Provides **instant feedback** and ensures robust code.  
- Acts as a **design tool** and **confidence booster**.  
- Use automated unit tests to actively identify problems before they escalate.  

### Best Practices for Testing
- Don't modify code or design without having **solid tests** in place.  
- Use **continuous integration tools** to run tests across all supported platforms and environments.  

---

## Code Quality

### Keep Classes and Components Small
- Avoid large, catch-all classes.  
- Keep your codebase modular and focused.  

### Treat Warnings as Errors
- **No checked-in code** should generate warnings — treat them as seriously as test failures.  

### Share Code Only When Ready
- Never commit code that:
  - Doesn't compile
  - Fails its tests
- Deliberately checking in broken code is **negligent**.  

### Code Reviews
- Conduct reviews after each task in **small chunks** with different developers.  
- Reviews improve code quality and reduce error rates when done continuously and effectively.  

---

## Error Handling

### Distinguishing Types of Errors
- **Program Defects:** Includes bugs like `NullPointerException` or missing keys/values.  
- **Environmental Problems:** Includes issues such as:
  - Database connection failures  
  - Remote web service outages  
  - Full disk  
  - Insufficient permissions  

> **Note:** Environmental problems often require defensive coding strategies rather than traditional bug fixes.  

---

## Mentorship
- **Be a mentor.** Sharing your knowledge helps both you and others grow.  
- Mentorship is an opportunity to inspire and guide others toward better solutions.  

---

## Design Patterns

### Singleton Pattern
- Ensures only **one instance** of a class is created.  
- Useful for managing shared resources like database connections.  

### Creational Patterns
- Focuses on class instantiation and object creation. Examples:
  - **Factory Method**
  - **Abstract Factory**
  - **Singleton**
  - **Object Pool**
  - **Prototype**

> Use a **Factory Pattern** when you want to create multiple instances of similar types while maintaining **loose coupling**.

### Structural Patterns
- Focuses on organizing different classes and objects to form larger, cohesive structures.  
- **Adapter Pattern:** Converts an interface into one expected by the client, allowing otherwise incompatible classes to work together.  

### Behavioral Patterns
- Focuses on common communication patterns between objects.  
- **Observer Pattern:** Defines a **one-to-many** dependency where dependent objects are automatically updated when the state of one object changes.  

> Use this pattern when multiple objects need to respond to state changes in a central object.  

---

## Final Note
> **"Actively find problems before they find you."**  
Consistently apply these practices to build resilient, maintainable, and high-quality software.
