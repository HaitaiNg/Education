#
# Haitai Ng
# Filename: terminalPrompt.make
#

# Usage: make -f terminalPrompt.make
# Makes: terminalPrompt 

terminalPrompt: terminalPrompt.student.o
	g++ terminalPrompt.student.o -o terminalPrompt

terminalPrompt.student.o: terminalPrompt.student.c
	g++ -std=c++11 -g  -c terminalPrompt.student.c

clean:
	rm -f terminalPrompt.student.o
	rm -f terminalPrompt
