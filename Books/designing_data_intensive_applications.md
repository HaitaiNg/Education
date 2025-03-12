# Reliable, Scalable, and Maintainable Systems

## Chapter One: Reliable, Scalable, and Maintainable Systems

### Reliability
The system should continue to work correctly, performing the correct function at the desired level.

A fault is defined as one component of the system deviating from its expected behavior. A failure occurs when the system as a whole stops providing the required service to the user. Hardware faults are an inevitable part of system design and must be mitigated.

### Scalability
As the system grows (e.g., volume, traffic), it must be able to handle increased load effectively.

#### Horizontal vs Vertical Scalability
- **Horizontal scaling**: Adding more machines or virtual machines to distribute the load.
- **Vertical scaling**: Upgrading existing machines with more powerful hardware.
- **Elastic systems**: Systems that can automatically add more resources as demand increases.

### Maintainability
#### Operability
- Make it easy for operations teams to keep the system running smoothly.
- Good operations can compensate for incomplete software, but good software cannot compensate for bad operations.
- Monitor system health, track down root causes, and anticipate future problems.
- Provide visibility into runtime behavior.
- Avoid dependency on individual machines.
- Ensure good documentation and self-healing mechanisms.

#### Simplicity
- Minimize complexity to make it easier for engineers to understand and maintain the system.
- Reduce hidden assumptions, unintended consequences, and unexpected interactions.
- Simpler systems improve maintainability and reduce the risk of bugs.

#### Abstraction
- A good abstraction hides implementation details behind a clean, simple interface.

#### Evolvability
- Make it easy for engineers to adapt the system to new requirements as they arise.
- Agile methodologies support this adaptability.
- Systems must meet both functional (e.g., data storage and retrieval) and non-functional (e.g., security, reliability, compliance) requirements.

---

## Chapter Two: Data Models and Query Languages

### Relational Model
- Data is organized into relations (tables in SQL) where each relation is an unordered set of tuples.

### NoSQL
- Emerged in 2010 to address scalability, throughput, and schema flexibility limitations of relational databases.
- Advantages: schema flexibility, better performance for specific queries, improved scalability.
- Relational databases provide stronger support for structured data and ACID guarantees.

### Choosing a Data Model
- Document databases work well for hierarchical and self-contained data.
- Relational databases can be cumbersome if data must be split across multiple tables.
- Graph databases are optimal for highly interconnected data.

---

## Chapter Three: Storage & Retrieval

### Storage Engine Categories
- **OLTP (Online Transaction Processing)**: Handles frequent, small transactions with indexes optimized for fast lookups.
- **OLAP (Online Analytical Processing)**: Handles large-scale analytics with column-oriented storage for efficient scanning.

### Storage Technologies
- Document databases: Optimized for scenarios with minimal relationships between records.
- Graph databases: Designed for highly connected data.
- Data warehouses: Relational structure optimized for analytical queries.

---

## Chapter Four: Encoding and Evolution

- **Encoding (Serialization)**: Conversion from in-memory representation to a byte sequence.
- **Decoding (Deserialization)**: Conversion from a byte sequence back to in-memory representation.

---

## Part II: Distributed Data

### Chapter Five: Replication

#### Partitioning
- Splitting a large database into smaller segments assigned to different nodes.

#### Replication Strategies
- **Synchronous replication**: Ensures followers have an up-to-date copy but can cause downtime if a follower is unavailable.
- **Asynchronous replication**: The leader sends updates without waiting for acknowledgment from followers.
- **Semi-synchronous replication**: A compromise where one follower is synchronous while others are asynchronous.

#### Handling Node Failures
- **Follower failure**: Catch-up recovery from logs.
- **Leader failure**: Requires failover, reconfiguring clients, and electing a new leader.
- **Split-brain scenario**: Occurs when multiple nodes believe they are leaders, leading to data inconsistencies.

#### Multi-Leader Replication
- Allows multiple nodes to accept writes, with conflicts resolved asynchronously.
- Example: Google Docs requires locking a document before editing to prevent conflicts.

#### Leaderless Replication
- No single leader, allowing any node to accept writes.
- Uses techniques like read repair and anti-entropy processes for consistency.

### Database Topologies
- **Circular topology**: Can be interrupted if one node fails.
- **All-to-all topology**: More resilient, as messages can travel different paths.

### Replication Purposes
- **High availability**: Keeping the system operational despite failures.
- **Disconnected operation**: Allowing work offline before syncing later.
- **Latency reduction**: Placing data closer to users.
- **Scalability**: Distributing workload across multiple nodes.

