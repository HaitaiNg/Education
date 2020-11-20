#Ethic Coding Exercise#

##Refresh Date: 11.19.20 ##
##Author: Haitai Ng ##

###Description###
This repository stores Haitai Ng's submission for the Ethic Coding Exercise. 

###How to compile the source code on the command line### 
Using a command line terminal, please check you are in the correct directory that contains the file "java_solution.java". Once you have confirmed the file exists please run the following commands:
```
javac java_solution.java 
java java_solution
```

###How to run the application from the command line, along with the sample input file### 
<ToDo> 

The program will prompt the user four possible questions. The user must select a question type by providing an integer corresponding to that question and pressing "enter" on their keyboard.
All inputs regarding start town and end town are not enclosed in single or double quotes. All integer inputs are expected to be ints

If the user inputs a 1. then the user will be prompted with the question : "The distance of the route ___". The user will be responsibile for providing the route.
Example command sequence: 1, (user presses enter"), ABC, (user presses enter) 

If the user inputs a 2. then the user will be prompted with the question : "The number of different routes between two towns where start _ , end _ and max number of stops is _". The user will be responsibile for providing the start town (single char), end town (single char), and an integer denoting the max number of stops. The start town (type: single char) and end town (single char) does not have single quotes or double quotes. 
Example command sequence: 1, (user presses enter"), C, (user presses enter),  E, (user presses enter), 4, (user presses enter) 

If the user inputs a 3. then the user will be prompted with the question : "The number of different routes between two towns where start _ , end _ and max distance is _". The user will be responsibile for providing the start town (single char), end town (single char), and an integer denoting the max allocated sitance. The start town (type: single char) and end town (single char) does not have single quotes or double quotes. 
Example command sequence: 1, (user presses enter"), ABC, (user presses enter) 

If the user inputs a 4. then the user will be prompted with the question : "The shortest between two towns where start _ , end _ . The user will be responsibile for providing the start town (single char), end town (single char). The start town (type: single char) and end town (single char) does not have single quotes or double quotes. 
Example command sequence: 1, (user presses enter"), ABC, (user presses enter) 

For additional references and supporting pictures showcasing the program execution, please refer to folder"Development_Process/SampleExecution.png" 

###How to run unit tests### 
<ToDo> 

###Assumptions you made while writing the source code (example: known limitations, etc.)### 
The name of start town and end town are single characters. 
The distance between towns is a positive integer where : ( 1 <= distance <= max_integer) 
We will not be removing any nodes in graph or modifying the graph after it has been instantiated by the input 



