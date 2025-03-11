# Competitive Programming in C++

## Algorithm Analysis
- **Pick the simplest algorithm** that works efficiently enough to:
  - **Pass the time limit**
  - **Stay within memory constraints**
  - **Produce the correct output**  

### Key Considerations
- Analyze the algorithm’s complexity based on:
  - **Input bounds**
  - **Time and memory limits**  
- Use this analysis to decide whether to:
  - Implement your initial algorithm  
  - Optimize it further  
  - Switch to another problem in the set  

---

## Recursive Algorithms
- For recursive problems with **b** recursive calls per level and **L** levels:  
  - The complexity is approximately **O(b^L)**  
- The **actual complexity** may vary depending on:
  - The actions performed at each level  
  - Whether pruning (cutting off unnecessary branches) is possible  

> **Tip:** Efficient pruning strategies can drastically improve performance in recursive problems.  
