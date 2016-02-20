/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theparser;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author jeremyongts92
 */
public class DisplayHandler {
	
	public static String processReferral(HashMap<String,String> infoMap){
		
		Iterator<String> iter = infoMap.keySet().iterator();
		String result = "";
		boolean moreThanOne = false;
		
		while (iter.hasNext()){
			String key = iter.next();
			
			if (!key.equals("Personal Statement") && !key.equals("Source of Information about SMU")){
				if (key.contains("Other sources")){
					if (moreThanOne){
						result += "\r\n";
					}
					result += infoMap.get(key);
					
					moreThanOne = true;
				} else {
					if (moreThanOne){
						result += "\r\n";
					}
					result += key;
					moreThanOne = true;
				}
			}
			
		}
		
		return result;
	}
	
	public static String GMATgenerator(HashMap<String, String> infoMap, String path) {
		if (infoMap.get("GMAT") != null) {
			if (infoMap.get("Total") != null) {
				String totalInfo[] = infoMap.get("Total").split(",");
				return totalInfo[1];
			} else {
				return "pending - " + infoMap.get("Date of Registered GMAT");
			}
		} else if (infoMap.get("GRE") != null) {
			DecimalFormat format = new DecimalFormat("#0");

			String[] quantitativeInfo = infoMap.get("Quantitative").split(",");
			int quantitative = Integer.parseInt(quantitativeInfo[1]);
			String[] verbalInfo = infoMap.get("Verbal").split(",");
			int verbal = Integer.parseInt(verbalInfo[1]);

			int result = (int) (-2080.75 + (6.38 * verbal) + (10.62 * quantitative));
			int oneDigit = result % 10;
			if (oneDigit >= 5) {
				result += 10 - oneDigit;
			} else {
				result -= oneDigit;
			}
			return format.format(result);
		} else {
			return null;
		}

	}
	
	public static String generateProgType(HashMap<String, String> infoMap) {

		String choice = infoMap.get("Choice");
		String[] choiceArr = choice.split(",");
		String progType = choiceArr[2];
		if (progType.equals("Full-Time")) {
			return "FT";
		} else if (progType.equals("Part-Time")) {
			return "PT";
		}
		
		return "";

	}
	
	public static String isSingaporean(HashMap<String, String> infoMap, String valueKey) {

		String status = infoMap.get(valueKey);
		if (status.contains("Singapore") || status.contains("SPR")){
			return "NA";
		} else {
			return null;
		}

	}

	public static String engProficiency(HashMap<String, String> infoMap) {

		String toefl = infoMap.get("TOEFL");
		String ielts = infoMap.get("IELTS");
		String engProf = "";
		boolean occurred = false;
		if (toefl != null) {
			String[] testDetails = toefl.split(",");
			engProf += "TOEFL - " + testDetails[2];
			occurred = true;

		}
		if (ielts != null) {
			if (occurred) {
				engProf += "\r\n";
			}
			String[] testDetails = ielts.split(",");
			engProf += "IELTS - " + testDetails[1];
		}

		return engProf;

	}
	
	public static String reqEngProficiency(HashMap<String,String> infoMap, String valueKey){
		
		
		String language = infoMap.get(valueKey).toLowerCase();
								if (language.equals("english")){
									return "N";
								} else {
									return "Y";
								}
	}
	public static String compulsoryCEC(HashMap<String,String> infoMap, String valueKey){
		
		
		String language = infoMap.get(valueKey).toLowerCase();
								if (language.equals("english")){
									return "NA";
								} else {
									return null;
								}
	}
	
	public static String parseFinanceSupport(HashMap<String,String> infoMap){
		Set keyset = infoMap.keySet();
		
		Iterator<String> iter = keyset.iterator();
		
		String result = "";
	
		if (iter.hasNext()){
			String next = iter.next();
			if (next.contains("Others")){
				result += "Others - " + infoMap.get(next);
						
			} else {
				result += next;
			}
		}
		
		while (iter.hasNext()){
					String next = iter.next();
					
					if (next.contains("Others")){
						result += "\r\nOthers - " + infoMap.get(next);
						
					} else {
						result += "\r\n" + next;
					}
				}
		
		return result;
	}
	
	
	public static String getBachelor(HashMap<String,String> infoMap){
		String result = "";
		String condition = "Degree";
		String condition2 = "Area of Study";
		String condition3 = "Major";
			
		int index = 0;
		boolean atLeastOne = false;
		//System.out.println(infoMap.get("GPA or Equivalent"));
		while (infoMap.get(condition) != null){
			String degreeName = infoMap.get(condition);
			
			//System.out.println("condition:" + condition + ",degreeName:" + degreeName);
			
			if (degreeName.contains("BACHELOR")){
				result += degreeName;
				result += ", " + infoMap.get(condition2);
				result += ", " + infoMap.get(condition3);
				if (atLeastOne){
					result += "\r\n ";
				}
				atLeastOne = true;
			}
			condition = "Degree" + "alt" + index;
			condition2 = "Area of Study" + "alt" + index;
			condition3 = "Major" + "alt" + index;
			index++;
			
		}
		return result;
	}
	
