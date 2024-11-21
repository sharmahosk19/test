package dmMachine.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dmMachine.server.MachineApp;

@RestController
@ComponentScan("com.bd.service")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class AppController {

	@GetMapping("/fetchMachineDetail")
	public String fetchMachineDetail() {
		return MachineApp.fetchMachineDetail();
	}

	@GetMapping("/fetchMachineNameDetail")
	public String fetchMachineNameDetail(@RequestParam("machineName") String machineName) {
		return MachineApp.fetchMachineNameDetail(machineName);
	}

	@GetMapping("/fetchButtonDetail")
	public String fetchButtonDetail(@RequestParam("machineName") String machineName) {
		return MachineApp.fetchButtonDetail(machineName);
	}

	@GetMapping("/fetchMachineName")
	public String fetchMachineName() {
		return MachineApp.fetchMachineName();
	}

	@PostMapping("/writeMachineInfo")
	public void writeMachineInfo(@RequestParam("machineName") String machineName, @RequestBody String jsonString) {
		MachineApp.writeMachineInfoInJson(machineName, jsonString);
	}

	@PostMapping("/writeButtonInfo")
	public void writeButtonInfo(@RequestParam("machineName") String machineName,
			@RequestParam("buttonCode") String buttonCode) {
		MachineApp.writeButtonInfoInJson(machineName, buttonCode);
	}
	
	@PostMapping("/testMachine")
	public String testMachine(@RequestParam("machineName") String machineName, @RequestBody String jsonString) {
		return MachineApp.testMachine(machineName, jsonString);
	}
	
	@GetMapping("/runMachine")
	public String runMachine(@RequestParam("buttonCode") String buttonCode) {
		return MachineApp.runMachine(buttonCode);
	}

	@DeleteMapping("/deleteMachineInfo/{machineName}")
	public void deleteMachineInfo(@PathVariable String machineName) {
		MachineApp.deleteMachineInfoInJson(machineName);
	}
	
	@DeleteMapping("/deleteButtonInfo/{buttonCode}")
	public void deleteButtonInfo(@PathVariable String buttonCode) {
		MachineApp.deleteButtonInfoInJson(buttonCode);
	}
	
}
