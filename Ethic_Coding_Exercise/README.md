# Ethic Coding Exercise
###### Refresh Date: 11.20.20 
###### Author: Haitai Ng (HaitaiNg@gmail.com) / 248-977-9325
This repository stores Haitai Ng's submission for the Ethic Coding Exercise. 

##### How to compile the source code on the command line 
Using a command line terminal, please check you are in the correct directory that contains the files: Graph.java; Ethic_Coding_Exercise.java; User_Executable.java. Once you have confirmed the three file exists please run the following commands:
```
$ javac *.java
```
##### How to run the application from the command line, along with the sample input file
After you have compiled the program by using “javac *.java” execute the following command to run the program.
```
$ java Ethic_Coding_Exercise
```

##### Input: 
The user is prompted to enter a comma separated list denoting the associations between towns and their corresponding distance (example: "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7"). This is a directed graph. For AB5 the first char A represents the start town, char B represents the destination town, and 5 represents the distance between A and B. This graph will be instantiated at the beginning of this program. We assume that no modifications will occur to the graph during the duration of the program. 

Loop Condition: After the graph has been instantiated, the program will continuously re-prompt the user to select a question. Each integer will correspond to a particular question. The program will display the following prompt to the console: As a reminder, all user input submissions do not require single or double quotes. 

```
Please select a question by choosing a number 1-5 and pressing enter: 
1.	The distance of the route _
2.	The number of trips starting at _ and ending at _ with a maximum of _ stops 
3.	The number of different routes from _ to _ with a distance of less than _
4.	The length of the shortest route (in terms of distance to travel from _ to _ 
5.	To quit the program, press 5 then enter. 
```

The user must provide a valid integer corresponding to a particular question. For inputs [1-4] they will be prompted to enter the additional arguments. For input 5, the program will be terminated.

- For question 1, the user must provide a route denoting all nodes in the route. For example: “ABC”. If the route does not exist or cannot be calculated, then the console will output “NO SUCH ROUTE” 

- For question 2, the user must provide three arguments: start node (single character) denoting the starting location; destination node (single character); maximum allocated number of stops (integer). 

- For question 3, the user must provide three arguments: start node (single character) denoting the starting locating; the destination node (single character); maximum allocated distance (integer)

- For question 4, the user must provide two arguments; start node (single character) denoting the starting location; the destination node (single character)

- ***For additional references and supporting pictures showcasing the program execution, please refer to folder "Development_Process/Standard_Sample_Execution.png"*** 

##### Output 
The console will display the corresponding output immediately after the input parameters have been provided.  
##### How to run unit tests#
<ToDo> 

##### Assumptions I made while writing the source code
- The name of start town and end town are single characters. 
- The distance between towns is a positive integer where : ( 1 <= distance <= max_integer) 
- We will not be removing any nodes in graph or modifying the graph after it has been instantiated by the input 
- Most inputs will be valid. I did not incorporate extensive error handling (exceptions, try / catch clauses) 
- Note: Unit testing is high level. I primarily wrote tests for the methods that validated the questions. These methods make several other method calls downstream. If this code were to be submitted to a production environment, I would like to include some tests for the lengthy supporting methods. 

##### Support 
Please contact Haitai Ng if there any questions or concerns. Thank you for your time. 


