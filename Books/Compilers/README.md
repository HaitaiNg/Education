# Compiler Book

The **Boeing 777** aircraft uses software written in **Ada**, a language designed for safety-critical systems.

A **compiler** translates a program written in a *source language* into a *target language* (for example, compiling C into native machine code).

An **interpreter** reads and executes a program directly without producing a separate translated output. This execution model is sometimes referred to as a **virtual machine**. Languages such as **Python** and **Ruby** are typically executed by an interpreter rather than compiled directly to native machine code.

**Java** source code is compiled into **Java bytecode**, an intermediate, platform-independent form of assembly language that is executed by the Java Virtual Machine (JVM).

> The best way to learn about compilers is to build one from start to finish.
> 

**Java** is simple, consistent, and portable, though generally not as performant as native languages.

**Python** is easy to learn and has excellent library support, but it is **dynamically (weakly) typed** and typically slower than compiled languages.

**Rust** provides strong static type safety and memory safety guarantees, though it is not yet as widely adopted as some older languages.

The **x86** computer architecture is widely used in desktops, laptops, and servers. Architectures such as **MIPS**, **SPARC**, and **ARM** are generally simpler in design compared to x86.

![Screenshot 2025-09-27 at 16.58.14.png](Compiler%20Book/Screenshot_2025-09-27_at_16.58.14.png)

The preprocessor prepares the source code for the compiler proper. In the C and C++ language, this means consuming all derivatives that start with the # symbol. 

The compiler scans and parses the source code, performs type checking, and other semantic routines, optimizes the code, and then produces the assembly language as the output. 

The assembler consumes the assembly code and produces object code. Object code is “almost executable” in that it contains raw machine language instructions in the form needed by the CPU. Object code does not know the final memory addresses in which it will be loaded, and so it contains gaps that must be filled in by the linker. 

The linker consumes one or more object files and library files and combines them into a complete, executable program. It selects the final memory locations where each piece of code and data will be loaded, and then “links” them together by writing in the missing address information. 

![Screenshot 2025-09-27 at 17.01.27.png](Compiler%20Book/Screenshot_2025-09-27_at_17.01.27.png)

CFG (context free grammar) is the form of a valid sentences in a programming language. They are stronger than regular expressions because they can express a richer set of structures. There are two types of CFGs known as LL(1) and LR(1) grammars. 

LL(1) grammars are CFGs that can be evaluated by considering only the current rule and next token in the input stream. 

LR(1) grammars are more general and more powerful than LL(1). They are harder to write by hand, and you usually use a parser generator to handle and interpret these sentences. 

A **terminal** is a discrete symbol that can appear in the language (e.g keywords, operators, and identifiers). 

A **non-terminal** represents a structure, but not a literal symbol (e.g declarations, statements, and expressions). 

P for program; S for statement; E for expression. 

A → xXy : non-terminal A represents a terminal x followed by a non-terminal X and a terminal y. 

![Screenshot 2025-11-23 at 16.19.44.png](Compiler%20Book/Screenshot_2025-11-23_at_16.19.44.png)

Another example of ambiguous: if E then if E then other else other. 

- if E then (if E then other else other)
- if E then (if E then other) else other

Recursive Descent Parsing: LL(1) grammars use simple hand-coded parsers. Simple function for each non-terminal in the grammar. The body of the function follows the right-hand sides of the corresponding rules: non-terminals result in a call to another parse function, while terminals result in considering the next token. 

- scan_token(): returns the next token on the input stream
- putback_token(t) puts an unexpected token back on the input stream, where it will be read again by the next call to scan_token.
- expect_token(t) calls can_token to retrieve the next token. If returns true if the token matches the expected type. If not, it puts the token back on the input stream and returns false.

This is recursive but its not an infinite recursive loop because every recursive path has a base case where no more tokens are consumed / no more recursion happens, and each recursive call makes progress by consuming input. 

- Each recursive steps consumes at least one token or it hits a branch that returns without recursing (base case).
- Each time you do recurse (`t == TOKEN_PLUS`) you’ve consumed at least one more token: the `+` and whatever `parse_T()` consumes.

