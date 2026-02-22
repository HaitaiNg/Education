# Latency : Reduce Delay In Software Systems

**Caching** 

Caching is a technique for speeding up data retrieval by having a temporary copy of the data closer to where the data is accessed. With caching, you have a backing store, such as a database that contains the primary copy of the data, which you cache to one or more locations to speed up data access. 

You should consider caching for reducing latency when your application or system:

- does not need transactions or complex queries
- cannot be changed, which makes using techniques such as replication difficult
- has compute or storage constraints that prevent you from using other techniques

In most circumstances, a simple key-value interface is sufficient (e.g user data such as profiles and settings as a key-value pair. The key is the user ID and the value is the user data in JSON). *Cache persistence* determines what happens to cached data when the system restarts or fails. In-memory caches are typically non-persistent so the cached data is lost when the application restarts, requiring the cache to be rebuilt from scratch. 

**Redis can persist cache data to disk, allowing the cache to survive restarts and failures**. Persistent caches provide better availability and eliminate cold-start penalties, but they introduce additional complexity and potential issues between the cache the underlying data store. 

A cache can be transactional or non-transactional, depending on the caching strategy. A *transactional cache* maintains isolation guarantees, similar to database transactions. A *non-transactional cache,* there is no isolation and applications can, therefore see stale cached data. 

*Cache hit, cache hit to miss ratio (how often the application finds a relevant value in the cache and how frequently the cache does not have a value).* 

Caching strategies

- Cache aside. Cache hit, data access latency is dominated by communication latency, put the cache close to the cache server or even in your application memory space. If there is a cache miss, the cache is a passive stored updated by the application. The cache just reports a miss, and then the application is responsible for fetching data from the backing store and updating the cache.

![Screenshot 2026-01-02 at 10.58.20.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_10.58.20.png)

Cache aside is popular because it is easy to set up a cache server such as Redis, and use it to cache database queries and service responses. The cache server is passive and does not need to know what database is being used or how the results are mapped to the cache. *Cache aside caching is simple and an effective way to reduce application latency. You can hide database access latency by having the most relevant information in a cache server close to your application. Problems: data consistency or freshness requirements. If multiple concurrent leaders are looking up a key in  cache, you need to coordinate in your application how you handle concurrent cache misses. You lose transaction support; significant tail latency because some cache lookups experience the database read latency on a cache miss.* 

Read through caching: 

![Screenshot 2026-01-02 at 11.38.54.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_11.38.54.png)

Write through caching: strategy where an update to the cache propagates immediately to the backing store. Whenever a cache is updated, the cache synchronously updates the backing store with the cached value. The write latency of write-through caching is dominated by the write latency to the backing store, which can be significant. 

![Screenshot 2026-01-02 at 11.40.05.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_11.40.05.png)

Write behind caching: update the cache immediately. The cache may accept multiple updates before updating the backing store 

The write latency of a write-behind service cache is lower than with write-through. 

The downside of write-behind caching is that you lose transaction support because the cache can no longer guarantee that the cache and the database are in sync. Furthermore, write-behind
caching can reduce durability, which is the guarantee that you don’t lose data, because if the cache crashes before flushing the updates to the backing store, you can lose the updates.

![Screenshot 2026-01-02 at 11.47.02.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_11.47.02.png)

Client-side caching means the cache is at the client layer within your application.
Although cache servers such as Redis use in-memory caching, the application must
communicate over the network to access the cache via the Redis protocol. If the application is a service running in a data center, a cache server is excellent for caching because the network round trip within a data center is fast, and the cache complexity is in the cache itself. However, the last-mile latency can still be a significant factor in the user experience on a device, which is why client-side caching is so lucrative. Instead of using a cache server, you have the cache in your application.
With client-side caching, a combination of read-through and write-behind caching
is optimal from a latency point of view because both the reads and writes are fast. Of
course, your client usually won’t be able to connect with the database directly but will
instead access the database indirectly via a proxy or an API server. Client-side caching
also makes transactions hard to guarantee because of the database access indirection
layers and latency.

For many applications that need low-latency client-side caching, the local-first
approach to replication may be more practical. But for simple read caching, client-side
caching can be a good solution for achieving low latency. Of course, client-side caching
also has a tradeoff: it can increase the memory consumption of the application because
you need space for the cache.

Cache coherence refers to the mechanisms that ensure multiple caches have a consistent view of the data that is cached. In multi-core systems, each CPU core has its local cache to speed up its memory access. Most CPUs today are cache-coherent which means that when a program writes to memory, the CPU cores use a cache coherent protocol to ensure that data is up to data across all caches. In other words, if two or more CPUs have data cached from the same memory region, a cache-coherent system ensures that when a write happens, every cache is updated or invalidated so programs never reach stale data. 

