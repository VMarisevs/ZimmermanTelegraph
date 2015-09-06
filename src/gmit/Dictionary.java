package gmit;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Dictionary {

	public static boolean testing = true;
	private List<String> bufferArray = new ArrayList<String>();//list of unique words, by default size 900
	private Map<Integer,String> decode = new HashMap<Integer,String>();
	private Map<String,List<Integer>> encode = new HashMap<String,List<Integer>>();
	
	
	// range of unique values that are available for new dictionary generation
	//private final int RS_MIN = 1;
	//private final int RS_MAX = 99999;
	
	/*
	 * 1) 25 words with ratio 1000 = 25000
	 * 2) 75 words with ratio 166 = 12450
	 * 3) 200 words with ratio 56 = 11200
	 * 4) 600 words with ratio 43 = 25800
	 * 5) 					TOTAL:	74450
	 */
	private final int FIRST_25_WORDS_RANGE = 1000;
	private final int FIRST_100_WORDS_RANGE =  166;
	private final int FIRST_300_WORDS_RANGE = 56;
	private final int REST_WORDS_RANGE = 43;
	private final int SUM_OF_3_GROUPS = 48650; // 25000 + 12450 + 11200
	
	public void generateNewDictionary(int rs_min, int rs_max) throws Exception{
		/* 
		 *  
		 * creating an array list with unique numbers and shuffles them 
		 * the range is between 1 and 99999 and 75000 values we need
		 * 
		 * O(n^2)
		 * Outer loop selects a word from buffer array and puts it into map (const).
		 * Inner loop depending on the word ratio populating encode map with value and decode map
		 * saveAsXML O(n)
		 */
		RandomSet randomSet = new RandomSet(rs_min, rs_max); 
		
		int howManyWordsCanBeEncoded = 300 + (rs_max - rs_min - SUM_OF_3_GROUPS )/REST_WORDS_RANGE;
		// reading common English words and adding them to array list
	
		EditFiles.readingCommonEnglishWords(bufferArray);
		
		/*
		if (bufferArray.size() > 1494){
			 bufferArray = new ArrayList<String>(); 
			 throw new Exception("\n[ERROR] commonEnglishWords.txt contains more words that generator can do random codes." +
			 		"\n first 25 * "+ FIRST_25_WORDS_RANGE + " = " + (25*FIRST_25_WORDS_RANGE) + 
			 		"\n second 75 * " + FIRST_100_WORDS_RANGE + " = " +(75*FIRST_100_WORDS_RANGE) +
			 		"\n third 200 * "+ FIRST_300_WORDS_RANGE + " = " + (200*FIRST_300_WORDS_RANGE) + 
			 		"\n rest " + (bufferArray.size()-300) + " * " + REST_WORDS_RANGE +" = "+ ((bufferArray.size()-300)*REST_WORDS_RANGE) +
			 		"\n Total codes needed:\t" + (	(25*FIRST_25_WORDS_RANGE) + 
			 										(75*FIRST_100_WORDS_RANGE) +
			 										(200*FIRST_300_WORDS_RANGE) + 
			 										((bufferArray.size()-300)*REST_WORDS_RANGE))+
			 		"\n Total codes available:\t" + (rs_min - rs_max) +
			 		"\n (excluding code 00000)");	
		 }
		
		*/
		double start = System.currentTimeMillis();
		int randomSetId = 0;
		for (int wordId = 0 ; wordId < howManyWordsCanBeEncoded; wordId++ ){
			
			int ratio = FIRST_25_WORDS_RANGE;

				if (wordId>=25){
					ratio = FIRST_100_WORDS_RANGE;
				}
				
				if (wordId>=100){
					ratio = FIRST_300_WORDS_RANGE;
				}
				if (wordId>=300){
					ratio = REST_WORDS_RANGE;
				}

			encode.put(bufferArray.get(wordId), new ArrayList<Integer>());
			
			for (int setCodeId = 0 ; setCodeId < ratio; setCodeId++){
				
				int random = randomSet.getElement(randomSetId++);
				encode.get(bufferArray.get(wordId)).add(random);
				decode.put(random, bufferArray.get(wordId));			
				
			}
		}
		
		double loopTime = ((System.currentTimeMillis() - start)/1000);
		if (this.testing){
			System.out.println("\nLoop within the loop. " +
					"\nOuter loop goes through bufferArray of words. " +
					"\nAnd creates an entry in `encode`<String,List<Integer>>[size:"+encode.size()+"] map, with empty list." +
					"\nInner loop, depending on word's code ratio populates encode's " +
					"\nlist with value and `decode`<Integer,String>[size:"+decode.size()+"] map with code and word.["+loopTime+"Sec]");
		}
		//System.out.println("Decode size "+ decode.size());
		//System.out.println("Encode size " + encode.size());
		saveAsXMLmanual();
		//saveAsXML();
	}

	private void saveAsXMLmanual(){
		StringBuilder xmlContent = new StringBuilder();
		
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		xmlContent.append("<Dictionary>\n");
		
		// main context
		for(Entry<Integer,String>  entry: decode.entrySet()){
			xmlContent.append("<Code CodeValue=\""+entry.getKey()+"\">\n");
			
				xmlContent.append("<Word>");
				xmlContent.append(entry.getValue());
				xmlContent.append("</Word>\n");
			
			xmlContent.append("</Code>\n");
		}
		
		xmlContent.append("</Dictionary>");
		
		String timestamp = new java.text.SimpleDateFormat("ddMMyyyyhmmss").format(new java.util.Date());
		try {
			EditFiles.saveAsTXT(xmlContent.toString(),"dictionary/dictionary_"+timestamp+".xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveAsXML(){
		/*
		 * O(n)
		 */
		try{
			double start = System.currentTimeMillis();
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Dictionary");
			doc.appendChild(rootElement);
			

			for(Entry<Integer,String>  entry: decode.entrySet()){
				int code = entry.getKey();
				String word = entry.getValue();
				
				Element codeElement = doc.createElement("Code");
				rootElement.appendChild(codeElement);
				codeElement.setAttribute("CodeValue", String.valueOf(code));
				
				Element wordElement = doc.createElement("Word");
				wordElement.appendChild(doc.createTextNode(word.toString()));
				
				codeElement.appendChild(wordElement);
				
			}
			double buildingTime = ((System.currentTimeMillis() - start)/1000);
			start = System.currentTimeMillis();
			// write to xml file
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			DOMSource source = new DOMSource(doc);
			
			
			
			String timestamp = new java.text.SimpleDateFormat("ddMMyyyyhmmss").format(new java.util.Date());
			StreamResult result = new StreamResult(new File("dictionary/dictionary_"+timestamp+".xml"));
			
			transformer.transform(source, result);
			double savingTime = ((System.currentTimeMillis() - start)/1000);
			
			if (this.testing){
				System.out.println("\nThe dictionary is saved as xml file.[dictionary/dictionary_"+timestamp+".xml] " +
						"\nBased on `decode`[size:"+ decode.size()+"] map [O(n)] " +
						"\nRecords building time: " + buildingTime + "Sec." +
						"\nFile saving time: "+ savingTime+"Sec.");
			}
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		 }
		
	}
	
	public void loadXML(String fileName){
		/*
		 * O(n)
		 */
		 try {
			 double start = System.currentTimeMillis();
			 
			 	encode =  new HashMap<String,List<Integer>>();
			 	decode = new HashMap<Integer,String>();
			 	
				File fXmlFile = new File(fileName);				
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				
				 
				NodeList nList = doc.getElementsByTagName("Code");
				
				
				
				String next;
				for (int temp = 0; temp < nList.getLength(); temp++) {
					 
					Node nNode = nList.item(temp);
			 					
			 
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						
						Element eElement = (Element) nNode;						
					
						
						next = eElement.getElementsByTagName("Word").item(0).getTextContent();
						
						
						if (encode.get(next) == null){
							encode.put(next, new ArrayList<Integer>());
						}
						
						encode.get(next).add(Integer.parseInt(eElement.getAttribute("CodeValue")));
						
						decode.put(Integer.parseInt(eElement.getAttribute("CodeValue")), next);						 		
						
					}
					
				}
				double loadingTime = ((System.currentTimeMillis() - start)/1000);
				
				if (this.testing){
					System.out.println("\nDictionary["+fileName+"] is populating encode, and decode maps." +
							"\nLoading time: " + loadingTime + "Sec.[O(n)]. " +
									encode.size()+" words loaded. " +decode.size() +" codes loaded.");
				}
				
		 } catch (Exception e) {
				e.printStackTrace();
		 }
		 
	}

	public String encodeMsg(String msg, Boolean zeros){
		double start = System.currentTimeMillis();
		/*
		 * O(n) for each loop in this method
		 */
		// replacing new line with space
		// O(n) loop through whole string
		msg = msg.replaceAll("\n"," ");
		msg = msg.replaceAll("\t"," ");
		
		// O(n) loop through whole string
		String[] wordsBuffer = msg.split(" "); 
		List<String> codeBuffer = new ArrayList<String>();
		StringBuffer encoded = new StringBuffer();
		
		
		// Analyzer reads the message and has each word count
		// so each word code will be more unique
		
		
		// O(n)
		Map<String,Integer> analyser = new HashMap<String,Integer>();
		for (int i = 0; i<wordsBuffer.length; i++){
			
			wordsBuffer[i] = wordsBuffer[i].toLowerCase();
			
			if (encode.containsKey(wordsBuffer[i])){
				if (!analyser.containsKey(wordsBuffer[i])){
					analyser.put(wordsBuffer[i], 1);
				} else {
					analyser.put(wordsBuffer[i], (analyser.get(wordsBuffer[i])+1));
				}	
				
			} else {
				// remove from message
				wordsBuffer[i] = "";
			}			
			
		}
		
		// checking if the word doesn't contains in dictionary
		/*
		for (Map.Entry<String, Integer> entry :analyser.entrySet()){
			String word = entry.getKey();
			
			if (!encode.containsKey(word)){		
				
				// return an error
				return "[Error] Cannot encrypt using this dictionary, please generate new one with populated words";
			}
		}
		*/
		
		// looping through array of words and if it is not empty
		
		for (int i = 0; i<wordsBuffer.length; i++){
			
			if (wordsBuffer[i]!=""){
				
				// pulling the encode list from Map
				// we can avoid using tmpSet
				// but it makes code more readable 
				
				// all get and put methods are constant because used known indexes
				List<Integer> tmpSet = encode.get(wordsBuffer[i]);
				
				if (tmpSet.size() >= analyser.get(wordsBuffer[i]) ){
					analyser.put(wordsBuffer[i], (analyser.get(wordsBuffer[i])-1));
					
					codeBuffer.add(tmpSet.get(analyser.get(wordsBuffer[i])).toString());
					
					
				} else {
					analyser.put(wordsBuffer[i], (analyser.get(wordsBuffer[i])-1));
					
					int mult = analyser.get(wordsBuffer[i]) / tmpSet.size();
					int index = analyser.get(wordsBuffer[i])  - mult*tmpSet.size();
					codeBuffer.add(tmpSet.get(index).toString());
					
					
				}
			}
			
		}
		
		
		int counter = 0;
		for (String s: codeBuffer){
			
			if (zeros){
				switch(s.length()){
				case 1:
					s = "0000" + s;
					break;
				case 2: 
					s = "000" + s;
					break;
				case 3:
					s = "00" + s;
					break;
				case 4:
					s = "0" + s;
					break;
				}
			}
			
			if (++counter == 10){
				encoded.append(s +"\n");
				counter = 0;
			} else {
				encoded.append(s+"\t");
			}

		}
		
		
		double encodingTime = ((System.currentTimeMillis() - start)/1000);
		
		if (this.testing){
			System.out.println("\nEncoding time "+ encodingTime + "Sec. I have 5 loops that goes one by another." +
					"\n1) Creates array of words (String) separated by space.["+wordsBuffer.length+" words]" +
					"\n2) Analysing string, how many times words were repeated, to reduce chance of repeating code." +
					"\n3) Goes through set of words["+analyser.size()+"] and checking by key, does this word has an entry in encode." +
					"\n4) Looping through array of words, pulling code for this word, and adding to another array." +
					"\n5) Looping through array of codes, and adding 0 at the front.(I could do 4 5 together, but to keep it readable.)");
		}
		
		return encoded.toString();
	}
	
	public String encodeMsgFast(String msg, Boolean zeros){
		/*
		 * O(n) 1 for loop + replace method in the string
		 */
		double start = System.currentTimeMillis();
		msg = msg.replaceAll("\n"," ");
		msg = msg.replaceAll("\t"," ");
		// O(n) loop through whole string
		String[] msgBuffer = msg.split(" ");
		Map<String,Integer> analyser = new HashMap<String,Integer>();
		String code = "";
		StringBuffer encoded = new StringBuffer("");
		
		for (int i = 0, counter = 0; i<msgBuffer.length; i++){
			msgBuffer[i] = msgBuffer[i].toLowerCase();
			
			// checking if the word exists in dictionary
			if (encode.containsKey(msgBuffer[i])){
				
				// encoding the word
				if (analyser.containsKey(msgBuffer[i])){
					int maxValue =analyser.get(msgBuffer[i]);
					maxValue++;
					
					// if it's more than size of a list, than restart the counter
						if (maxValue == encode.get(msgBuffer[i]).size()){
							maxValue = 0;
						} 
					
					analyser.put(msgBuffer[i], maxValue);
					
					
					
				} else {
					analyser.put(msgBuffer[i], 0);
				}
				code = encode.get(msgBuffer[i]).get(analyser.get(msgBuffer[i])).toString();
				
				// concatenating code msg
				if (zeros){
					// switching the length of the code
					switch(code.length()){
					case 1:
						code = "0000" + code;
						break;
					case 2: 
						code = "000" + code;
						break;
					case 3:
						code = "00" + code;
						break;
					case 4:
						code = "0" + code;
						break;
					}
				}
				// adding new line or tab
				
				
				if ((++counter%10)==0){
					code += '\n';
				} else{
					code += '\t';
				}
				
				encoded.append(code); 
				
			} else {
				msgBuffer[i] = "";
			}
			
			
		}
		
		
		double encodingTime = ((System.currentTimeMillis() - start)/1000);
		if (this.testing){
			System.out.println("\nEncoding time "+ encodingTime + "Sec. " +
					"\n Creates array of words (String) separated by space.["+msgBuffer.length+" words]");
		}
		
		return encoded.toString();
	}
	
	public String decodeMsg(String msg){
		/*
		 * O(n)
		 */
		double start = System.currentTimeMillis();
		
		StringBuffer decoding = new StringBuffer();
		msg = msg.replaceAll("\n", "\t");
		String[] codeBuffer = msg.split("\t"); 
		
		
		String next;
		for (int i= 0; i<codeBuffer.length; i++){
			
			try{
				if (decode.containsKey(Integer.parseInt(codeBuffer[i]))){
					next = decode.get(Integer.parseInt(codeBuffer[i]));
				} else {
					next = "[null:"+codeBuffer[i]+"]";
				}
				
				decoding.append(" " + next);
				
				if (((i+1) % 10) == 0){
					decoding.append("\n");
				}
			} catch(Exception e){
				System.out.println("Please insert code values.");
			}
		}
		
		double decodingTime = ((System.currentTimeMillis() - start)/1000);
		System.out.println("\nDecoding time: " + decodingTime + "Sec.3 Loops." +
				"\n1) Removes all new line characters." +
				"\n2) Splits message into array." +
				"\n3) Building decoding string.");
		
		return decoding.toString();
	}


}
