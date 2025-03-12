# System Design Concepts

## Key Steps in System Design
1. **Ask Requirements Clarifications**
2. **Estimate System Scale**
   - Consider scaling, partitioning, load balancing, and caching
   - Estimate storage requirements (e.g., media-heavy applications like CMS)
3. **Define System Interface**
   - Example APIs: `postTweet`, `generateTimeline`, `markFavoriteTweet`
4. **Define Data Model**
   - Identify system entities, interactions, and data management aspects (e.g., database choice)
5. **High-Level Design**
6. **Detailed Design**
7. **Identify and Resolve Bottlenecks**

## Database Considerations
- For **billions of rows**, consider NoSQL solutions like **DynamoDB**, **Cassandra**, or **RISK** (avoids complex relationships).

## Server Connectivity
- **HTTP Long Polling** or **WebSockets** for maintaining persistent connections.

## Facebook's Database Strategy
- Facebook uses **HBase**, a column-oriented key-value NoSQL database modeled after Google's **BigTable** and runs on top of HDFS.
- HBase efficiently stores variable-sized data by buffering data in memory before writing to disk.

## Load Balancing
Load balancing distributes incoming traffic across multiple servers to improve:
- **Responsiveness**
- **Availability**
- **Reduces Downtime**

### Load Balancing Algorithms
- **Least Response Time Method**
- **Least Bandwidth Method**
- **Round Robin Method**
- **Weighted Round Robin Method**

### Redundant Load Balancers
- To prevent a load balancer from becoming a **single point of failure**, use a second load balancer for redundancy. Both monitor each other's health and share traffic distribution duties.

## CAP Theorem
Distributed systems can guarantee at most two of these three properties:
- **Consistency**: All nodes see the same data.
- **Availability**: Every request receives a success or failure response.
- **Partition Tolerance**: System continues to function despite network partitions.

## Caching (Locality of Reference Principle)
- Frequently requested data is likely to be requested again.
- Cache offers short-term memory for improved response time.

## Data Partitioning
- Break large databases into smaller parts for:
  - **Manageability**
  - **Performance**
  - **Availability**
  - **Load Balancing**

### Partitioning Strategies
- **Horizontal Partitioning**
- **Vertical Partitioning**
- **Directory-Based Partitioning**

## Indexing
- Indexes speed up data retrieval but can slow down data insertion and updates.
- Performance degradation occurs when many insert, update, and delete operations are active.

## Proxies
- A proxy server acts as an intermediary between clients and backend servers.
- Functions include:
  - **Filtering requests**
  - **Logging requests**
  - **Modifying headers**
  - **Encrypting/decrypting data**

## Redundancy and Replication
- **Redundancy**: Duplicates components to ensure reliability.
- **Replication**: Ensures duplicated data is synchronized and useful for fault tolerance.

## SQL vs. NoSQL
### SQL (Relational Database)
- **Predefined Schema**
- **Upfront structure preparation**
- **Vertically scalable** (increasing RAM, CPU, etc.)
- **Best for complex queries**

### NoSQL (Non-Relational Database)
- **Dynamic Schema**
- Best suited for:
  - **Big Data**
  - **Hierarchical Data**
- **Horizontally scalable** (adding more machines)

### Common Database Types
- **Key-Value Store** (e.g., Dynamo, Redis)
- **Document Databases** (e.g., MongoDB)
- **Wide Column Databases** (e.g., HBase)

## Schema Design
- Defines how data is organized.
- Performance is context-dependent.

## Consistent Hashing
- Distributed hashing that efficiently maps data across nodes in a **hash ring** structure.

