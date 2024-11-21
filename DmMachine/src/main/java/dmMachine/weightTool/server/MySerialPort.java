package dmMachine.weightTool.server;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class MySerialPort {

	private final static List<String> data = new ArrayList<>();
	private final static int serialport_default_close_time = 5000;
	private final static int minimum_output_count = 3;
	private final static int response_read_wait_gap = 100;

	protected static String readSerialPort(String comPortName, int baudRate, int parity, int dataBits, int stopBits)
			throws Exception {
		SerialPort serialPort = null;
		String output = "0";
		boolean isSerialActive = true;
		try {
			serialPort = SerialPort.getCommPort(comPortName);
			serialPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
			serialPort.addDataListener(new SerialPortDataListener() {

				@Override
				public void serialEvent(SerialPortEvent event) {
					byte[] bytes = event.getReceivedData();
					String receivedData = new String(bytes, 0, bytes.length, StandardCharsets.UTF_8);
					if (vNull(receivedData).doubleValue() > 0) {
						data.add(receivedData);
					}
				}

				@Override
				public int getListeningEvents() {
					return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
				}
			});
			serialPort.openPort(serialport_default_close_time);
			int closeTime = serialport_default_close_time;
			while (serialPort.isOpen() && isSerialActive) {
				if (data.size() >= minimum_output_count) {
					isSerialActive = false;
				}
				if (isSerialActive) {
					Thread.sleep(response_read_wait_gap);
					closeTime -= response_read_wait_gap;
					if (closeTime <= 0) {
						isSerialActive = false;
					}
				}
			}
			
			if (data.size() > 0) {
				double myOutput = 0;
				for (String o : data) {
					myOutput += vNull(o).doubleValue();
				}
				myOutput /= data.size();
				output = vNull(myOutput).toString();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (serialPort != null) {
					serialPort.closePort();
					serialPort.removeDataListener();
					serialPort = null;
				}
			} catch (Exception e2) {
			}
		}

		return output;
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

}