**When you are optimizing for low latency with a
cache, maximizing the cache hit ratio is crucial because primary storage access typically has a large latency penalty that you are trying to avoid with caching.** 

For example, you may be caching data in a key–value store that is both close to the application and also has fast lookup times. In contrast, the primary storage may be a database run-
ning in a cloud server, which can be far away and slow to acces. 

Overall, when optimizing for low latency, try to drive your cache hit ratio as high as possible
based on your application’s working set.

Caching reduces access latency by providing a temporary copy of data that is faster to
access than the primary storage. However, the faster the access latency is, the more
expensive the storage typically is. For example, DRAM access latency is 100–1000x lower
than NVMe or SSD access latency. However, even high-end machine configurations typically have only a few hundred gigabytes of DRAM but have terabytes of storage. More
importantly, the price per gigabyte for DRAM is ten times higher than that of SSDs. 

As a result, you can only cache some of your dataset and must set some capacity limits on your cache. As long as there is space in the cache, you can keep adding data on
a miss. However, when the cache is fully occupied, you must evict some elements from
the cache on a miss. The cache replacement policy determines what you throw out from the
cache. The replacement decision is typically based on how long the element has been in
the cache or on the access frequency of a component. Choosing the right cache replace-
ment policy is critical for maintaining a high cache hit ratio and, therefore, low-latency
access, but the decision can be workload-specific.

LEAST RECENTLY USED: evicts data elements from the cache based on them not being used recently. LRU caching can maintain a high hit ratio for workfloads with high temporal locality. If recently accessed data in the cache is likely to be accessed shortly, LRU will ensure that relevant data stays in the cache. 

LEAST FREQUENTLY USED: evicts data elements based on how frequently they are used. This is ideal for content delivery network workflows where popular assets are always in the cache over time. 

FIFO

SIEVE : new cache replacement strategy 

![Screenshot 2026-01-02 at 12.00.34.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_12.00.34.png)

A materialized view is an object in a database that stores the results of a query. You cannot write to a materialized view. Rather, when you update the base table, the materialized view is updated to reflect the changes. The benefits are similar to a cache, except the database management system manages them. 

Memoization: caching technique used to improve the performance of a program by storing the result of an expensive function call and reusing those results when the same inputs occur again. With memoization, you create a cache of function outputs with function input arguments as the cache key. When you call a memoized function, it first checks if the cache has a result for a given input. If there is one, the function returns the cached result without executing the full function again. *Memoization reduces redundant computations but at the trade off of increased space complexity to keep a copy of the function results. When you cache REST API results in the client, you apply the memoization technique to reduce latency.* 

Caching is a technique for speeding up data retrieval by storing temporary copies
of data closer to where it’s accessed, similar to colocation or replication but with
different tradeoffs. The primary goal of caching is to reduce the latency and com-
plexity involved in data access.

Different caching strategies—cache-aside, read-through, write-through, and
write-behind caching—vary in how the cache integrates with the backing store.
The various strategies have different tradeoffs between latency and complexity
because of how they deal with reading and writing.
In many applications, you end up using distributed caching, meaning multiple
copies of the cache exist. This indicates that you need to deal with cache coher-
ency in the cache unless your application can cope with cache incoherency.
Maximizing the hit ratio of your cache is the key to low-latency access. Different
cache replacement policies, such as LRU, LFU, and FIFO, mandate that entries
are evicted from a cache when it’s fully occupied, impacting the hit ratio.
Time-to-live (TTL) is a way to deal with data freshness in a cache by specifying
how long an entry can be considered valid in a cache before it has to be retrieved
again from the backing store. However, TTL is often tricky to get right and can
result in a low cache hit ratio if the TTL is too low, or in an application reading
stale data if the TTL is too high.
Materialized views are a technique to speed up database queries by building a
copy of the data, organized in views, which can be queried like database tables
when constructed.
Memoization is a technique for caching the results of computations. Although
the term comes from dynamic programming, an everyday use of memoization
is to cache the results of REST API calls, which often combine computation and
data access.

**Exploiting Concurrency** 

Concurrency is the ability for a system to execute multiple tasks at the same time - a critical capability for low-latency systems that must handle long running operations or I/O bound tasks without blocking. Well designed concurrent systems can maintain responsiveness by switching between multiple tasks. Concurrent execution means you can interrupt a long-running task at a regular interval to allow other, shorter tasks to run.