---

This document serves as a high-level overview of designing reliable, scalable, and maintainable systems, focusing on data models, storage, retrieval, encoding, and distributed data concepts.


## Chapter Six: Partitioning

### What is Partitioning?
Partitioning is the process of breaking up data into smaller partitions. This improves scalability by distributing data and query load across multiple nodes. Ideally, if there are 10 nodes, the system should handle 10x the data and be 10x faster in read and write operations.

### Partitioning Challenges
- **Skewed Partitioning:** Some nodes have more data or traffic than others, leading to inefficiencies.
- **Hot Spots:** A partition that receives a disproportionately high load.

### Partitioning Methods
- **Key-Value Partitioning**
- **Range-Based Partitioning:** Efficient for sorting but prone to hot spots. Can be mitigated by dynamically splitting partitions.
- **Hash-Based Partitioning:** Uses a hash function to distribute data more evenly, though it destroys order. Fixed partitions can be assigned in advance and rebalanced as needed.

### Re-Balancing
Re-balancing involves moving partitions between nodes to evenly distribute load. Avoid using a "hash mod N" algorithm, as it requires rehashing all data when N changes.

### Request Routing
As partitions are rebalanced, clients must determine which node to connect to for a given key. Many distributed systems use a separate coordination service to manage this.

---

## Chapter Seven: Transactions

### ACID Properties
1. **Atomicity:** Operations are fully completed or not executed at all.
2. **Consistency:** Queries return the same data each time.
3. **Isolation:** Concurrent transactions do not interfere.
4. **Durability:** Once committed, data is not lost even after failures.

### Concurrency Issues
- **Dirty Reads:** A client reads uncommitted writes from another client.
- **Dirty Writes:** One client overwrites another’s uncommitted changes.
- **Read Skew:** Different parts of the database appear at different times.
- **Write Skew:** A transaction makes decisions based on outdated values.
- **Phantom Reads:** Queries return different results within a single transaction.

### Isolation Levels
- **Snapshot Isolation:** Readers and writers do not block each other.
- **Serializable Isolation:** Ensures the result is equivalent to executing transactions in a strict sequence.

### Serialization Methods
- **Actual Serial Execution:** Transactions are executed in strict order.
- **Two-Phase Locking:** Historically used but causes performance bottlenecks.
- **Serializable Snapshot Isolation (SSI):** Transactions proceed without blocking but may be aborted if conflicts arise.

---

## Chapter Eight: Consistency & Consensus

### Eventual Consistency
If writes stop, all reads will eventually reflect the last committed value.

### Linearizability
Ensures a system appears as if there is only a single copy of data that reflects the latest writes.

### CAP Theorem
A system can only guarantee two of:
- **Consistency**
- **Availability**
- **Partition Tolerance**

### Consensus Algorithms
- **Multi-Leader Replication (Non-Linearizable)**
- **Atomic Commit (Two-Phase Commit)**
- **Lamport Timestamps:** Used to track event ordering.

---

## Chapter Ten: Batch Processing

### Batch vs. Stream Processing
- **Batch Processing:** Processes large amounts of data at once (e.g., MapReduce).
- **Stream Processing:** Processes events in near real-time.

### MapReduce
- Provides an "all or nothing" guarantee.
- Outputs data into immutable directories for later analysis.

### Fault Tolerance
- Batch jobs can be restarted upon failure due to immutable inputs.

---

## Chapter Eleven: Stream Processing

### Event Streams
Data is incrementally processed as events occur.

### Message Brokers
- Optimized for handling streams.
- Producers write messages, consumers process them asynchronously.
- Supports **load balancing** and **acknowledgments** for reliable processing.

### Microbatching
Breaks streams into small, manageable blocks for efficient processing.

### Lambda Architecture
Combines batch processing (e.g., Hadoop) and stream processing for real-time analytics.

---

## Chapter Twelve: The Future of Data Systems

### Data as Pollution
Data is the pollution problem of the information age. Just as past generations dealt with industrial waste, future generations will judge how we manage data collection and privacy.
Try replacing the word "data" with surveillance and observe if common phrases still sound good, "our surveillance sciensts are learning from your actions". 

---

## References
- [Distributed Systems Design](https://en.wikipedia.org/wiki/Distributed_computing)
- [ACID Transactions](https://en.wikipedia.org/wiki/ACID)
- [MapReduce](https://en.wikipedia.org/wiki/MapReduce)
