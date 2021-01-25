targetFileName = "FlashBoys.txt"
characterLimitPerSentence = 50 

targetFile = open( targetFileName, "r")
newListOfContents = [] 
for sentence in targetFile.readlines(): 
    if(len(sentence) > characterLimitPerSentence):
        wordList = sentence.split() 
        temporarySentence = "" 
        while(len(wordList) > 0): 
            if(len(temporarySentence) > characterLimitPerSentence):
                temporarySentence += "\n"
                newListOfContents.append(temporarySentence)
                temporarySentence = "" 
            else:
                temporarySentence += wordList.pop(0) + " "
        if(len(temporarySentence) != 0):
            newListOfContents.append(temporarySentence + "\n") 
    else:
        newListOfContents.append(sentence) 

writeFile = open(targetFileName, "w") 
for i in newListOfContents:
    writeFile.write(i)
print("Text File Refactored") 