```cpp
int parse_P() {
    return parse_E() && expect_token(TOKEN_EOF);
}

int parse_E() {
    return parse_T() && parse_E_prime();
}

int parse_E_prime() {
    token_t t = scan_token();
    if(t == TOKEN_PLUS) {
        return parse_T() && parse_E_prime();
    } else {
        putback_token(t);
        return 1;  // epsilon branch (no more +)
    }
}

int parse_T() {
    return parse_F() && parse_T_prime();
}

int parse_T_prime() {
    token_t t = scan_token();
    if(t == TOKEN_MULTIPLY) {
        return parse_F() && parse_T_prime();
    } else {
        putback_token(t);
        return 1;  // epsilon branch (no more *)
    }
}

int parse_F() {
    token_t t = scan_token();
    if(t == TOKEN_LPAREN) {
        return parse_E() && expect_token(TOKEN_RPAREN);
    } else if(t == TOKEN_INT) {
        return 1;
    } else {
        printf("parse error: unexpected token %s\n",
               token_string(t));
        return 0;
    }
}
```

A LL(1) parse table is used to determine which rule should be applied for any combination of non-terminal on the stack and next token on the input stream. To create a parse table, we use the FIRST and FOLLOW sets like this: 

![Screenshot 2025-11-23 at 16.47.28.png](Compiler%20Book/Screenshot_2025-11-23_at_16.47.28.png)

The idea is to keep a stack that tracks the current state of the parser. In each step we consider the top element of the stack and the next token on the input. If they match, then pop the stack, accept the token, and continue. If not, consult the parse table for the next rule to apply. 

![Screenshot 2025-11-23 at 16.49.36.png](Compiler%20Book/Screenshot_2025-11-23_at_16.49.36.png)

**Parsing** is the process of taking a sequence of tokens (produced by a lexer) and determining whether they follow the rules of a grammar - and if they do, building a structure (usually a parse tree or syntax tree) that represents the program. 

A **parse table** is a precomputed lookup table that tells the parser exactly what action to take at each step. Instead of “thinking” the parser just “looks at the table and follows instructions”. 

Think of parsing like driving:

- **Parsing algorithm** → the driving rules
- **Parse table** → the GPS instructions
- **Parser** → the driver following directions exactly

General purpose programming languages use LR(1) grammar and associated bottom-up parsing techniques. LR(1) is the set of grammars that can be parsed via shift-reduce techniques with a single token of a lookahead. LR(1) is a super-set of LL(1) and can accomodate  left recursion and common left prefixes which are not permitted in LL(1) 

+---------------------------+
|          LR(1)            |
|                           |
|    +----------------+     |
|    |     LL(1)      |     |
|    +----------------+     |
+---------------------------+

The LR() automation is known as the canonical collection or the compact finate state machine of the grammar. 

Each state in the automaton consists of multiple items, which are rules
augmented by a dot (.) that indicates the parser’s current position in that
rule. For example, the configuration E →E . + T indicates that E is currently on the stack and `+ T` is a possible next sequence of tokens. 

![Screenshot 2025-11-23 at 16.59.07.png](Compiler%20Book/Screenshot_2025-11-23_at_16.59.07.png)

If we have a problem to be solved, it should be attacked using the least expensive tool that is capable of addressing the problem. If we can solve a given problem by employing regular expressions instead of context free grammar then we should use regular expressions because they consume less state, have simpler machinery, and present fewer roadblocks to a solution. 

Assembly language is the most powerful language available in our toolbox and is capable of expressing any program that the computer is capable of executing. However, assembly language is also the most difficult to use because it gives none of the guarantees found in higher level languages. Higher level languages are less powerful than assembly language, and this is what makes them more predictable, reliable, and congenital to use. 

---

SLR for idiots: Imagine you are assembling a burger, `patty + cheese .` . 

Dot at the end means: “Should we wrap it up?”. 

However if you look ahead there may be more ingredients:

→ bun : wrap it 

→ lettuce : wrap it 

→ another patty → NO STOP, keep building (don’t reduce yet). 

FOLLOW(cheeseburger) tells you what ingredients can legally come next after a full cheeseburger. If the next thing isn’t allowed → don’t reduce. That is SLR. 

