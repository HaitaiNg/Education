targetFile = open("Biographies.txt", "r")
newListOfContents = [] 
for sentence in targetFile.readlines(): 
    if(len(sentence) > 50):
        wordList = sentence.split() 
        temporarySentence = "" 
        while(len(wordList) > 0): 
            if(len(temporarySentence) > 50):
                temporarySentence += "\n"
                newListOfContents.append(temporarySentence)
                temporarySentence = "" 
            else:
                temporarySentence += wordList.pop(0) + " "
        if(len(temporarySentence) != 0):
            newListOfContents.append(temporarySentence + "\n") 
    else:
        newListOfContents.append(sentence) 

writeFile = open("Biographies.txt", "w") 
for i in newListOfContents:
    writeFile.write(i)