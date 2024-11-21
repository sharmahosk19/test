package dmMachine.server;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dmMachine.model.MachineEnum;
import dmMachine.weightTool.model.DirectWeighmentEnum;
import dmMachine.weightTool.model.ThirdPartyWeighmentEnum;
import dmMachine.weightTool.server.JsonHandler;
import dmMachine.weightTool.server.WeightTool;

public class MachineApp {

	private static final String MACHINE_FILE_PATH = "C:\\dmMachine\\45411\\541.json";
	private static final String BUTTON_FILE_PATH = "C:\\dmMachine\\45411\\452.json";

	public static String fetchMachineNameDetail(String machineName) {
		HashMap<String, String> hm = new HashMap<>();
		try {
			MachineEnum machineEnum = MachineEnum.getViaName(machineName);
			if (machineEnum != null) {
				String jsonData = JsonHandler.readJsonStringFromFile(MACHINE_FILE_PATH, machineEnum.getName());
				if (jsonData != null && !jsonData.trim().equalsIgnoreCase("")) {
					Type listType = new TypeToken<HashMap<String, String>>() {
					}.getType();
					hm = new Gson().fromJson(jsonData, listType);
				} else {
					if (machineEnum == MachineEnum.WEIGHMENT_DIRECT) {
						for (DirectWeighmentEnum directWeighmentEnum : DirectWeighmentEnum.values()) {
							hm.put(directWeighmentEnum.getName(), "");
						}
					} else if (machineEnum == MachineEnum.WEIGHMENT_VIA_THIRD_PARTY_APP) {
						for (ThirdPartyWeighmentEnum thirdPartyWeighmentEnum : ThirdPartyWeighmentEnum.values()) {
							hm.put(thirdPartyWeighmentEnum.getName(), "");
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}

		return new Gson().toJson(hm);
	}

	public static String fetchButtonDetail(String machineName) {
		List<String> lst = new ArrayList<>();
		try {
			MachineEnum machineEnum = MachineEnum.getViaName(machineName);
			if (machineEnum != null) {
				Map<String, String> map = JsonHandler.readJsonFromFile(BUTTON_FILE_PATH);
				if (map != null) {
					for (String key : map.keySet()) {
						if (map.get(key).equalsIgnoreCase(machineEnum.getName())) {
							lst.add(key);
						}
					}
				}
			}

		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return new Gson().toJson(lst);
	}

	public static String fetchMachineDetail() {
		HashMap<String, HashMap<String, String>> machineDetail = new HashMap<>();
		try {
			for (MachineEnum machineEnum : MachineEnum.values()) {
				String jsonData = JsonHandler.readJsonStringFromFile(MACHINE_FILE_PATH, machineEnum.getName());
				if (jsonData != null && !jsonData.trim().equalsIgnoreCase("")) {
					Type listType = new TypeToken<HashMap<String, String>>() {
					}.getType();
					machineDetail.put(machineEnum.getName(), new Gson().fromJson(jsonData, listType));
				}
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return new Gson().toJson(machineDetail);
	}

	public static String fetchMachineName() {
		List<String> lst = new ArrayList<>();
		try {
			for (MachineEnum machineEnum : MachineEnum.values()) {
				lst.add(machineEnum.getName());
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return new Gson().toJson(lst);
	}

	public static void writeMachineInfoInJson(String machineName, String jsonData) {
		try {
			MachineEnum machineEnum = MachineEnum.getViaName(machineName);
			if (machineEnum != null) {
				JsonHandler.writeToJsonFile(MACHINE_FILE_PATH, machineEnum.getName(), jsonData);
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}

	public static void writeButtonInfoInJson(String machineName, String buttonCode) {
		try {
			MachineEnum machineEnum = MachineEnum.getViaName(machineName);
			if (machineEnum != null) {
				JsonHandler.writeToJsonFile(BUTTON_FILE_PATH, buttonCode, machineEnum.getName());
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}

	public static void deleteMachineInfoInJson(String machineName) {
		try {
			Map<String, String> map = JsonHandler.readJsonFromFile(BUTTON_FILE_PATH);
			if (map != null) {
				for (String buttonCode : map.keySet()) {
					if (map.get(buttonCode).equalsIgnoreCase(machineName)) {
						JsonHandler.deleteJsonEntry(BUTTON_FILE_PATH, buttonCode);
					}
				}
			}
			JsonHandler.deleteJsonEntry(MACHINE_FILE_PATH, machineName);
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}

	public static void deleteButtonInfoInJson(String buttonCode) {
		try {
			JsonHandler.deleteJsonEntry(BUTTON_FILE_PATH, buttonCode);
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}

	}

	public static String testMachine(String machineName, String jsonData) {
		try {
			MachineEnum machineEnum = MachineEnum.getViaName(machineName);
			if (machineEnum != null) {
				Type listType = new TypeToken<HashMap<String, String>>() {
				}.getType();
				HashMap<String, String> map = new Gson().fromJson(jsonData, listType);
				if (map != null) {
					return WeightTool.runMachine(machineEnum.getName(), map);
				}
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return "0";
	}

	public static String runMachine(String buttonCode) {
		try {
			String machineName = JsonHandler.readJsonStringFromFile(BUTTON_FILE_PATH, buttonCode);
			MachineEnum machineEnum = MachineEnum.getViaName(machineName);
			String jsonData = JsonHandler.readJsonStringFromFile(MACHINE_FILE_PATH, machineEnum.getName());
			return testMachine(machineName, jsonData);
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return "0";

	}

}
