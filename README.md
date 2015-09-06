# Zimmerman Telegraph
	by Vladislavs Marisevs

# Galway Mayo Institute of Technology Software Development Level 7
Module: Algorithms
Lecturer: Dr. John Healy
Submission Date: April 9th 2014

# About
This is an ATM emulator system, where all data is saved in MS Access file.

# IDE 
	-	Application was developed using Eclipse
	-	User-friendly interface, that can speed up the user's work.GUI more attractive for non-technical people and it looks more professional.:)	
	-	It was designed for Windows.
	
# User guide
	-	cmd => java -jar encoder.jar gmit/Runner.class
	-	Eclipse project Runner.class

	
# Summary

Whole point of this project was to make loops more efficient, and not to stuck in infinite loop or orverload the PC.

	Class RandomSet:
	-	Constructor, creating values in the list, depending on the range 
		and shuffling them. Adding values at the end of array list(const) n times.
		O(n) + shuffling method.
		
	-	printRandomSet, O(n) looping and printing each value.
	
	Class EditFiles:
	-	readingCommonEnglishWords O(n). Dictionary file readed and each line were 
		added into bufferArray.
		
	-	populateCommonEnglishWords O(3n). First loop is openTXT method O(n), 
		split string to array O(n), comparing each word to new one if it exists O(n).
		If it doesn't exists add into file O(1);
		
	-	openTXT O(n). Reading file and adding into string splitting them with '\n'
	
	-	saveAsTXT 0(1). if the size of the message doesn't change it will write 
		with approximately the same time.
		
	Dictionary
	-	generateNewDictionary .readingCommonEnglishWords O(n) + Loop within the loop 
		Outer loop selects a word from buffer array and puts it into map (const). Inner 
		loop depending on the word ratio populating encode map with value and decode map 
		with new key and associated word O(n^2). + saveAsXML O(n)
		
	-	saveAsXML o(n). creating a file depending on decode map (int key, string value).
	
	-	loadXML o(n). reading xml file and populating both maps encode and decode.
	
	-	encodeMsg .
		+	O(n) Looping through whole message and replacing '\n' with ' '
		+	O(n) Looping through whole message and replacing '\t' with ' '
		
		+	O(n) Splitting words depending on ' ' and adding words into buffer array
*			Creating temporary map 'analyser' that contains the word as key and int value 
			as how many times this word was mentioned.
		
		+	O(n) Looping through whole buffer array and analysing how many times word was mentioned.
			this method will help us to encode the message with 100% dictionary usage.
			If we will have 50 code values and this word will be mentioned 51 times in the message 
			it will use just 1 duplicate value.
			and if the word doesn't exists in the dictionary it will be replaced with empty string.
		
		+	O(n) Looping through buffer array and populating code buffer with unique values.
		
		+	O(n) Building a code string with tab and new line separators. 
		
	-	encodeMsgFast .
		+	O(n) Looping through whole message and replacing '\n' with ' '
		+	O(n) Looping through whole message and replacing '\t' with ' '
		+	O(n) Looping through whole message analysing and building a code string

	-	decodeMsg
		+	O(n) Replacing '\n' chars to '\t'
		+	O(n) Populating code buffer array with message codes.
		+	O(n) Building a decoded string using map.
	
# Project Extras

	++	I have found a bug in the program, concatinating strings overloaded the jvm.
		I changed big strings into stringbuilder and it started to work faster.
		When I tried to save into xml file using javax.xml and org.w3c.dom it had the same problem,
		so I write my own xml file writer saveAsXMLmanual() in dictionary class.



	-	File => New ; This function helps user to generate new dictionary.
		User can open txt file and by pressing on `analyse` button it will loop through the message 
		and populates commonEnglishWords file with new words. (by default MaX words 1494 because 
		of the default range 1..99999)
		By pressing Generate Dictionary it will generate new dictionary using commonEnglishWords file 
		and using range selected by user.

	-	To avoid infinite loop in random value generator, I used shuffled list.
		Class RandomSet imlements an array list of integers, in range entered by user.
		The default range that used in program is 1..99999

	-	Randomly generated codes can be selected ascending or descending order.
		
	-	If code value is less than 5 digit long to keep the layout, 
		user can add or remove at the front of the code
		
	-	User can load any xml dictionary and encode or decode the message.
		If the dictionary is wrong it might translate the message wrong.
		
	-	User can Open txt file as message text. (Encode or Decode)

	-	User can Save as txt file. (Encoded or Decoded message)