
#Warnings
#we assume that the unique key is at the end 

fileName = "mockText.txt"
delimiter = "|" 
setRowDataString = "Test Data"
newSetRowDataString = "Scenario" 
uniqueKey = "unique_key" 
logicalName = "mockTable" 

file = open(fileName, 'r') 
fileContent = file.readlines()
parsedFileContent = [] 

for line in fileContent: 
	line = line.split(delimiter)
	line = [listObject.strip() for listObject in line][1:] 
	if(setRowDataString in line):
		line.pop(0) 
	parsedFileContent.append(line) 

output = [] 
for line in parsedFileContent:
	if(uniqueKey in line):
		uniqueKeyIndex = line.index(uniqueKey) 
		uniqueKeyValueIndex = uniqueKeyIndex + 2 
		newList = []
		newList.append("") 
		newList.append(newSetRowDataString) 
		newList.append( line[uniqueKeyIndex]) 
		newList.append( "as") 
		newList.append( line[uniqueKeyValueIndex])
		newList.append( "and") 		
		line = line[:uniqueKeyIndex - 1]
		for entry in line:
			newList.append(entry)
		newList.append("")
		output.append(newList)
		
	else:
		line = [""] + line 
		output.append(line)


for j in output:
	j = (delimiter).join(j)
	print(j)
