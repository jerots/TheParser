/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theparser;

import java.util.HashMap;

/**
 *
 * @author jeremyongts92
 */
public class Section {
	
	String owner;
	int sectionNum;
	String[][] source;
	HashMap<String, String> infoMap;
	
	public Section (String owner, int num) {
		this.owner = owner;
		sectionNum = num;
		source = new String[0][0];
		infoMap = new HashMap<>();
	}

	public String getOwner() {
		return owner;
	}

	public int getSectionNum() {
		return sectionNum;
	}
	
	
	
	public HashMap<String, String> processSection(){
		
		for (String[] line : source){
			String tempKey = line[0];
			String key = tempKey;
			int index = 0;
			
			if (key.length() > 0){
				
				while (infoMap.get(key) != null){ //while key exists
					key = tempKey;
					key += "alt" + index;
					index++;
					
				}

				String value = line[1];
				for (int j = 2; j < line.length; j++) {
					String current = line[j];
					if (current.length() > 0) {
						value += "," + line[j];
					}
				}
				infoMap.put(key, value);
			}
		}

		return infoMap;
		
	}
	
	public void addLine (String[] line, int lineNum){
		String[][] newArr = new String[source.length+1][];
		System.arraycopy(source, 0, newArr, 0, source.length);
		source = newArr;
		
		source[lineNum] = line;
	}
	
	
}