Parallelism is the ability of the system to execute multiple tasks simultaneously. 

![Screenshot 2026-01-02 at 12.41.39.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_12.41.39.png)

Little’s Law : C = TL 

C = throughput

T = time 

L = latency 

L = C / T means the average latency equals concurrency divide by throughput time. 

As concurrency increases, if throughput T remains constants, latency decreases. 

Latency usually increases because of resource contention: the more concurrent requests we have, the longer each process waits because they are competing for limited resources such as CPU, memory, and I/O bandwidth. 

e.g If an API server request processing takes 20ms, and receives 1000 requests per second. That means 1000 / 20 = 50 concurrent requests without increasing latency significantly. 

Operations such as Database queries; remote procedure calls; file system operations; network responses introduce potential waiting times that could impact latency. If such a server processed requests sequentially, it would experience higher per-request latency and lower throughput because each request would need to wait for all previous requests to complete. A key insight is that while one request is waiting for a database query to complete, the server could be processing another request that is ready to execute, which maximizes resource utilization. 

You can achieve concurrency by using *task switching or parallel execution.*

In a single core system, one thread runs at a time, but the Operating System switches between threads every few milliseconds, allowing all the tasks to make forward progress. The context switching creates the illusion of parallel execution, even though only one task is running at any given moment. The OS’s schedules ensures a fair share of CPU time among all concurrent tasks, thus making it appear as if multiple operations are progressing simultaneously. 

Multicore systems can run up to ten threads in parallel, each on a different core. Modern processors have hyperthreading, which allows each physical core to run multiple threads concurrently. GPUs can run thousands of threads in parallel. 

A thread represents an independent stream of execution within a process; it maintains its own stack while sharing memory and other resources with other threads in the same process. 

![Screenshot 2026-01-02 at 13.33.09.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_13.33.09.png)

![Screenshot 2026-01-02 at 13.33.22.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_13.33.22.png)

Thread context switching involves these steps: 

1 Kernel mode transition—The CPU switches from user to kernel mode, which has
more privileges for hardware access.
2 Saving the current thread context—The OS saves the context of the currently running thread, including the program counter, any registers that hold temporary data and state, and the stack pointer.
3 Scheduler invocation—The OS invokes the thread scheduler, which decides which
thread will run next based on scheduling policies (e.g., priority, fairness) and the
state (ready, blocked) of available threads.
4 Restoring thread context—After selecting a thread to run, the OS restores its context.
5 User mode transition—Finally, the CPU switches to user mode for the newly sched-uled thread, allowing it to continue execution from where it left off.

Context switching overhead can dominate execution time when managing many threads. *High performance low latency systems often benefit from using kernel threads primarily for parallel execution while handling concurrency at the user space level (fibers) or in hardware (GPUs).* 

FIBERS are threads managed entirely within the user space. Fibers run the same memory space as the process and therefore other fibers) but they have their own stack, allowing independent execution. They rely on cooperative multitasking, which means it runs until it decides to yield its execution to other fibers. AS fibers run in user space, the context-switch cost is much smaller than that of kernel threads because you don’t need kernel crossing. 

Coroutines are a concurrency primitive that allows async programming in a manageable and efficient way. With coroutines, you can write functions that can suspend and resume code execution without blocking a thread, which is great when your application has to wait for I/O to complete or has long-running computation. Coroutines are lightweight cooperating tasks that execute concurrently within a single thread. They are managed entirely by the programming language or libraries, allowing for the creation of many of them without the overhead associated with threads. *Coroutines are resumable functions. Whereas a regular function performs some work and returns control flow to the caller via the return keyword, a coroutine can also yield control to the caller in a way that allows the caller to resume execution of the coroutine where it yielded.* 