		public static String getBachelorSchool(HashMap<String,String> infoMap){
		
		String result = "";
		String condition = "Degree";
		String bachelorSchool = "College / University";
		
		int index = 0;
		boolean atLeastOne = false;
		
		while (infoMap.get(condition) != null){
			String degreeName = infoMap.get(condition);
			
			if (degreeName.contains("BACHELOR")){

				infoMap.put("Bachelor School", infoMap.get(bachelorSchool));
				if (atLeastOne){
					result += "\r\n ";
				}
				atLeastOne = true;
			}
			
			
			condition = "Degree" + "alt" + index;
			bachelorSchool = "College / University" + "alt" + index;
			index++;
			
		}
		
		return result;
	}
	
	
	public static String calculateWorkingExp (String workingExp){
		
		int num = Integer.parseInt(workingExp);
		
		if (num < 2){
			return "< 2 yr";
		} else if (num < 5){
			return "2 to 5 yr";
		} else if (num < 10){
			return "5 to 10 yr";
		} else {
			return "> 10 yr";
		}
	}
	
	public static String generateSources (HashMap<String,String> infoMap){
		String result = "";
		
		
		Iterator<String> iter = infoMap.keySet().iterator();
		ArrayList<String> storage = new ArrayList<>();
		
		while (iter.hasNext()){
			String s = iter.next();
			storage.add(s);
		}
		for (int i = 1; i < storage.size() - 1; i++) { //skip first and last
			result += storage.get(i);
			
			if (i != storage.size() - 2){ //if not the last one, add comma
				result += ","; 
			}
		}
		return result;
	}
	
	public static String parseAddress(HashMap<String,String> info4){
		
		String result = "";
		
		
		String line1 = info4.get("Address 1alt0");
		String line2 = info4.get("Address 2alt0");
		String line3 = info4.get("Address 3alt0");
		String line4 = info4.get("Address 4alt0");
		
		//There are two kinds of form, hence this
		if (line1 == null){
			
			line1 = info4.get("House / Block Noalt0");
			line2 = info4.get("Street Namealt0");
			line3 = info4.get("Unit Noalt0");
			line4 = info4.get("Building Namealt0");
			
			
		}
		
		result += line1;
		if (line2 != null && !line2.isEmpty()){
			result += ", " + line2;
		}
		if (line3 != null && !line3.isEmpty()){
			result += "\r\n" + line3;
		}
		if (line4 != null && !line4.isEmpty()){
			result += "\r\n" + line4;
		}
		
		result += "\r\n";
		String city = info4.get("City");
		if (city != null){
			result += city + ", ";
		}
		
		result += info4.get("Postal Code") + ", " + info4.get("Countryalt0");
		
		
		
		return result;
	}
	
	public static String calculateAge(String birthdate) {

		DateFormat fmt = new SimpleDateFormat("dd-MMM-yy");
		Date dobDate = new Date();
		
		try {
			dobDate = fmt.parse(birthdate);
		} catch (ParseException e){
			System.out.println("parse exception");
		}
		
		Calendar dob = Calendar.getInstance();
		dob.setTime(dobDate);  
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		
		// calculation includes month and date
//		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
//			age--;
//			
//		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
//				&& today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
//			age--;
//		}

		return "" + age;

	}
	
	public static String calculateAgeGroup(String age){
		
		int ageInt = Integer.parseInt(age);
		
		if (ageInt < 25){
			return "below 25";
		} else if (ageInt <= 30){
			return "25-30";
		} else if (ageInt <= 35){
			return "31-35";
		} else {
			return "above 35";
		}
		
	}
	
	
	
	
	public static String getCertification(HashMap<String,String> infoMap){
		
		String result = "";
		String condition = "Degree";
		int index = 0;
		boolean atLeastOne = false;
		
		while (infoMap.get(condition) != null){
			String degreeName = infoMap.get(condition);
			
			if (degreeName.contains("MASTER")){
				result += degreeName;
				if (atLeastOne){
					result += ", ";
				}
				atLeastOne = true;
			}
			
			
			condition = "Degree" + index;
			index++;
			
		}
		
		return result;
	}
	
}