FOLLOW(A) acts like a guard. 

- if next token → FOLLOW(A) : it’s safe to reduce.
- if next token ≠ FOLLOW(A) : don’t reduce; shift instead.

A grammar is SLR if: Using follow sets is enough to remove all ambiguities in the LR(0) automation. 

SLR is stronger than LL(1); weaker than full LR(1); much simpler than LR(1); uses similar tables; easier to implement. 

**SLR parsing = LR(0) automation + FOLLOW sets deciding when reductions are allowed.** 

![Screenshot 2025-11-23 at 17.49.10.png](Compiler%20Book/Screenshot_2025-11-23_at_17.49.10.png)

if LR(0) is a skeleton, LR(1) is a skeleton + the nervous system wiring. Same bonus; more signals.

```cpp
LR(0):

“You finished a sentence. Maybe stop here?”

SLR:

“You finished a sentence. Stop only if the next word is one that 
could follow a sentence.”

LR(1):

“You finished a sentence. Stop only if the next word is one that
 would follow exactly this sentence in this specific context.” 

----
✔ LR(0)

Build states with dot positions only.

Often has shift/reduce conflicts.

✔ SLR

Same LR(0) states.

Use FOLLOW sets to restrict reductions.

Fixes some conflicts.

✔ LR(1)

Items carry lookahead sets.

Much more precise.

The automaton gets bigger (more states).

Can parse almost all deterministic grammars.
```

An **expression validator** confirms whether the code that is provided actually matches the grammar rules. In other words, is the code standards compliant (e.g HTML, CSS, JSON online validators). 

An **interpreter** reads the input program and then executes the program to produce a result. One approach is to compute the result of each operation as soon as it is parsed. Another option is to parse the program into an abstract syntax tree and then execute it. 

A **translator** reads the input program, parses it into an abstract syntax tree, and then traverses the abstract syntax tree to produce an equivalent program in a different program. 

The abstract syntax tree represents the primary structure of a program. It is the starting point for semantic analysis of a program. 

- A declaration states the name, type, and value of a symbol so that it can be used in a program (e.g constants, variables, functions).
    - b : boolean;
    - s : string = “hello”;
    - f : function integer (x : integer) = {return x + x;}

```cpp
struct decl * decl_create( char *name,
struct type *type,
struct expr *value,
struct stmt *code,
struct decl *next )
{
struct decl *d = malloc(sizeof(*d));
d->name = name;
d->type = type;
d->value = value;
d->code = code;
d->next = next;
return d;
}
```

![Screenshot 2025-12-12 at 18.26.33.png](Compiler%20Book/Screenshot_2025-12-12_at_18.26.33.png)

- A statement indicates an action to be carried out that changes the state of the program (e.g loops, conditionals, and function returns).

A programming language (and its type system) are commonly classified on the following:

- safe or unsafe
- static or dynamic
- implicit or explicit

C is considered an unsafe language, because this code could compile and run.

```cpp
/* This is C code */
int i;
int a[10];
for(i=0;i<100;i++) a[i] = i;
```

![Screenshot 2025-12-13 at 18.12.02.png](Compiler%20Book/Screenshot_2025-12-13_at_18.12.02.png)

A CPU is free to use memory in any way it sees fit. Code and data could be scattered and intermixed across memory in any order that is convenient. The CPU could even modify the memory containing the code it is running on. Program memory is laid out in logical segments. Each segment is a sequential address range, dedicated to a particular purpose within the programs. 

![Screenshot 2025-12-13 at 18.34.46.png](Compiler%20Book/Screenshot_2025-12-13_at_18.34.46.png)

The code segment (aka the text segment): contains the machine code of the program (e.g the corresponding bodies of functions in a C program). 

The data segment contains the global data of the program, corresponding to the global variables in a C program. 

The heap segment contains the heap, which is the area of memory that is managed dynamically at runtime by malloc and free in a C program or new and delete in other languages. 

The stack segment contains the stack, which records the current execution state of the program as well as the local variables currently in use. 

