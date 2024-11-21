package dmMachine.scheduler;

import java.nio.charset.StandardCharsets;
import java.util.Timer;

import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.annotation.PostConstruct;

@Component
public class WeighmentScheduler {
	
	@SuppressWarnings("unused")
	private String com_port_name="", baud_rate="", parity="", data_bits="", stop_bit = "";

	@PostConstruct
	public void init() {
		@SuppressWarnings("unused")
		Timer timer = new Timer();
		
		try {
//			String jsonStr = JsonHandler.readJsonStringFromFile("1");
			String jsonStr = "";
			JsonObject json = JsonParser.parseString(jsonStr).getAsJsonObject();
			JsonObject machinInfo = null;
			if (json.has("machineInfo")) {
				machinInfo = json.get("machineInfo").getAsJsonObject();
				if (machinInfo.has("COM Port Name")) {
					com_port_name = machinInfo.get("COM Port Name").getAsString();
				}
				if (machinInfo.has("Baud Rate")) {
					baud_rate = machinInfo.get("Baud Rate").getAsString();
				}
				if (machinInfo.has("Parity")) {
					parity = machinInfo.get("Parity").getAsString();
				}
				if (machinInfo.has("Data Bits")) {
					data_bits = machinInfo.get("Data Bits").getAsString();
				}
				if (machinInfo.has("Stop Bit")) {
					stop_bit = machinInfo.get("Stop Bit").getAsString();
				}
			};
			
			SerialPort serialPort= SerialPort.getCommPort(com_port_name);
			serialPort.setBaudRate(0);
			serialPort.setParity(0);
			serialPort.setNumDataBits(0);
			serialPort.setNumStopBits(0);
			serialPort.setBaudRate(0);
			serialPort.addDataListener(new SerialPortDataListener() {
				
				@Override
				public void serialEvent(SerialPortEvent event) {
					byte[] bytes=event.getReceivedData();
					@SuppressWarnings("unused")
					String readBytes=new String(bytes,0,bytes.length,StandardCharsets.UTF_8);
				}
				
				@Override
				public int getListeningEvents() {
					// TODO Auto-generated method stub
					return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
				}
			});
			serialPort.openPort()
;			
//			TimerTask timerTask = new TimerTask() {
//
//				@Override
//				public void run() {
//					
//					System.out.println("com_port_name "+com_port_name);
//					System.out.println("baud_rate "+baud_rate);
//					System.out.println("Parity "+parity);
//					System.out.println("Data Bits "+data_bits);
//					System.out.println("Stop Bit "+stop_bit);
//
//				}
//			};
//			timer.scheduleAtFixedRate(timerTask, 0, 5000);
		} catch (Exception e) {

		}

	}

}
