/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theparser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jeremyongts92
 */
public class SectionMgr {
	ArrayList<Section> sectionList = new ArrayList<>();
	ArrayList<HashMap<String,String>> infoMapList = new ArrayList<>();
	
	public void add(Section section){
		sectionList.add(section);
	}
	
	public Section get(String owner, int sectionNum){
		
		for (Section section : sectionList){
			String owner2 = section.getOwner();
			int sectionNum2 = section.getSectionNum();
			
			if (owner.equals(owner2) && sectionNum == sectionNum2){
				return section;
			}
			
		}
		return null;
		
	}
	
	public void processSections(){
		
		for (int i = 0; i < sectionList.size(); i++){
			
			HashMap<String,String> current = sectionList.get(i).processSection();
			infoMapList.add(current);
			
		}
		
	}
	
	public HashMap<String,String> getInfoMap(int sectionNum){
		return infoMapList.get(sectionNum - 1);
	}
	
}
