/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theparser;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jeremyongts92
 */
public class Properties {
	//For run.bat
	final File saveFolder = new File("./");
	
	//For Netbeans development
//	final File saveFolder = new File("../data");
	
	CSVReader reader;
	CSVWriter writer;
	String[][] source;
	HashMap<Integer,String[]> infoMap;
	
	public Properties() throws IOException{
		load();
		processInfo();
	}
	
	public void load() throws IOException{
		try {
			reader = new CSVReader(new FileReader(saveFolder + "/" + "properties.csv"));
		} catch (FileNotFoundException e){
			System.out.println("properties.csv file not found in " + "/" + saveFolder + "/");
			System.out.println("Creating new properties.csv...");
			writer = new CSVWriter(new FileWriter(saveFolder + "/" + "properties.csv"));
			generateDefault(writer);
			writer.close();
			
			reader = new CSVReader(new FileReader(saveFolder + "/" + "properties.csv"));
		}
		String[] nextLine;
			source = new String[0][];
			int lineNum = 0;
			
			while ((nextLine = reader.readNext()) != null){
				
				String[][] newArr = new String[source.length+1][];
				System.arraycopy(source, 0, newArr, 0, source.length);
				source = newArr;
		
				source[lineNum] = nextLine;
				lineNum++;
				
			}
		
	}
	
	public void processInfo(){
		
		//Stores each line of []
		infoMap = new HashMap<>();
		for (int i = 1; i < source.length; i ++){
		//for (String[] line : source){
			String[] line = source[i];
			int tempKey = Integer.parseInt(line[0]);
			int key = tempKey;
			int index = 0;
			
			infoMap.put(key, line);
			
		}
		
	}

	public HashMap<Integer, String[]> getInfoMap() {
		return infoMap;
	}

