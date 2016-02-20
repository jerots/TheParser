/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theparser;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jeremyongts92
 */
public class TheParser {

	/**
	 * @param args the command line arguments
	 */
	
	public static void main(String[] args) throws IOException{

		Properties properties = new Properties();
		
		//For run.bat
		final File folder = new File("data/reports");
		final File saveFolder = new File("./");
		
		//For NetBeans development
//		final File folder = new File("../data/reports");
//		final File saveFolder = new File("../data");
		
		//search all file paths
		ArrayList<String> paths = new ArrayList<>();
		scanFiles(folder, paths);
		
		//initialise header in file
		CSVWriter writer = new CSVWriter(new FileWriter(saveFolder + "/" + "result.csv"));
		initHeaders(writer,properties);
		
		//For each user; path is used as user's unique identifier
		for (String path : paths){
			
			// If file is a .csv file
			if (path.contains(".csv")){
				
				CSVReader reader = new CSVReader(new FileReader(folder + "/" + path));
				SectionMgr sectionMgr = new SectionMgr();
				
				//SECTIONS READING
					for (int i = 0; i < 23; i++){
						
						Section section = new Section(path,i+1);
						sectionMgr.add(section);
						
						int lineNum = 0;
						
						while (true){
							String[] nextLine = reader.readNext();
							if (nextLine == null || nextLine[0].contains("___________")){
								break;
							}
							
							section.addLine(nextLine, lineNum);
							lineNum++;
						}
						
						
					}
					
				//WRITING FROM PROPERTIES
				
				ArrayList<String> toWrite = new ArrayList<>();
				int size = properties.getSource().length - 1; //minus one because of header
				sectionMgr.processSections();
				
				for (int i = 1 ; i <= size; i++){ //for the number of columns
					
					HashMap<Integer,String[]> infoMap = properties.getInfoMap();
					String[] currentCol = infoMap.get(i);
					
					
					String specialMethod = currentCol[4];
					String ifEmpty = currentCol[5];
					int section = Integer.parseInt(currentCol[2]);
					String valueKey = currentCol[3];
					HashMap<String,String> sectionInfoMap = sectionMgr.getInfoMap(section);
					
					if (specialMethod.length() > 0){
						switch (specialMethod){
							case "processReferral":
								
								toWrite.add(checkEmpty(DisplayHandler.processReferral(sectionInfoMap),ifEmpty));
								break;
							case "financialSupport":
								toWrite.add(checkEmpty(DisplayHandler.parseFinanceSupport(sectionInfoMap),ifEmpty));
								break;
							case "workingExpCategory":
								String workingExp = sectionInfoMap.get(valueKey);
								toWrite.add(checkEmpty(DisplayHandler.calculateWorkingExp(workingExp),ifEmpty));
								break;
							case "reqEngProficiency":
								toWrite.add(checkEmpty(DisplayHandler.reqEngProficiency(sectionInfoMap,valueKey),ifEmpty));
								break;
							case "compulsoryCEC":
								toWrite.add(checkEmpty(DisplayHandler.compulsoryCEC(sectionInfoMap,valueKey),ifEmpty));
								break;
							case "isSingaporean":
								toWrite.add(checkEmpty(DisplayHandler.isSingaporean(sectionInfoMap,valueKey),ifEmpty));
								break;
							case "GMAT":
								toWrite.add(checkEmpty(DisplayHandler.GMATgenerator(sectionInfoMap, path),ifEmpty));
								break;
							case "engProficiencyScore":
								toWrite.add(checkEmpty(DisplayHandler.engProficiency(sectionInfoMap),ifEmpty));
								break;
							case "progType":
								toWrite.add(checkEmpty(DisplayHandler.generateProgType(sectionInfoMap),ifEmpty));
								break;
							case "mailingAddress":
								toWrite.add(checkEmpty(DisplayHandler.parseAddress(sectionInfoMap),ifEmpty));
								break;
							case "certification":
								toWrite.add(checkEmpty(DisplayHandler.getCertification(sectionInfoMap),ifEmpty));
								break;
							case "bachelorSchool":
								DisplayHandler.getBachelorSchool(sectionInfoMap); //run this to process bachelor school
								toWrite.add(checkEmpty(sectionInfoMap.get("Bachelor School"),ifEmpty));
								break;
							case "bachelorDegree":
								toWrite.add(checkEmpty(DisplayHandler.getBachelor(sectionInfoMap),ifEmpty));
								break;
							case "nationality":
								toWrite.add(checkEmpty(sectionInfoMap.get("Nationality") + "/" + sectionInfoMap.get("Singapore Citizen Status"),ifEmpty));
								break;
							case "age":
								String birthdate = sectionInfoMap.get("Date of Birth");
								String age = DisplayHandler.calculateAge(birthdate);
								toWrite.add(checkEmpty(age,ifEmpty));
								break;
							case "ageGroup":
								birthdate = sectionInfoMap.get("Date of Birth");
								age = DisplayHandler.calculateAge(birthdate);
								toWrite.add(checkEmpty(DisplayHandler.calculateAgeGroup(age),ifEmpty));
								break;
							default:
								toWrite.add(specialMethod);
								break;
						}
					} else {
						toWrite.add(checkEmpty(sectionInfoMap.get(valueKey),ifEmpty));
					}
					
					
					
					
				}
				String[] arrWrite = new String[toWrite.size()];
				toWrite.toArray(arrWrite);
				writer.writeNext(arrWrite);
				
			}
			
		}
		
		writer.close();
		
	}
	
	
	public static void scanFiles(final File folder, ArrayList<String> paths) {
		for (final File fileEntry : folder.listFiles()) {
			
			// Looks into directories for files.
			if (fileEntry.isDirectory()) {
				scanFiles(fileEntry, paths);
			} else {
				paths.add(fileEntry.getName());
			}
		}
	}
	
	
	public static void initHeaders(CSVWriter writer, Properties properties){
		
		ArrayList<String> headers = new ArrayList<>();
		HashMap<Integer,String[]> infoMap = properties.getInfoMap();
		
		int index = 1;
		String[] currentRow = null;

		while ((currentRow = infoMap.get(index)) != null){
			headers.add(currentRow[1]);
			index++;
			
		}
		
		String[] toWrite = new String[headers.size()];
		headers.toArray(toWrite);
		
		writer.writeNext(toWrite);
		
	}
	
	public static String checkEmpty (String res, String ifEmpty){
		if (res == null || res.length() == 0){
			return ifEmpty;
		}
		return res;
	}

}