```cpp
The heap segment contains the heap, which is the area of memory
that is managed dynamically at runtime by malloc and free in a
C program, or new and delete in other languages. The top of the
heap is historically known as the break.

The stack segment contains the stack, which records the current ex-
ecution state of the program as well as the local variables currently
in use.
Typically, the heap grows “up” from lower addresses to higher ad-
dresses, while the stack grows “down” from higher to lower. In between
the two segments is an invalid region of memory that is unused until over-
taken by one segment or the other.

On a simple computer such as an embedded machine or microcon-
troller, logical segments are nothing more than an organizational conven-
tion: nothing stops the program from using memory improperly.
```

The OS should set permissions on particular segments in memory. These permissions on logical segments protect a process from damaging itself in certain ways. It also protects a process from requesting to much memory. If memory limits are requested but none are available, the OS will kill the program. 

`mprotect` is a **Unix/Linux system call** used to **change the memory protection of a region of virtual memory** in a process.

In simple terms: it controls **whether a block of memory can be read, written, or executed**.

```cpp
The idea of breaking a program into segments is so powerful and use-
ful that it was common for many decades to have the concept implemented
in hardware. (If you have taken a class in computer architecture and op-
erating systems, you have probably studied this in some detail.) The basic
idea is that the CPU maintains a table of segments, recording the starting
address and length, along with the permissions associated with each seg-
ment. The operating system would typically set up a hardware segment
to correspond to the logical organization just described.
```

```cpp
The heap contains memory that is managed dynamically at runtime. The
OS does not control the internal organization of the heap, except to limit
its total size. Instead, the internal structure of the heap is managed by the
standard library or other runtime support software that is automatically
linked into a program. In a C program, the functions malloc and free al-
locate and release memory on the heap, respectively. In C++, new and
delete have the same effect. Other languages manipulate the heap implic-
itly when objects and arrays are created and deleted.
```

Memory fragmentation: the heap can degenerate into a mix of oddly sized chunks of allocated and free memory. Excessive memory fragmentation can result in waste: if there are many small chunks available, but none of them large enough to satisfy the current `malloc` then the process has no choice but to extend the heap, leaving the small pieces unused. 

```cpp
The stack is used to record the current state of the running program. Most
CPUs have a specialized register – the stack pointer – which stores the
address where the next item will be pushed or popped. Because the stack
grows down from the top of memory, there is a confusing convention:
pushing an item on the stack causes the stack pointer to move to a lower
numbered address, while popping an item off the stack causes the stack
pointer to move to a higher address. The “top” of the stack is actually at
the lowest address!

Each invocation of a function occupies a range of memory in the stack,
known as a stack frame. The stack frame contains the parameters and
the local variables used by that function. When a function is called, a
new stack frame is pushed; when the function returns, the stack frame
is popped, and execution continues in the caller’s stack frame.
Another specialized register known as the frame pointer (or some-
times base pointer) indicates the beginning of the current frame. 

Code within a function relies upon the frame pointer to identify the location of
the current parameters and local variables.
For example, suppose that the main function calls function f, and then
f calls g. If we stop the program in the middle of executing g, the stack
would look like this:
```

![Screenshot 2025-12-13 at 18.46.36.png](Compiler%20Book/Screenshot_2025-12-13_at_18.46.36.png)

The simplest computer systems store an executable as a binary blob on disk. The program code, data, and initial state of the heap and stack are simply dumped into one file without distinction. To run the program, the OS must simply load the contents of the file into memory, and then jump to the first location of the program to begin execution. Embedded systems often have very small programs measured in a few kilobytes, and rely on binary blobs.

The a.out format is a major improvement over a binary blob. The extensible linking format (ELF) is widely used today across many operating systems to represent executables, object files, and shared libraries. 

In order to build a compiler, you must have a working knowledge of at least one kind of assembly language. 

X86 is a generic term that refers to the series of microprocessors descended
from (or compatible with) the Intel 8088 processor used in the original IBM
PC, including the 8086, 80286, ’386, ’486, and many others. 

Each generation of CPUs added new instructions and addressing modes from 8-bit to
16-bit to 32-bit, all while retaining backwards compatibility with old code. A variety of competitors (such as AMD) produced compatible chips that
implemented the same instruction set.

Assembly code has three different elements: 

