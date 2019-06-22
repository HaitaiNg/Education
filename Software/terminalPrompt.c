/**
 * Name: Haitai Ng
 */

 #include <algorithm>
 #include <cstring>
 #include <cctype>
 #include <cstring>
 #include <ctype.h>
 #include <errno.h>
 #include <fcntl.h>
 #include <fstream>
 #include <iostream>
 #include <iomanip>
 #include <iterator>
 #include <list>
 #include <sstream>
 #include <stdio.h>
 #include <stdlib.h>
 #include <string>
 #include <sys/wait.h>
 #include <sys/types.h>
 #include <sys/stat.h>
 #include <unistd.h>
 #include <time.h>
 #include <typeinfo>
 #include <vector>
using namespace std;

/**
 * Name: display_file_open_error
 * Purpose: Display a message when file cannot is invalid/ cannot be opened
 * Receive: none
 * Return: none
 */
void display_file_open_error()
{
  char buf[512];
  size_t nbytes;
  strcpy(buf, " :: Error :: Invalid Filename :: File Could Not Be Found :: \n ");
  nbytes = strlen(buf);
  write(2, buf , nbytes);
}

/**
 * Name: determine_numberOfOperations
 * Purpose: return the number of operations for a string
 * Receive: fileContentVector, letters
 * Return: number of operation occurences
 */
int determine_NumberOfOperations(vector<string> fileContentVector, std::string operationLetter )
{
  int numOperations = 0;
  for(int i = 0; i < fileContentVector.size(); i++)
  {
    if(fileContentVector[i] == operationLetter)
    {
      numOperations++;
    }
  }
  return numOperations;
}


/**
 * Name: is_digit
 * Purpose: check if a str is a valid digit
 * Receive: str character
 * Return: boolean
 */
bool is_digit(const std::string& s)
{
    return !s.empty() && std::find_if(s.begin(), s.end(), [](char c) { return !std::isdigit(c); }) == s.end();
}

int display_PageTable(vector<string> memoryReferences)
{
  std::string memoryReference = memoryReferences[0];
  if((is_digit(memoryReference)) )
  {
    int memoryReferenceValue = atoi(memoryReference.c_str());
    if(memoryReferenceValue == 0)
    {
      std::cout << "Do Not Display Table Contents \n";
      return 0;
    }
    else
    {
      printf("\n");
      std::cout << "Display Table Contents After " << memoryReferenceValue  << " memory reference(s) \n";
      return 1;
    }
  }
  else
  {
    std::cerr << " :: Error :: Invalid Memory Reference Value :: \n" ;
    return 2;
  }
}

/**
 * Name: number_Of_References()
 * Purpose: determine the number of memory references
 * Receive: vector of memory references
 * Return: number of memory references
 */
int number_Of_References(vector<string> memoryReferences)
{
  std::string memoryReference = memoryReferences[0];
  if((is_digit(memoryReference)))
  {
    int memoryReferenceValue = atoi(memoryReference.c_str());
    return memoryReferenceValue;
  }
}

/**
 * Name: create__Empty_PageTable
 * Purpose: create an empty page table with appropriate headers
 * Receive: none
 * Return: vector of strings
 */
