# Ethic Coding Exercise
###### Refresh Date: 11.20.20
###### Author: Haitai Ng (HaitaiNg@gmail.com) / 248-977-9325
This repository stores Haitai Ng's submission for the Ethic Coding Exercise.

##### How to compile the source code on the command line
This program was developed using Java 8 (jdk1.8.0_271.jdk). Using a command line terminal, please check if you are in the correct directory: "Ethic/src/main/java/". Once you have confirmed you are in the correct directory please run the following commands:
```
$ javac *.java
```
##### How to run the application from the command line, along with the sample input file
After you have compiled the program, please execute the following command to run the program. You will be prompted to enter an absolute path to your input file.

```
$ java Ethic
```

##### Input:
```
Please provide the absolute path to your input file:
```

The user is responsible for providing the ***absolute path*** to the input test file. Once a file has been entered the results will be displayed on the console. 
Please note that the input file format is case-sensitive with file type “.txt”. The questions are tokenized, therefore the format must match the exercise description. Please refer to the /InputFiles/sample.txt or other files in /InputFiles for additional examples.
######Console Execution Example
```
Please provide the absolute path to your input file: /Users/haitai/Documents/Ethic/InputFiles/sample.txt
```


######Input File Example: /Users/haitai/Documents/Ethic/InputFiles/sample.txt
```
AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7
The distance of the route ABC.
The number of trips starting at C and ending at C with a maximum of 3 stops
The number of different routes from C to C with a distance of less than 30
The length of the shortest route (in terms of distance to travel) from A to C
```

- For additional references and supporting pictures describing the program execution, please refer to folder: "Development_Process/console_execution.png"

##### Output
The console will display the corresponding output immediately after the input file have been provided.

##### How to run unit tests
This program was developed using Java 8 (SDK 1.8.0_271), and was written using the Intellij editor. The testing framework that was used was JUnit4.
To execute tests please navigate to the “src/test/java/” directory where all unit tests can be found.

##### Assumptions I made while writing the source code
- The name of start town and end town are single characters.
- The distance between towns is a positive integer: (1 <= distance <= max_integer (2^31-1)).
- Questions will be lower case and follow the exercise description format. Currently this program does not incorporate extensive error handling (exceptions, try / catch) for invalid inputs.
- The graph will not be modified once it has been instantiated. This includes removing towns, increasing or reducing costs between towns, and adding additional towns.
- Note: The unit testing is high level. If this solution were to be submitted to a production environment, it would be ideal to write additional tests covering critical methods.

##### Support
I enjoyed this programming exercise very much. Please contact Haitai Ng if there are any questions or concerns. Thank you for your time.





