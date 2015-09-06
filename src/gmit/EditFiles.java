package gmit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditFiles {
	
	private final static String DICTIONARY_FILE = "commonEnglishWords.txt";
	
	public static void readingCommonEnglishWords(List<String> bufferArray) throws Exception{
		/*
		 * O(n) 1 while loop
		 */
		try{
			
			double start = System.currentTimeMillis();
			
			BufferedReader br = new BufferedReader(new InputStreamReader
													( new DataInputStream(new FileInputStream(DICTIONARY_FILE))));
			
			/* Reading all words from commonEnglishWords, and creating keys without values O(n) */
			
			String next;
			
			while ((next = br.readLine()) != null) {
								
				bufferArray.add(next);				
			}
			
			br.close();
			double readTime = ((System.currentTimeMillis() - start)/1000); 
			if (Dictionary.testing){
				System.out.println("\nLoading words from commonEnglishWords.txt to List<String> bufferArray[O(n)]." +
						"[" + readTime +"Sec] [size:" + bufferArray.size()+"]");
			}
		} catch (Exception e){
			throw new Exception("[ERROR] Encountered a problem reading the file. " + e.getMessage());	
		}
				
	}
	
	public static int populateCommonEnglishWords(String newWords) throws Exception{
		/*
		 * O(n) splitting string to array and 1 for loop
		 */
		
		int commonWordsCount = 0; // returns how many new words needs to be added
		
		String words = openTXT(DICTIONARY_FILE); // reading common words from file
		String[] wordsBuffer = words.split("\n"); // splitting common words into array
		String[] newWordsBuffer = newWords.split(" "); // splitting new words into array
		
		Map<String,String> wordsBuf = new HashMap<String,String>(); // hash map to speedup search
		StringBuilder newWordsToBeWritten = new StringBuilder();
		//ArrayList<String> newWordsToBeWritten = new ArrayList<String>(); // new unique words to add into common words
		
		
		// building a map for each word from common words file
		for (String word: wordsBuffer){
	
				wordsBuf.put(word, word);
				commonWordsCount++;
		
		}
		
		
		// creating new words string
		if (newWords.length() > 0){
			for (String newWord: newWordsBuffer){
				if (!wordsBuf.containsKey(newWord)){
					System.out.println("Word ["+newWord +"] was added into commonWords file.");
					newWordsToBeWritten.append("\n"+newWord);
					commonWordsCount++;
				}
			}
		}

		
		BufferedWriter out = new BufferedWriter(new FileWriter(DICTIONARY_FILE,true));
		out.write(newWordsToBeWritten.toString());
		out.flush();
		out.close();
		
		return commonWordsCount;
	}

	public static String openTXT(String fileName) throws Exception{
		/*
		 * O(n) 1 while loop
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader
				( new DataInputStream(new FileInputStream(fileName))));
		
		StringBuffer fileTXT = new StringBuffer();
		String next = "";
		while ((next = br.readLine()) != null) {
			
			fileTXT.append(next);
			if (fileTXT != null){
				fileTXT.append("\n");
			}
		}
		
		br.close();
		return fileTXT.toString();
	}
	
	public static void saveAsTXT(String msg,String fileName) throws Exception{
		/*
		 * O(1)
		 */
	            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
	            out.write(msg);
	            out.flush();
	            out.close();
	}


	
}