std::vector<string> create__Empty_PageTable()
{
  std::vector<string> listOfIndexes { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
  std::vector<string> emptyPageTable;
  std::string pageTableHeaders = "I V R M FM";
  emptyPageTable.push_back(pageTableHeaders);
  for(int i = 0; i < listOfIndexes.size(); i++)
  {
    std::string pageTableStr;
    pageTableStr += listOfIndexes[i];
    pageTableStr += " 0 0 0 00";
    emptyPageTable.push_back(pageTableStr);
  }
  return emptyPageTable;
}

/**
 * Name: print_Vector
 * Purpose: Print out vector contents (currently used to print the pagetable )
 * Receive: vector of strings
 * Return: none
 */
void print_Vector(vector<string> pageTable)
{
  printf("\n");
  for(int i = 0; i < pageTable.size(); i++)
  {
    std::cout << pageTable[i] << "\n";
  }
  printf("\n");
}

/**
 * Name: determine_LoopCount
 * Purpose: This function used to return the number of loops but it is not used.
 * Receive: count, vector of strings
 * Return: int
 */
int determine_LoopCount(int count, int memoryReferenceVectorSize)
{
  int i = 0;
  if(count >= memoryReferenceVectorSize)
  {
    i = memoryReferenceVectorSize;
  }
  else if(memoryReferenceVectorSize % count == 0)
  {
    i = memoryReferenceVectorSize / count;
  }
  else
  {
    i = memoryReferenceVectorSize / count + 1;
  }
  return i;
}

//new content
bool determine_valid_page_replacementAlgorithm(std::string algorithmStr)
{
  if(algorithmStr == "FIFO")
  {
    return 0;
  }
  else if (algorithmStr == "LRU")
  {
    return 0;
  }
  else if (algorithmStr == "CLOCK")
  {
    return 0;
  }
  else
  {
    char buf[512];
    size_t nbytes;
    strcpy(buf, ":: Error :: Invalid Page Replacement Algorithm :: \n ");
    nbytes = strlen(buf);
    write(2, buf , nbytes);
    return 1;
  }
}

void display_invalid_page_frame_error()
{
  char buf[512];
  size_t nbytes;
  strcpy(buf, " :: Error :: Invalid Page Frame Replacement Value :: \n ");
  nbytes = strlen(buf);
  write(2, buf , nbytes);
}

std::vector<string> create_pageFrameListVector(int numberOfPageFrames)
{
  std::vector<string> tempVector;
  if(numberOfPageFrames > 0)
  {
    int startingPageFrame = 40;
    int lastPageFrame = startingPageFrame + numberOfPageFrames;
    for(int i = 40; i < lastPageFrame + 1; i++)
    {
      tempVector.push_back(std::to_string(i));
    }
  }
  return tempVector;
}








// end content

// ############################################################################
int main( int argc, char *argv[], char *env[])
{
  std::string cliInput;
  for(int i=1; i<argc; i++)
    {
      cliInput.append(argv[i]);
      cliInput.append(" ");
    }
  int inputFile;
  char buf[512]; //buffer storing contents of file
  size_t nbytes; //number of bytes
  ssize_t bytesRead; // bytes rad from input file
  ssize_t bytesWrite; // bytes written
  istringstream iss(cliInput);
  vector<string> cliInputVector;
  copy(istream_iterator<string>(iss), istream_iterator<string>(),back_inserter(cliInputVector));

  vector<string> numMemoryReferencesVector;
  vector<string> filesToOpen;

  if(cliInputVector.size() != 2)
  {
    std::cerr << " :: Invalid Number of Arguments ::  \n";
  }
  else
  {
    filesToOpen.push_back(cliInputVector.back());
    cliInputVector.pop_back();
    numMemoryReferencesVector.push_back(cliInputVector.back());
    cliInputVector.pop_back();
  }

  if(filesToOpen.size() == 1)
  {
    const char * c = filesToOpen.back().c_str();
    inputFile = open(c, O_RDONLY);
    if(inputFile == -1)
    {
      display_file_open_error();
    }
    else
    {
      std::vector<string> pageTable = create__Empty_PageTable();
      std::ifstream infile(filesToOpen.back());
      std::ifstream myFile(filesToOpen.back());
      std::string line;
      std::vector<std::string> fileContentVector;
      std::vector<std::string> memoryReferenceVectorCollection;
      int numMemoryReferences = 0;

      //NEW content
      std::vector<std::string> pageFrameListVector;
      std::vector<std::string> pageReplacementAlgorithmVector;
      std:vector<string> totalFileContentVector;
      std::string lineTotal;
      while(std::getline(myFile, lineTotal))
      {
        totalFileContentVector.push_back(lineTotal);
      }
      std::string pageReplacementAlgorithm;
      pageReplacementAlgorithm = totalFileContentVector[0];
      for (auto & c: pageReplacementAlgorithm) c = toupper(c); //convert to upper case
      int validAlgorithm = determine_valid_page_replacementAlgorithm(pageReplacementAlgorithm);
      if(validAlgorithm > 0) // Error handling for invalid page replacement Algorithm
      {
        return 0;
      }
      std::string numberOfPageFramesStr = totalFileContentVector[1];
      if(is_digit(numberOfPageFramesStr) == 0) // Error handling for invalid number of page frames allocated to process
      {
        display_invalid_page_frame_error();
        return 0;
      }
      int numberOfPageFrames = atoi(numberOfPageFramesStr.c_str());
      pageFrameListVector = create_pageFrameListVector(numberOfPageFrames); // create page frame list vector

      totalFileContentVector.erase(totalFileContentVector.begin()); //pop the algorithm off
      totalFileContentVector.erase(totalFileContentVector.begin()); //pop the number of page frames off
      //END New content

      int displayTable = display_PageTable(numMemoryReferencesVector);
      for(int i = 0; i < totalFileContentVector.size(); i++)
      {
        std::string line = totalFileContentVector[i];
        numMemoryReferences++;
        std::stringstream iss(line);
        std::istream_iterator<std::string> begin(iss);
        std::istream_iterator<std::string> end;
        std::vector<std::string> lineContentVector(begin, end);
        for(int i = 0; i < lineContentVector.size(); i++) //
        {
          fileContentVector.push_back(lineContentVector[i]); // creates a vector with all the file contents [item1Line1, item2line1, item2line2, etc]
        }
        std::vector<std::string> memoryReferenceVector;
        if(lineContentVector.size() == 2) // algorithmn for the memory reference ** NEED TO HANDLE ERRORS
        {
          memoryReferenceVector.push_back(lineContentVector[0]); // 'R' or 'W' operation
          if(lineContentVector[1].size() == 4)
          {
            memoryReferenceVector.push_back(lineContentVector[1]); //logical address being referenced (add zeros )
            std::string pageNumber = lineContentVector[1];
            pageNumber = pageNumber.at(0);
            memoryReferenceVector.push_back(pageNumber); // page number
            std::string pageOffset = lineContentVector[1];
            pageOffset = pageOffset.substr(1, pageOffset.size());
            memoryReferenceVector.push_back(pageOffset); // page offset
            //NEW contents
            std::string pageFaultFlag = " ";
            memoryReferenceVector.push_back(pageFaultFlag);
            std::string writeBackFlag = " ";
            memoryReferenceVector.push_back(writeBackFlag);
            std::string physicalAddress = "00000";
            memoryReferenceVector.push_back(physicalAddress);

            //END NEW Content
            std::stringstream ss;
            for(size_t i = 0; i < memoryReferenceVector.size(); ++i)
            {
              if( i != 0)
                ss << " ";
                ss << memoryReferenceVector[i];
            }
            std::string s = ss.str();
            memoryReferenceVectorCollection.push_back(s);
          }
          else if(lineContentVector[1].size() > 4)
          {
            std::cerr << ":: Error Invalid Logical Address :: End Program :: \n";
            return 0;
          }
          else
          {
            int leadingZeroCount = 4 - lineContentVector[1].size();
            std::string newMemoryReference;
            for(int i = 0; i < leadingZeroCount; i++)
            {
              newMemoryReference += "0";
            }
            newMemoryReference += lineContentVector[1];
            memoryReferenceVector.push_back(newMemoryReference);
            std::string pageNumber = newMemoryReference;
            pageNumber = pageNumber.at(0);
            memoryReferenceVector.push_back(pageNumber); // page number
            std::string pageOffset = newMemoryReference;
            pageOffset = pageOffset.substr(1, pageOffset.size());
            memoryReferenceVector.push_back(pageOffset); // page offset

            //NEW contents
            std::string pageFaultFlag = " ";
            memoryReferenceVector.push_back(pageFaultFlag);
            std::string writeBackFlag = " ";
            memoryReferenceVector.push_back(writeBackFlag);
            std::string physicalAddress = "00000";
            memoryReferenceVector.push_back(physicalAddress);
            //END NEW Content

              std::stringstream ss;
              for(size_t i = 0; i < memoryReferenceVector.size(); ++i)
              {
                if( i != 0)
                  ss << " ";
                  ss << memoryReferenceVector[i];
              }
              std::string s = ss.str();
              memoryReferenceVectorCollection.push_back(s);
          }
        }
      }
      // ##############################################################################################
      //Implementing logic for project 7
      vector<string> pageIndexVector;
      int referenceCount = 0;
      if(numberOfPageFrames > memoryReferenceVectorCollection.size())
      {
        referenceCount = memoryReferenceVectorCollection.size();
      }
      else
      {
        referenceCount = numberOfPageFrames;
      }

      for(int i = 0; i < referenceCount; i++)
      {
        std::string tempPageNumber = std::to_string(i);
        pageIndexVector.push_back(tempPageNumber); //
      }


        for(int k = 0; k < memoryReferenceVectorCollection.size(); k++)
        {
          stringstream ss;
          char x = memoryReferenceVectorCollection[k][7];
          ss << x;
          std::string internalPageFrameIndex;
          ss >> internalPageFrameIndex;
          //Handles page faults

          if(std::find(pageIndexVector.begin(), pageIndexVector.end(), internalPageFrameIndex) != pageIndexVector.end())
          {
            memoryReferenceVectorCollection[k][13] = 'F'; // set page fault
            char z = pageFrameListVector[k][0];
            memoryReferenceVectorCollection[k][17] = z; // change physical address 17,18
            char y = pageFrameListVector[k][1];
            memoryReferenceVectorCollection[k][18] = y;
            memoryReferenceVectorCollection[k][19] = memoryReferenceVectorCollection[k][3];
            memoryReferenceVectorCollection[k][20] = memoryReferenceVectorCollection[k][4];
            memoryReferenceVectorCollection[k][21] = memoryReferenceVectorCollection[k][5];

            if(memoryReferenceVectorCollection[k][0] == 'W')
            {
              memoryReferenceVectorCollection[k][15] = 'W';
              //memoryReferenceVectorCollection[k][17] = 'B';
            }
          }
          else
          {
          }
        }


        if(pageReplacementAlgorithm == "FIFO")
        {
        }
        if(pageReplacementAlgorithm == "LRU")
        {

        }
        if(pageReplacementAlgorithm == "Clock")
        {

        }

      // ##############################################################################################
      // Displaying the output
      int numReadOperations = determine_NumberOfOperations(fileContentVector, "R");
      int numWriteOperations = determine_NumberOfOperations(fileContentVector, "W");
      int count = number_Of_References(numMemoryReferencesVector);
      vector<string> displayMemoryReferenceVector;
      displayMemoryReferenceVector.push_back(":: File Contents :: ");

      if((count > memoryReferenceVectorCollection.size()) || (count == 0))
      {
        count = memoryReferenceVectorCollection.size();
      }
      int index = 0;
      int appendIndex = 0;
      if(displayTable < 2)
      {
        if(count > 0)
        {
          while(displayMemoryReferenceVector.size() != memoryReferenceVectorCollection.size() + 1)
          {
            if(displayTable == 1)
            {
              print_Vector(pageTable);
            }
            while((appendIndex != count) && (displayMemoryReferenceVector.size() != memoryReferenceVectorCollection.size() + 1))
            {
              displayMemoryReferenceVector.push_back(memoryReferenceVectorCollection[index]);
              index++;
              appendIndex++;
            }
              appendIndex = 0;
              print_Vector(displayMemoryReferenceVector);
          }
          if(displayTable == 1)
          {
            print_Vector(pageTable);
          }
        }
        std::cout << "Page Replacement Algorithm: " << pageReplacementAlgorithm << "\n";
        std::cout << "Number Of Frames Allocated: " << numberOfPageFrames << "\n";
        std::cout << "Total number of memory references: " << numMemoryReferences <<  "\n";
        std::cout << "Total number of read operations: " << numReadOperations << "\n";
        std::cout << "Total number of write operations: " << numWriteOperations << "\n";
      }
      else
      {
        std::cerr << " :: Error occurred :: \n";
      }
    }
  }
return 0;
}
