import re
import sys 

file_name = sys.argv[1] 
character_limit_per_line = 50 

file = open(file_name, "r") 
new_list_of_contents = [] 
for line in file.readlines(): 
    if len(line) > character_limit_per_line:
        wl = line.split() 
        tmp = "" 
        while len(wl) > 0: 
            if len(tmp) > character_limit_per_line: 
                tmp +=  "\n"
                new_list_of_contents.append(tmp)
                tmp = "" 
            else: 
                tmp += wl.pop(0) + " " 
    else:
        new_list_of_contents.append(line) 

output = open(file_name, "w") 
for j in new_list_of_contents:
    output.write(j) 

file_name = str(file_name)
print("File Refactored")
print(file_name)
