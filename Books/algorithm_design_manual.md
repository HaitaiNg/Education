# Algorithm Design Manual

## Complexity Hierarchy
- **n!** ≫ **cⁿ** ≫ **n³** ≫ **n²** ≫ **n¹⁺ε** ≫ **n log n** ≫ **n** ≫ **√n** ≫  
  **log²n** ≫ **log n** ≫ **log n / log log n** ≫ **log log n** ≫ **α(n)** ≫ **1**

---

## Problem-Solving Questions
When approaching algorithmic problems, consider these questions:

### Understanding the Problem
- **Do I understand the problem?**  
- **What does the input consist of?**  
- **What are the desired results or output?**  
- **Can I construct a small example solvable by hand?**  

### Efficiency and Optimization
- **How important is speed for this application?**  
- **Am I solving a:**  
  - Numerical problem?  
  - Graph algorithm?  
  - Geometric problem?  
  - String problem?  
  - Set problem?  

### Problem Solving Techniques
- **Brute Force:**  
  - Will brute force solve my problem correctly by searching through all subsets or arrangements?  
- **Divide and Conquer:**  
  - Can I split the problem into two smaller problems, perhaps using binary search?  
  - Can I partition the elements into big/small or left/right groups?  
- **Dynamic Programming:**  
  - Do the input objects or desired solution have a natural order (e.g., characters in a string, elements of a permutation, or leaves of a tree)?  
  - Can I exploit this order to apply dynamic programming efficiently?  

### Data Structures for Optimization
- **Are certain operations being done repeatedly?**  
  - Searching  
  - Finding the largest/smallest element  
- **Can I use a data structure to speed up these queries?**  
  - Dictionary / Hash Table  
  - Heap / Priority Queue  

### Randomization and Heuristics
- **Can I use random sampling to select the next object?**  
- **What about constructing multiple random configurations and selecting the best one?**  