	public String[][] getSource() {
		return source;
	}
		
	
	
	
	public void generateDefault(CSVWriter writer) {

		ArrayList<String> headers = new ArrayList<>();
		headers.add("COLUMN");
		headers.add("HEADER NAME");
		headers.add("INFO FROM? (SECTION)");
		headers.add("VALUE KEY");
		headers.add("SPECIAL METHOD");
		headers.add("IF EMPTY");
		
		String[] arrWrite = new String[headers.size()];
		headers.toArray(arrWrite);
		writer.writeNext(arrWrite);

		for (int i = 1; i <= 45; i++) {
			
			ArrayList<String> column = new ArrayList<>();
			String headerName = "";
			String section = "";
			String valueKey = "";
			String specialMethod = "";
			String ifEmpty = "";
			
			switch (i){
				case 1: headerName="Prefix";section="1";valueKey ="Name Prefix";specialMethod ="";ifEmpty="";break;
				case 2: headerName="Surname";section="3";valueKey ="Family Name/Surname";specialMethod ="";ifEmpty="";break;
				case 3: headerName="Full name";section="1";valueKey ="Full Name";specialMethod ="";ifEmpty="";break;
				case 4: headerName="Interview Date";section="1";valueKey ="";specialMethod ="pending";ifEmpty="";break;
				case 5: headerName="Mode of interview";section="1";valueKey ="";specialMethod ="";ifEmpty="";break;
				case 6: headerName="Application Date";section="1";valueKey ="Application Date";specialMethod ="";ifEmpty="";break;
				case 7: headerName="Application Nbr";section="1";valueKey ="Application Number";specialMethod ="";ifEmpty="";break;
				case 8: headerName="System ID";section="1";valueKey ="System ID";specialMethod ="";ifEmpty="";break;
				case 9: headerName="Defer from prev yr?";section="1";valueKey ="";specialMethod ="N";ifEmpty="";break;
				case 10: headerName="Interviewed";section="1";valueKey ="";specialMethod ="N";ifEmpty="";break;
				case 11: headerName="Application outcome";section="1";valueKey ="";specialMethod ="pending interview";ifEmpty="";break;
				case 12: headerName="Response Deadline/Follow up date";section="1";valueKey ="";specialMethod ="";ifEmpty="";break;
				case 13: headerName="Applicant's Reply";section="1";valueKey ="";specialMethod ="pending interview";ifEmpty="";break;	
				case 14: headerName="GMAT, min score : 540";section="8";valueKey ="";specialMethod ="GMAT";ifEmpty="pending";break;
				
				case 15: headerName="Financial Gurantee";section="3";valueKey ="Singapore Citizen Status";specialMethod ="isSingaporean";ifEmpty="pending";break;
				case 16: headerName="Require IELTS/TOFEL ?";section="7";valueKey ="Undergraduate Medium of Instruction";specialMethod ="reqEngProficiency";ifEmpty="";break;
				case 17: headerName="IELTS/TOFEL, min score : 7/94";section="8";valueKey ="";specialMethod ="engProficiencyScore";ifEmpty="";break;
				case 18: headerName="Compulsory CEC?";section="7";valueKey ="Undergraduate Medium of Instruction";specialMethod ="compulsoryCEC";ifEmpty="pending";break;
				
				case 19: headerName="Prog Type";section="2";valueKey ="";specialMethod ="progType";ifEmpty="";break;
				case 20: headerName="Nationality";section="3";valueKey ="";specialMethod ="nationality";ifEmpty="";break;
				case 21: headerName="NRIC/FIN";section="3";valueKey ="NRIC/FIN";specialMethod ="";ifEmpty="pending";break;
				case 22: headerName="PP";section="3";valueKey ="Passport Number";specialMethod ="";ifEmpty="";break;
					
				case 23: headerName="FIN Type";section="3";valueKey ="Singapore Citizen Status";specialMethod ="isSingaporean";ifEmpty="pending";break;	
				case 24: headerName="FIN Expiry Date";section="3";valueKey ="Singapore Citizen Status";specialMethod ="isSingaporean";ifEmpty="pending";break;
				case 25: headerName="SOLAR NO.";section="3";valueKey ="Singapore Citizen Status";specialMethod ="isSingaporean";ifEmpty="pending";break;
				
				case 26: headerName="CV";section="1";valueKey ="";specialMethod ="0";ifEmpty="";break;
				case 27: headerName="Referee Forms";section="1";valueKey ="";specialMethod ="0";ifEmpty="";break;
				case 28: headerName="Tscript";section="1";valueKey ="";specialMethod ="0";ifEmpty="";break;
				case 29: headerName="Cert";section="1";valueKey ="";specialMethod ="0";ifEmpty="";break;
				case 30: headerName="BirthDate";section="3";valueKey ="Date of Birth";specialMethod ="";ifEmpty="";break;
				case 31: headerName="Age";section="3";valueKey ="Date of Birth";specialMethod ="age";ifEmpty="";break;
				case 32: headerName="Age group";section="3";valueKey ="Date of Birth";specialMethod ="ageGroup";ifEmpty="";break;
				case 33: headerName="Mobile";section="5";valueKey ="Mobile Number";specialMethod ="";ifEmpty="";break;
				case 34: headerName="Email";section="3";valueKey ="Email Address";specialMethod ="";ifEmpty="";break;
				case 35: headerName="Mailing Addr";section="4";valueKey ="";specialMethod ="mailingAddress";ifEmpty="";break;
				case 36: headerName="Certification";section="7";valueKey ="";specialMethod ="certification";ifEmpty="";break;
				case 37: headerName="School";section="7";valueKey ="";specialMethod ="bachelorSchool";ifEmpty="";break;
				case 38: headerName="Degree";section="7";valueKey ="";specialMethod ="bachelorDegree";ifEmpty="";break;
				case 39: headerName="Industry sector";section="14";valueKey ="Industry Code";specialMethod ="";ifEmpty="NA";break;
				case 40: headerName="Current Employment";section="14";valueKey ="Organisation";specialMethod ="";ifEmpty="NA";break;
				case 41: headerName="Current Position";section="14";valueKey ="Job Designation";specialMethod ="";ifEmpty="NA";break;
				case 42: headerName="Total Yrs work Exp";section="14";valueKey ="Total Years of Experience";specialMethod ="";ifEmpty="";break;
				case 43: headerName="Work exp";section="14";valueKey ="Total Years of Experience";specialMethod ="workingExpCategory";ifEmpty="";break;
				case 44: headerName="Source";section="16";valueKey ="";specialMethod ="processReferral";ifEmpty="";break;
				case 45: headerName="Financial Support";section="18";valueKey ="";specialMethod ="financialSupport";ifEmpty="";break;
			}
			
			column.add("" + i);
			column.add(headerName);
			column.add(section);
			column.add(valueKey);
			column.add(specialMethod);
			column.add(ifEmpty);
			
			arrWrite = new String[column.size()];

			column.toArray(arrWrite);
			writer.writeNext(arrWrite);
			

		}
	}

	
	
}
