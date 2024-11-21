package dmMachine.model;

public enum MachineEnum {

	WEIGHMENT_DIRECT("Direct Weighment"),
	WEIGHMENT_VIA_THIRD_PARTY_APP("Third Party Weighment");

	private final String name;

	public String getName() {
		return name;
	}

	private MachineEnum(String name) {
		this.name = name;
	}

	public static MachineEnum getViaName(String name) {
		if (name != null) {
			for (MachineEnum machineEnum : MachineEnum.values()) {
				if (machineEnum.getName().equalsIgnoreCase(name)) {
					return machineEnum;
				}
			}
		}
		return null;
	}

}