Event driven concurrency model manages concurrent operations by responding to events as they occur rather than dedicating specific threads to specific tasks. In this model, a single thread can efficiently handle multiple concurrent operations by switching between them as events trigger state changes. (e.g a web server handling thousands of simultaneous connections: rather than allocating a thread per request, the server listens for events such as incoming client requests, completed database queries or available network buffers. When an event occurs, the server executes the corresponding handler code non-blockingly. This workflow has an event loop that continously performs two essential functions.

1. monitor multiple event sources (like network connections, file descriptors and timers). 
2. dispatch detected events to their appropriate handlrs. 

```cpp
loop {
	let events = wait_for_events(); 
	for event in events{
		handle_event(events); 
	}
}
```

![Screenshot 2026-01-02 at 15.22.12.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_15.22.12.png)

In Rust, futures and promises are a core part of the language, and you can use them with the `async` and `await` keywords. Can you clarify what a future and promise? When would I use these? 

A future is a promise of a value that the system will provide later, and to do that you often have to allocate the future to the heap, increasing hte number of small memory allocations. Future-promise systems suitable for low latency programs usually implement custom memory allocators to reduce the overhead associated with memory management. 

ACTOR MODEL: asynchronous messaging (e.g used in e-commerce systems, real-time gaming). The Actix is a popular actor framework for Rust. 

Parallel Processing
Data Parallelism: execute identical operations simultaneously across different portions of data. Effective data parallelism relies heavily on thoughtful data partitioning. Data should be organized into independent, similarly sized chunks that can be processed concurrently with minimal interdependencies. This approach is known as locality-aware partitioning, which ensures balanced workload distribution across processing units while minimizing communication overhead. For example, dividing a large matrix into contiguous blocks rather than scattered elements allow each processing unit to work efficiently on its assigned portion. 

![Screenshot 2026-01-02 at 15.31.51.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_15.31.51.png)

The degree of data parallelism directly impacts processing time and latency reduction. As you increase the number of parallel processing units, you can divide the data into smaller segments, allowing more operations to coincide (e.g sum up an array of 1000 elements would take 1000 operations if completed sequentially; however it would take 500 instructions with two processing units or 250 instructions with four processing units, etc. 

GPUS allows you to execute thousands of floating point operations per clock cycle because of how the highly parallel architecture is designed. 

Task parallelism: different tasks run on multiple processing units in parallel. Data parallelism is when the same operation runs on different ses of data in parallel. Task parallelism divides a program into independent computational tasks that execute in parallel. 

ACID principles

ATOMICITY: ensures that a transaction executes in full or not at all.

CONSISTENCY: transaction preserves database invariants. A transaction preserves database invariants. 

ISOLATION: concurrent transactions do not interfere with each other and that each transaction sees a consistent view of the database. ISOLATION IS THE ACID TERM thats related to consistency in distributed systems. 

DURABILITY: once a transaction commits, its effects will persist, even in a system failure. 

LINEARIZIBILITY: ensures real time ordering of individual operations. 

SERIALIZABILITY focuses on transaction ordering. 

The simplest way to achieve serializability is to execute transactions sequentailly. However this severely limits throughput and increases latency. 

![Screenshot 2026-01-02 at 15.50.08.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_15.50.08.png)

Two phase locking is a classic approach to concurrency control in database systems. 2PL assumes conflicts between transactions are likely and proactively prevents them by acquiring locks before accessing data items. This protocol divides each transaction’s execution into two distinct phases.

- growing phase: the transaction acquires all the required locks to perform the transaction. To prevent deadlocks, no locks are released, even if data is no longer accessed.
- shrinking phase: the transaction releases all its acquired locks. This phase prevents the transaction from acquiring new locks.

The strict separation between the lock acquisition and release phases ensures serializibility across transactions but also requires transactins that access the same data to wait. 

MVCC 

![Screenshot 2026-01-02 at 15.58.44.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-02_at_15.58.44.png)

TOKIO: an asynchronous runtime framework for rust. 

When you hit limits of what you can do, concurrent execution can help. 

¡ Concurrency models enable multiple tasks to execute simultaneously to reduce
latency, though true parallelism specifically refers to performing tasks simulta-
neously across multiple processors—both approaches require different design
considerations for optimal performance.
¡ Threading provides a widely used concurrency model but comes with kernel-level
context-switching overhead. Coroutines offer lightweight user-space managed
concurrency through suspendable execution. And event-driven architectures
handle concurrency through event loops and non-blocking operations.
¡ Parallel execution can reduce your application latency compared to sequential
execution because you can potentially complete more work in less time. Data
parallelism improves performance by executing identical operations simultane-
ously across different data portions (which is especially effective with SIMD and
GPUs). Task parallelism runs different independent tasks simultaneously. These
complementary approaches can be combined for maximum performance gains.
¡ Transaction isolation levels represent different tradeoffs between consistency
guarantees and performance, from read uncommitted to serializable, with snap-
shot isolation providing a practical middle ground through multiversion concur-
rency control (MVCC).
¡ Concurrency control mechanisms like MVCC allow databases to execute transac-
tions concurrently while maintaining consistency, helping reduce latency com-
pared to serial execution while preserving critical correctness guarantees for
applications.

## Mental model: “What is the bottleneck?”

| Bottleneck | Best choice |
| --- | --- |
| Compute-bound | Wider vectors help |
| Memory-bound | Vector width doesn’t matter much |
| Branch-heavy | Narrower vectors often better |
| Power / thermal | Smaller vectors |
| Portability | AVX2 or even SSE |

---

## Real-world rule of thumb

- **AVX2 (256-bit)** is often the *sweet spot*
- **AVX-512** only wins when:
    - Work is very regular
    - Data fits in cache
    - You’ve measured it

> “Peak SIMD width ≠ peak performance”
> 

**Asynchronous Processing** 

asynchronous processing : allows your system to initiate tasks without waiting for their results. By performing I/O operations and deferring noncritical work, you can significantly improve perceived responsiveness because end users don’t perceive the latency. Asynchronous procesing hides latency by allowing the system to remain responsive even when some operations take time. 

Asynchronous processing enables tasks to execute independently and in overlapping periods. 
Synchronous processing requires tasks to execute in sequential order. 

![Screenshot 2026-01-03 at 16.06.03.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-03_at_16.06.03.png)

![Screenshot 2026-01-03 at 16.06.41.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-03_at_16.06.41.png)

The event loop is the central coordinator for all input and output operations — its at the heart of an asychronous system. Traditional synchronous programs handle one connection at a time — like a single worker processing tasks in sequence — an event loop operates as a dispatcher, simultaneously managing thousands of I/O operations. The event loop does the following:

- poll for events
- process events
- run scheduled tasks
- repeat

Asynchronous processing is more complex than synchronous code. You need to carefully manage task dependencies, handle errors across multiple operations and deal with race conditions. 

Strategies to consider:

- I/O Multiplexing
- Request batching
- Request hedging
    
    ![Screenshot 2026-01-03 at 16.13.41.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-03_at_16.13.41.png)
    

Request hedging can hide latency spikes because it uses the first response returned from the third party application / server. 

Request hedging requires that all operations must be idempotent (e.g reading a user’s profile is idempotent; processing a payment is not). 

- Buffered I/O
- Memory Mapping
- Deferring work to a later time.
- Task scheduling (immediate execution vs deferred execution).
    - A social media platform might immediately show a person’s post to their followers, but defer updating engagement metrics or generating analytics until a later time.
    - An e-commerce system could confirm orders immediately, but defer inventory reconciliation and other tasks to off-peak hours.
- Priority queues
- Work stealing: dynamically balance the load across processing units by allowing idle workers to take tasks from busy ones.
- Thread pools: a set of threads that have been pre-initialized and that you maintain for task execution. When you have a new task, you borrow a thread from the thread pool instead of creating a new one, which hides the thread creation and teardown latency and allows you to perform work with the thread. The challenge is determining the appropriate size for the thread pool.
- Memory Pools : instead of allocating and deallocating memory to perform some work, you borrow memory from a memory pool. The challenge is how large of a memory pool do you need.
- Connection pools: maintain a st of pre-established connections that the application can re-use.

Backpressure is essential for managing con-
currency to maintain a stable system with predictable performance. Backpressure is a
flow-control mechanism that regulates data flow between producers (for example, a
client) and consumers (a server or a service) in async systems. When a consumer can-
not keep pace with incoming data, backpressure signals the producer to slow down or
pause transmission. This feedback loop ensures clients don’t send more requests than
the server can handle.

![Screenshot 2026-01-03 at 16.31.31.png](Latency%20Reduce%20Delay%20In%20Software%20Systems/Screenshot_2026-01-03_at_16.31.31.png)

- Buffering.
- Dropping and rate limiting.

¡ In synchronous processing, tasks run one after another, waiting for a task to
complete before starting another one. In contrast, asynchronous processing is
primarily about structuring your application in a way where tasks can start inde-
pendently, addressing the issue of some tasks taking a long time to complete.
¡ The event loop is a fundamental concept in asynchronous processing, where we
have a dispatcher at the core of the system that polls for events such as data arriv-
ing from the network and reacts to them.
¡ Although asynchronous processing can improve performance and reduce
latency, it has some downsides too, with resource management and error han-
dling often being more complex.
¡ I/O multiplexing is a fundamental OS primitive enabling the event-loop
approach. It allows the event loop to efficiently monitor thousands of event
sources, enabling the application to react to events as they happen.
¡ Asynchronous processing enables various efficient latency-hiding techniques
such as request hedging, deferred work, and more.
¡ Managing concurrency with backpressure is critical in asynchronous systems to
avoid overwhelming the system.
¡ Asynchronous processing requires special attention to error handling. For exam-
ple, handling partial failures and recovering from them can be tricky. Timeouts
and cancellations are also essential to dealing with asynchronous task errors.