- directives: contains structural information (e.g .file simply records the start of the data segment of the program; .text indicates the start of the text segment).
- label: present in the object code for the purpose of linking and in the eventual execution, for purposes of debugging.
- instructions: actual assembly code

The `MOV` instruction moves data between registers and to and from memory in a variety of different modes. A single letter determines the size of data that needs to be moved. 

![Screenshot 2025-12-14 at 12.33.56 PM.png](Compiler%20Book/Screenshot_2025-12-14_at_12.33.56_PM.png)

The stack is an auxiliary data structure used primarily to record the function call history of the program along with local variables that do not fit in registers. By convention, the stack grows *downward* from high values to low values. The sp register is known as the **stack pointer** and keeps track of the bottom-most item on the stack.

**Optimization strategies** 

**Constant folding:** technique of converting an expression by combining multiple constants into a single constants. An operator node in the tree with two constant child nodes can be converted into a single node with the result of the operation computed in advanced. The process can cascade up so that complex expressions may be reduced to a single constant. 

The result computed in advance is precisely equal to what would have been performed at runtime. This requires using variables of the same precision and dealing with boundary cases such as underflow, overflow, and division by zero. 

**Strength reduction** is the technique of converting a special case of an expensive operation into a less expensive operation. For example, the source code expression xˆy for exponentiation on floating point values is, in general, implemented as a call to the function pow(x,y), which might be implemented as an expansion of a Taylor series. However, in the particular case of xˆ2 we can substitute the expression x*x which accomplishes the same thing. This avoids the extra cost of a function call and many loop iterations. In a similar way, multiplication/division by any power of two can be replaced with a bitwise left/right shift, respectively. For example, x*8 can be replaced with x<<3.
**Loop unrolling** is the technique of transforming a loop into another that has fewer iterations, but does more work per iteration. The number of repetitions within the loop is known as the **unrolling factor**. Increasing the work per loop iteration saves some unnecessary evaluations and it also eliminates branches from instruction stream which avoids pipeline stalls and other complexities within the microprocessor. 

```jsx
Before:
for(i=0;i<400;i++) {
   a[i] = i*2 + 10;
}

Could be:

for(i=0;i<400;i+=4) {
   a[i] = i*2 + 10;
   a[i+1] = (i+1)*2 + 10;
   a[i+2] = (i+2)*2 + 10;
   a[i+3] = (i+3)*2 + 10;
}

Or this:

for(i=0;i<400;i++) {
   a[i] = i*2 + 10;
   i++;
   a[i] = i*2 + 10;
   i++;
   a[i] = i*2 + 10;
   i++;
   a[i] = i*2 + 10;
}
```

**Function inlining** is the process of substituting a function call with the effect of that function call directly in the code. This is particularly useful for brief functions that exist to improve the clarity or modularity of code, but do not perform a large amount of computation. For example, suppose
that the simple function quadratic is called from many times within a loop, like this:

```
int quadratic( int a, int b, int x ) {
  return a*x*x + b*x + 30;
```

}

```
for(i=0;i<1000;i++) {
    y = quadratic(10,20,i*2);

```

}

The overhead of setting up the parameters and invoking the function likely exceeds the cost of doing the handful of additions and multiplies within the function itself. By inlining the function code into the loop, we can improve the overall performance of the program.

Function inlining is most easily performed on a high-level representation such as an AST or a DAG. First, the body of the function must be duplicated, then the parameters of the invocation must be substituted in. Note that, at this level of evaluation, the parameters are not necessarily
constants, but may be complex expressions that contain unbound values.

For example, the invocation of quadratic above can be substituted with the expression (a*x*x+b*x+30) under the binding of a=10, b=20, and x=i*2. Once this substitution is performed, unbound variables such as i are relative to the scope where quadratic was called, not where it
was defined.

```jsx
for(i=0;i<1000;i++) {
    y = 10*(i*2)*(i*2) + 20*(i*2) + 30;
}
```

Be careful of the correctness of optimizations. A given piece of code must produce the same result before and after optimization, for all possible inputs. We must be particularly careful with the boundary conditions of a given piece of code, where inputs are particularly large, or small, or run into fundamental limitations of the machine.