package dmMachine.weightTool.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import dmMachine.security.crypto.AESCryptoService;

public class JsonHandler {

	public static void writeToJsonFile(String FILE_PATH, String key, String jsonString) throws Exception {
		Map<String, String> jsonMap = readJsonFromFile(FILE_PATH);
		jsonMap.put(key, jsonString);
		FileWriter fileWriter = new FileWriter(FILE_PATH);
		fileWriter.write(AESCryptoService.encrypt(new Gson().toJson(jsonMap)));
		fileWriter.close();
	}

	public static String readJsonStringFromFile(String FILE_PATH, String key) throws Exception {
		String jsonString = "";
		Map<String, String> jsonMap = readJsonFromFile(FILE_PATH);
		jsonString = jsonMap.get(key);
		return jsonString;
	}

	// Function to read JSON from file
	@SuppressWarnings("unchecked")
	public static Map<String, String> readJsonFromFile(String FILE_PATH) throws Exception {
		Map<String, String> jsonMap = new HashMap<>();
		try {
			File file = new File(FILE_PATH);
			if (!file.exists()) {
				File parentDir = file.getParentFile();
				if(parentDir!=null && !parentDir.exists()) {
					if(!parentDir.mkdirs()) {
						new Exception("Root Directory Not Found!"+FILE_PATH);
					}
				}
				if(!file.createNewFile()) {
					new Exception("Root Directory Not Found!" +FILE_PATH);
				}
				
				try(FileWriter fileWriter = new FileWriter(FILE_PATH)){
					fileWriter.write(AESCryptoService.encrypt("{}"));
				}
			}
			BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			bufferedReader.close();
			String decJsonContent = AESCryptoService.decrypt(stringBuilder.toString());
			jsonMap = new Gson().fromJson(decJsonContent, HashMap.class);
		} catch (Exception e) {
			throw e;
		}
		return jsonMap;
	}

	public static void deleteJsonEntry(String FILE_PATH, String key) throws Exception {
		try {
			Map<String, String> jsonMap = readJsonFromFile(FILE_PATH);
			if (jsonMap.containsKey(key)) {
				jsonMap.remove(key);
				FileWriter fileWriter = new FileWriter(FILE_PATH);
				fileWriter.write(new Gson().toJson(jsonMap));
				fileWriter.close();
				System.out.println("JSON entry deleted successfully.");
			} else {
				System.out.println("Key not found in JSON.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
