# Laws Of Software Engineering

---

Source: [https://lawsofsoftwareengineering.com/](https://lawsofsoftwareengineering.com/) 

Refresh date: 56 Laws - June 19, 2026

**Architecture**

**Hyrum’s Law:** with a sufficient number of API users, all observable behaviors of your system will be depended on somebody. This law warns that any change can break something for someone. Consumers may have integrated your API in ways you didn’t expect, including relying on timing, error messages, formatting, etc. 

**Gall’s Law:** a complex system that works is invariably found to have evolved from a simple system that worked. Make a simple system that works, then iterate and add complexity. Large systems often grow organically. This law argues for creating a MVP ( minimum viable product) and then growing it, rather than a big bang approach. 

![image.png](Laws%20Of%20Software%20Engineering/image.png)

In the era of micro services, a common piece of advice influenced from Gall’s law is: don’t start with micro services, start with a monolith ( a simpler architecture) that works. As it grows, identify pieces to split off into separate services. 

**The Law Of Leaky Abstractions:** All non-trivial abstractions, to some degree, are leaky. No matter how well-designed abstractions (libraries, frameworks, etc) have edge cases that depend on internal details. (e.g memory management in high level languages). 

**Tesler’s Law (Conservation Of Complexity):** Every application has an inherent amount of irreducible complexity that can only be shifted, not eliminated. Good design often requires that developers absorb most of the complexity so that user’s interactions are simpler. This law encourages designers to move complexity away from the user experience wherever possible. 

**CAP Theorem:** A distributed system can guarantee only two of these: consistency, availability, partition tolerance. MongoDB leans toward consistency; blocking writes during a partition so all replicas stay in sync. Cassandra leans toward availability, keeping the lights on and serving queries even if replicas briefly disagree. 

- Consistency: All nodes see the same data.
- Availability: every request receives a response.
- Partition Tolerance: the system operates despite network failures.

Network partitions are unavoidable.

![image.png](Laws%20Of%20Software%20Engineering/image%201.png)

**Second-System Effect**: Small, successful systems tend to be followed by over engineered bloated replacements. This is from the Mythical Man Month. Version 2 can sometimes include bloatware or features that no one asked for. Therefore it can cause you to lose your competitive edge. 

**Fallacies Of Distributed Computing:** A set of eight false assumptions that new distributed system designers often make.

- Networks drop messages, delays, finite throughput, and can be insecure. You must account for these using retries, timeouts, security measures, and dynamic discovery.
- The fallacies often manifest in subtle bugs. You must assume that latency exists.
- Taking these fallacies into account leads to defensive design: using caches (bandwidth / latency aren’t perfect); building redundancy (networks aren’t reliable) and handling dynamic membership (topology changes).
    - The network is reliable.
    - Latency is zero.
    - Bandwidth is infinite.
    - Network is secure.
    - Topology doesn’t change.
    - There is one administrator.
    - Transport cost is zero.
    - Network is homogenous.

**Laws Of Unintended Consequences:** whenever you change a system, expect surprise. Outcomes are not entirely predictable. 

**Zawinski’s Law:** Feature creep is unavoidable. Each new feature increases complexity and maintainability, which can make the product confusing for users. Software evolution stating that applications continually gain features until they do everything, even things completely outside their original scope. This is feature creep. (e.g. a basic note taking app now adds chat or sharing). Unchecked expansion can sabotage a product’s original idea. Adding features is easy, but adding only the right features and saying “no” to the rest is essential. 

---

**Design**

**YAGNI (You aren’t gonna need it)**: Don’t add functionality until it is necessary. Focus on current tasks rather than implementing features that are not needed now. Anticipating the future leads to over-engineering and this adds complexity and maintenance issues. Don’t write code for features that haven’t been requested or aren’t immediately needed yet. 

**DRY (Don’t repeat yourself):** Every piece of knowledge must have a single unambiguous, authoritative representation. The DRY principle is about avoiding duplication of knowledge in code. If the same idea or logic is in multiple places, it's a signal to refactor. Duplication creates hidden maintenance cost. When requirements change, you should update logic in only one place. (e.g. abstracting common code into a function or class, consolidating configuration into a single file). DRY applies to database schemas, tests, and documentation. Large codebases benefit from DRY through shared libraries. If multiple applications need the same security logic, create a shared library instead of copying code across applications. (reference: Pragmatic Programmer). 

**KISS (keep it simple, stupid):** Designs and systems should be as simple as possible. A solution that satisfies the requirements and is simple in design is better than a complex one. Software has to be understood by humans. A simple design is much easier to maintain: new team members can get up to speed faster, bugs are easier to localize, and modifications cause fewer ripple effects. 

**SOLID Principles:** The five main guidelines that enhance software design, making code more maintainable and scalable.

The SOLID principles are five high-level guidelines for object-oriented design: **Single Responsibility** (one concern per class), **Open/Closed** (open for extension, closed for modification), **Liskov Substitution** (subclasses must be substitutable for their parent), **Interface Segregation** (no forced dependency on unused interfaces), and **Dependency Inversion** (depend on abstractions, not concretions).

1. SOLID leads to software that is easier to extend, test, and refactor without breaking existing functionality. 
2. A class with a single responsibility (SRP) that depends on well-defined interfaces (ISP, DIP) and uses inheritance appropriately (LSP) and polymorphism for extensions (OCP) will be easier to maintain. 
3. Changes to one part of the system won’t cascade into breakages elsewhere, because the code is loosely coupled and well-encapsulated. 
4. SOLID is not perfect, but it provides proven guidelines for object oriented programming. 

For the **Single Responsibility Principle**, consider a web application managing user accounts. An anti-SRP design might have a single `UserManager` class handling validation, database operations, sending emails, and logging. A better design separates these into `UserValidator`, `UserRepository`, `EmailService`, and a `UserRegistrationService`that orchestrates them. Each class has one focus, and if the email format changes, you only touch `EmailService`.

For the **Dependency Inversion Principle**, imagine a `NotificationService` that sends alerts. Without DIP, it directly uses `EmailSender` or `SMSSender`. With DIP, you define an `INotificationChannel` interface, and all senders implement it. The service depends only on the abstraction, making it easy to add new channels or swap in test doubles.

**Law Of Demeter:** An object should only interact with its immediate friends, not strangers. An object should only call methods of: itself, its direct components, its function parameters, or objects it creates. It should NOT navigate through one object to reach another (”don't talk to strangers”). It's the principle of least knowledge: minimize the amount of knowledge that any given object has about the overall system structure. 

If object A has a reference to object B, and B has a reference to C, A should not call methods on C through B (e.g. a.b.getC().doSomething()). 

**Principle of Least Astonishment:** Software and interfaces should behave in a way that least surprise users and other developers. System should behave in a way that matches the user’s mental model of how it ought to work. 

---

**Planning**

**Premature Optimization (Knuth’s Optimization Principle):** premature optimization is the root of all evil. We should forget about small efficiencies about 97% of the time, and focus on clean design and correct functionality. Obsessing over micro-optimizations everywhere wastes time and can make code harder to read and maintain. Get it working correctly first, then make it fast, then make it pretty. Write simple code, profile it, and then improve parts that truly need it. 

**Parkinson’s Law:** work expands to fill the time available for its completion. Loose deadlines can reduce productivity, so teams should set clear, realistic time limits. 

**The Ninety Ninety Rule:** The first 90% of the code accounts for the first 90% of development time; the remaining 10% accounts for the other 90%. The final parts of a project (e.g. polishing, edge cases, integration, bug fixes) often take far more effort than anticipated, often as much as the initial development. Do not celebrate too early. 

**Hofstadter’s Law:** It always takes longer than you expect, even when you take into account Hofstadter’s law. Humans are generally bad at estimating how long tasks take, especially for complex projects. There are always unknowns and complexities that reveal themselves during implementation, extending the timeline. Include contingency buffers in schedule, don’t be overly optimistic in planning. Delays and surprises can happen. 

**Goodhart’s Law:** When a measure becomes a target, it ceases to be a good measure (e.g. lines of code written, number of features closed). Metrics are proxies for what you value (e.g. productivity, quality, etc.). Once they’re targets, people meet the metric even if it undermines the original intent. Combine multiple metrics to avoid a singular focus that can be gamed. 

**Gilb’s Law:** Anything you need to quantify can be measured in some way better than not measuring it. It is better to have some data or metric than to be completely blind. An approximate or indirect measurement is better than nothing (e.g. performance, customer satisfaction, code maintainability).   

---

**Quality**

**Boy Scouts Rule**: leave the code better than you found it. Don’t leave bad things rot in a project. 

**Murphy’s Law / SOD’s Law:** anything that can go wrong, will go wrong. If an error can happen, it will happen. Plan and code defensively with this in mind. Add error handling, backups, and checks (e.g. check for nulls, handle exceptions, validate inputs and fail gracefully). Implement monitoring, have contingency plans. 

**Postel’s Law:** Be conservative in what you do, be liberal in what you accept from others. When your system emits data or interacts with the outside world, adhere closely to protocols and standards. When receiving data, handle variations, minor errors, or deviations when possible. Don’t crash or reject communication over minor issues. 

**Broken Windows Theory:** Don’t leave broken windows (bad decision, wrong decisions, or poor code) unresolved. A clean, well maintained codebase encourages engineers to keep it clean, whereas a chaotic codebase encourages corner-cutting and further degradation. Fix problems while they’re small. Refactor destructive code, update outdated docs to prevent a downward spiral of code health. (ref: Pragmatic Programmer). 

**Technical Debt:** everything that slows us down when developing software. Each minute spent on dirty code, bugs, and workarounds due to a lack of refactoring is interest on the debt. Not all technical debt is inherently bad. It is sometimes necessary for market timing or prototyping. The way to pay down technical debt is to refactor code, add missing tests, and improve design. 

A common example is skipping automated tests. A team under deadline pressure releases a new feature without writing tests. The release is successful (debt incurred), but later making changes becomes harder since every change risks unforeseen bugs because there's no safety net of tests (interest payments). 

Technical debt is like a small loan. It can be wise to take shortcuts (borrow) to achieve something sooner, provided you pay back the loan by cleaning up the code later. 

**Linus’ Law:** Given enough eyeballs, all bugs are shallow. The strength of peer review and community. If a codebase is accessible to many developers, someone will eventually have the expertise to identify and fix a given bug. This is a key advantage of open source software. 

The core of open source: the transparency and collaboration lead to more robust, reliable software. 

**Kernighan’s Law:** Debugging is twice as hard as writing the code in the first place. Debugging is harder because you have to understand the code + why it does not work. Simple code with good structure and documentation is easier to debug, saving time in the long run. A maintainable version is usually superior to an optimized version that is difficult to understand. 

```jsx
public string GetUserDisplay(User u) =>
     u?.IsActive == true ? (u.Name ?? "").Trim() is var n && n.Length > 0
     ? n + (u.Role > 0 ? $" ({(Role)u.Role})" : "") : "Unknown" : "Inactive";
```

versus 

```jsx
public string GetUserDisplay(User user)
{
    if (user is null || !user.IsActive)
        return "Inactive";

    var name = user.Name?.Trim();

    if (string.IsNullOrEmpty(name))
        return "Unknown";

    if (user.Role > 0)
        return $"{name} ({(Role)user.Role})";

    return name;
}
```

**Testing Pyramid:** A project should have many fast unit tests, fewer integration tests and only a small number of UI tests.  

- web e-commerce application
    - unit tests to cover : price calculation, discount logic, and validation
    - API integration tests (verifying that order placement connects inventory, payment, and notification services correctly).
    - E2E tests : entire user journey (e.g. browser, adding to cart, checkout).
    - If infrastructure teams land a change that passes your tests but breaks your product, the missing tests are your responsibility. Your test suite is your contract with the world; the absence of a test is a silent permission slip to ship broken.

![Screenshot 2026-05-28 at 5.48.22 PM.png](Laws%20Of%20Software%20Engineering/Screenshot_2026-05-28_at_5.48.22_PM.png)

**Pesticide Paradox:** Repeatedly running the same tests becomes less effective over time. When the same pesticide is used repeatedly, pests develop resistance to it. 

**Lehman’s Laws Of Software Evolution:** Software that reflects the real world must evolve, and that evolution has predictable limits. The eight laws are:

1. Continuing Change
2. Increasing Complexity
3. Self Regulation
4. Conservation of Organizational Stability
5. Conservation of Familiarity 
6. Continuing Growth 
7. Quality Degradation 
8. Feedback System 

**Sturgeon’s Law:** 90% of everything is crap. Most new concepts or technologies fail to deliver, while the exceptional ones stand out. A 10X engineer is not the engineer who writes 10X code., instead it's someone **who identifies the 10% of work that delivers 10X value. Sturgeon’s law encourages continuous refinement, ignore the noise and focus on the signal.** (e.g. an app has 100 features, user analytics show that users only use 10% of those features). 

**Teams** 

**Conway’s Law:** software systems that reflect the communication structure of the organization that builds them. A company with separate frontend, backend, and database departments will produce a three tier architecture. Small distributed teams tend to produce modular service architectures, while large, collocated teams tend to build monoliths. 

- Integration between tiers can be painful.
- Amazon designed 2 pizza teams each owning a specific service. Amazon’s architecture is service oriented, with clear API contracts between services.
    - Any efficient team should be small enough   with two pizzas (e.g 5 - 8 people). Small teams move faster because communication stays simple.

Reference : Mythical Man-Month. 

**Brook’s Law:** Adding manpower to a late software project makes it later. New people take time to get up to speed, consuming existing team member’s time for training and coordination, which reduces overall productivity. Instead of hoping manpower will solve slippage, adjust the scope or timeline. Be wary of “just hire more coders” as a solution to lateness. 

The law does not say “adding people never helps, but adding people in response to lateness can be counterproductive”. 

**Dunbar’s Law:** There is a cognitive limit of about 150 stable relationships one person can maintain. Strong collaboration happens in groups far below the 150 threshold. Small teams win. Smaller social layers can be better (10 - 15 people is when you start to lose cohesion). Around 150 people is when the culture can start to shift at a startup. 

**Ringelmann Effect:** Individual productivity decreases as group size increases. There is a point where adding people yields diminishing returns, or even negative returns per person. Small, focused teams often outperform poorly coordinated larger teams. Smaller teams or individuals feel greater responsibility and greater ownership. 

**Price’s Law:** The square root of the total number of participants does 50% of the work. Its wise to identify and retain the small group of people who are essential to the company’s output. It highlights a risk: if those top √N contributors leave, you lose a large chunk of productivity, so retention and preventing burnout for them are critical.

- A notable example is when Twitter cut staff after Musk bought it, yet the product kept running. Before the takeover, Twitter had roughly 7,500 employees, meaning √7,500 ≈ 87. Price’s law suggests that when the new leadership decided to lay off almost 50% of staff, the platform could still operate if the core 80-100 people stayed. But this holds only if the proper people are selected. The law won’t predict reliability, as layoffs strip redundancy in SRE, security, and moderation. Twitter even asked some laid-off workers to return, a signal that it missed critical skills.

**Putt’s Law:** Those who understand technology don’t manage it, and those who manage it don’t understand it. There is often a gap between deep technical understanding and management roles. Rarely does one person excel at both. Successful organizations bridge this by training managers in tech basics or promoting technically savvy leaders. The lesson is technical leadership should maintain technical competence or risk mismanaging the technical work.

- Think of a software manager who came from a sales or business background. They might push the team to add complex features at warp speed, failing to understand why quality or testing is needed. They “manage what they do not understand.”
- On the other side, a brilliant sysadmin knows precisely how to optimize infrastructure but isn’t involved in management decisions about budgeting or project direction. They “understand what they do not manage.

**Peter Principle**: In a hierarchy, every employee tends to rise to their level of incompetence. This principle explains why organisations often end up with mediocre managers. Organisations should offer dual career paths (technical vs managerial) to prevent stagnation and forcing engineers into management when they truly excel at engineering. Individual contributor route vs management route. 

**Bus factor**: the minimum number of team members whose loss would put the project in serious trouble. A bus factor of 1 means if that person dies, the project is doomed or stalled. A higher bus factor the better. (e.g 5 means, 5 people need to die before the project is doomed). Teams should work to increase their bus factor by sharing knowledge, documenting critical systems, having code reviews and rotating responsibilities. Skilled engineers have options and won’t tolerate dysfunction for long. Those who stay are often the ones who can’t easily find work elsewhere. 

**Dilbert Principle**: companies tend to promote incompetent employees to management to limit the damage they can do. Instead of addressing poor performance directly, companies often promote struggling employees into management roles where their impact is perceived as less immediately harmful. (reference: Dilbert Principle). 

The Peter Principle says competent people get promoted until they reach a role where they’re incompetent. The Dilbert Principle says incompetent people get promoted precisely because they’re incompetent.

Both lead to the same outcome (incompetent managers) but through opposite paths. Together, they explain why management quality is such a persistent challenge in organizations.

**Scale** 

**Amdahl’s Law:** The speedup from parallelization is limited by the fraction of work that cannot be parallelized. Sequential work sets the ceiling, and no amount of parallelism can overcome it. Scaling exposes bottlenecks. More resources make limits visible, not disappear. Fix before you scale, reduce sequential paths first. Decision bottlenecks can dominate at the team scale.  

As you add CPU cores, only the parallelizable fraction of your code speeds up. The sequential fraction remains unchanged and eventually dominates the total execution time. If S is the sequential function, the maximum speedup with infinite parallel resources is 1/s. so only 10% is sequential. If 50% is sequential, the fastest you can go is 2x. 

If one person or committee handles all architectural decisions, adding engineers increases coordination costs without increasing throughput. 

Adding application servers does not help if all requests hit a single database instance. Breaking a monolith into micro-services will not improve performance if all requests serialize through a shared dependency, such as an authentication or billing service. 

**Gustafson’s Law:** It is possible to achieve significant speedup in parallel processing by increasing the problem size. Software and algorithms should be designed to ‘scale out’. That means that as available processor cores or machines increase, the amount of work that can be done can be scaled up accordingly (e.g if you have a cluster that is 2x powerful, it should be able to process 2x the data; if analyzing 1 million records takes an hour, 10 machine cluster should analyze 10 million records in an hour or finish in 6 minutes). Modern distributed systems like MapReduce and Spark encourage splitting datasets into more partitions as nodes increase, keeping all processors busy. 

**Metcalfe’s Law:** The value of a network is proportional to the square of the number of numbers. If you double the number of users, the potential connections 4x. Each new user adds value to existing users by creating new opportunities for interaction. A product or platform becomes exponentially more useful as its user base grow (e.g social media apps). 

**Decisions**

**Dunning-Kruger Effect**: The less you know about something, the more confident you tend to be. Early confidence signals ignorance, not mastery. 

**Hanlon’s Razor**: never attribute to malice that which is adequately explained by stupidity or carelessness. 

**Occam’s Razor:** The simplest explanation is often the most accurate one. Simpler code is easier to understand, maintain, and debug, whereas complex code has more potential points of failure. Remove or avoid unnecessary moving parts in system architecture. When debugging, start with the simplest explanation before jumping into complex theories. 

**Sunk Cost Fallacy:** Sticking with a choice because you’ve invested time or energy in it, even when walking away helps you. Do not stay stuck on past investments. Healthy engineering organizations pivot or stop projects that no longer make sense (e.g. Google Graveyard). 

![Screenshot 2026-06-17 at 3.53.54 PM.png](Laws%20Of%20Software%20Engineering/Screenshot_2026-06-17_at_3.53.54_PM.png)

The map is not the territory: Our representations of reality are not the same as reality itself. Design docs, UML diagrams, and architecture semantics are abstractions. Don’t confuse the blueprint with the actual running software. 

**Confirmation bias:** A tendency to favor information that supports our existing beliefs or ideas. 

**The Hype Cycle & Amara’s Law:** We tend to overestimate the effect of a technology in the short run and underestimate the impact in the long run. For developers and tech leaders, do not get swept up in the hype cycle. Be patient and evaluate new tools by their proven value, not just their buzz. Good teams use stable, proven tech for most needs and adopt a hyped new technology only when it truly addresses real problems. 

**The Lindy Effect:** The longer something has been in use, the more likely it is to continue being used. It's better to invest in timeless skills and fundamentals rather than chasing every new framework that might be obsolete in a few years. 

**First principles thinking:** Breaking a complex problem into its most basic blocks and then building it up from there. 

**Inversion:** solving a problem by considering the opposite outcome and working backward from it. Use techniques like pre-mortem. Design for the worst case (e.g what if the database goes down? what if latency spikes?) Netflix’s Chaos Monkey. 

**Pareto Principle (80/20 rule):** 80% of problems result from 20% of the causes. Identify the top 20% of factors that contribute to 80% of whatever you care about. Prioritize hotspots over workflows that are rarely executed. Address the small set of high-impact issues to make most users happier quickly. ****A software team analyzed usage analytics and discovered that out of 50 features, only 10% were used by 80%.