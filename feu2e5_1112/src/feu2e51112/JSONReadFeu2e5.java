package feu2e51112;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONReadFeu2e5 {

	public static void main(String[] args) {
		try {
				
			FileReader reader = new FileReader("orarendFEU2E5.json");
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);

			JSONObject root = (JSONObject) jsonObject.get("GG_orarend");
			JSONArray lessons = (JSONArray) root.get("ora");
			
			System.out.println("Órarend: Mérnökinformatika 2024\n");
			
			for (int i = 0; i < lessons.size(); i++) {
				JSONObject lesson = (JSONObject) lessons.get(i);
				JSONObject time = (JSONObject) lesson.get("idopont");
				System.out.println("Tárgy: " + lesson.get("targy"));
				System.out.println("Idõpont: " + time.get("nap") +"-"+ time.get("tol") +"-"+ time.get("ig"));
				System.out.println("Helyszín: " + lesson.get("helyszin"));
				System.out.println("Oktató: " + lesson.get("oktato"));
				System.out.println("Szak: "+lesson.get("szak")+"\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
