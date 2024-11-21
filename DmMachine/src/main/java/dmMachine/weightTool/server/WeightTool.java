package dmMachine.weightTool.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import dmMachine.model.MachineEnum;
import dmMachine.weightTool.model.DirectWeighmentEnum;
import dmMachine.weightTool.model.ThirdPartyWeighmentEnum;

@Component
public class WeightTool {

	public static String runMachine(String machineName, HashMap<String, String> data)throws Exception {
		try {
			MachineEnum machineEnum = MachineEnum.getViaName(machineName);
			if (machineEnum != null) {
				switch (machineEnum) {
				case WEIGHMENT_DIRECT: {
					return runDirect(data);
				}
				case WEIGHMENT_VIA_THIRD_PARTY_APP: {
					return runViaThirdParty(data);
				}
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return "0";
	}

	private static String runDirect(HashMap<String, String> data) throws Exception {
		String com_port_name = "";
		int baud_rate = 0, parity = 0, data_bits = 0, stop_bit = 0;
		try {
			if (data.containsKey(DirectWeighmentEnum.COM_PORT_NAME.getName())) {
				com_port_name = data.get(DirectWeighmentEnum.COM_PORT_NAME.getName());
			}
			if (data.containsKey(DirectWeighmentEnum.BAUD_RATE.getName())) {
				baud_rate = iNull(data.get(DirectWeighmentEnum.BAUD_RATE.getName()));
			}
			if (data.containsKey(DirectWeighmentEnum.PARITY.getName())) {
				parity = iNull(data.get(DirectWeighmentEnum.PARITY.getName()));
			}
			if (data.containsKey(DirectWeighmentEnum.DATA_BITS.getName())) {
				data_bits = iNull(data.get(DirectWeighmentEnum.DATA_BITS.getName()));
			}
			if (data.containsKey(DirectWeighmentEnum.STOP_BIT.getName())) {
				stop_bit = iNull(data.get(DirectWeighmentEnum.STOP_BIT.getName()));
			}
			return MySerialPort.readSerialPort(com_port_name, baud_rate, parity, data_bits, stop_bit);
		} catch (Exception e) {
			throw e;
		}
	}

	private static String runViaThirdParty(HashMap<String, String> data) throws Exception {
		String exePath = "", filePath = "";
		try {
			if (data.containsKey(ThirdPartyWeighmentEnum.EXE_PATH.getName())) {
				exePath = data.get(ThirdPartyWeighmentEnum.EXE_PATH.getName());
			}
			if (data.containsKey(ThirdPartyWeighmentEnum.FILE_PATH.getName())) {
				filePath = data.get(ThirdPartyWeighmentEnum.FILE_PATH.getName());
			}
			return getWeight(exePath, filePath);
		} catch (Exception e) {
			throw e;
		}
	}

	private static String getWeight(String exePath, String filePath) throws Exception {
		double weight = 0;
		try {
			if (isValidFile(exePath)) {
				if (executeExe(exePath) == 0) {
					if (isValidFile(filePath)) {
						weight = vNull(readTextFileData(filePath, true)).doubleValue();
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return vNull(weight).toString();
	}

	private static boolean isValidFile(String path) throws Exception {
		if (!xNull(path).trim().equalsIgnoreCase("")) {
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				return true;
			}
		}
		return false;
	}

	private static String readTextFileData(String txtFilePath, boolean isDelete) throws Exception {
		File file = null;
		try {
			Path path = Paths.get(txtFilePath);
			file = path.toFile();
			return new String(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (file != null && file.exists() && isDelete) {
					file.deleteOnExit();
				}
			} catch (Exception e) {
			}
		}
	}

	private static int executeExe(String exePath) throws Exception {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(exePath);
			Process process = processBuilder.start();
			return process.waitFor();
		} catch (Exception e) {
			throw e;
		}
	}

	private static String xNull(Object value) {
		if (value == null) {
			value = "";
		}

		return (String) value;
	}

	private static Double vNull(Object value) {
		if (value == null) {
			value = 0;
		}
		if (String.valueOf(value).equalsIgnoreCase("")) {
			value = 0;
		}
		return Double.valueOf(value.toString());
	}

	private static Integer iNull(Object value) {
		if (value == null) {
			value = 0;
		}
		if (String.valueOf(value).equalsIgnoreCase("")) {
			value = 0;
		}
		return Integer.valueOf(value.toString());
	}

}
