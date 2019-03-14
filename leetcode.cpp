//REVERSE AN INTEGER
class Solution {
 public :
   int reverse(int x) 
	{ 
	 if(x > 0)
	 {
		x = -x; 
		positive = false ;
	 } 

	long long int temp = 0;
	while(x>0)
	{ temp = temp*10+x%10; 
	 x /= 10;
	}
	if ((temp>>31)>0)
	return 0; 
	if(positive)
		return (int)temp;
	return (int)-temp; 
}
}; 

//Valid Parenthesis 
class Solution {
public:
    bool isValid(string s) {
        stack<char> st;
        for (int i = 0; i < s.size(); i++) {
            if (s[i] == '(' || s[i] == '[' || s[i] == '{') {
                st.push(s[i]);
            }
            else {
                if(st.empty()) return false;
                char cur = st.top();
                st.pop();
                if (s[i] == ')') {
                     if (cur == '(') continue;
                }
                if (s[i] == ']') {
                     if (cur == '[') continue;
                }
                if (s[i] == '}') {
                     if (cur == '{') continue;
                }
                return false;
            }
        }
        return st.empty();
    }
};

//
class Solution { 
Public: 
	char getOpenBracket(char closeBracket)
{
	if(closeBracket == ')')
		return '('; 
	if(closeBracket == '}')
		return '{'; 
	return '['; 
}

	bool isValid(string s )
{
	stack<char mystack; 
	for(int i =0; I < s.size(); I++)
	{
		if(s[I] == '(' || s[I] == '{' || s[I] == '[')
		{
			mystack.push(s[i]); 
		}
		else
		{
		 if(mystack.empty() || mystack.top() != getOpenBracket(s[I])) 
			return false;
		mystack.pop()
		}
	}
	return mystack.empty()
	}
}; 