package gmit;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class RandomSet {
	
	private List<Integer> randomSet = new ArrayList<Integer>();
	
	public RandomSet(int min, int max) throws Exception{
		
		if (min<max){
			
			/*
			 * O(n), creating a list of UNIQUE numbers in the range min and max value
			 * then shuffle them, and reducing the size
			 */
		
			double start = System.currentTimeMillis();
				for (int i=min; i<max; i++){
					randomSet.add(i);
				}
				
			double creationTime = ((System.currentTimeMillis() - start)/1000); 
					//System.out.println("Creating list of unique numbers:");
					//printRandomSet();
			
			start = System.currentTimeMillis();	
			
				Collections.shuffle(randomSet);	
			
			double shufflingTime = ((System.currentTimeMillis() - start)/1000); 
					//System.out.println("Shuffle unique list:");
					//printRandomSet();
			
			if (Dictionary.testing){
				System.out.println("\nRandom Set generated " + (max-min) 
						+ " values[O(n)]. Generation time: " + creationTime+ "ms. Shuffling time: " + shufflingTime + "ms.");
			}
			
		} else{
			throw new Exception("[ERROR] RandomSet minimal value is more that maximal");
		}
	}
	
	public int getElement(int index){
		/*
		 * O(1)
		 */
		return randomSet.get(index);
	}
	
	public void printRandomSet(){
		/*
		 * O(n) if is not empty
		 */
		if (!randomSet.isEmpty()){
			for (int i=0; i<randomSet.size();i++){
				System.out.println(i+"   "+randomSet.get(i).toString());
			}
		}
	}
}
