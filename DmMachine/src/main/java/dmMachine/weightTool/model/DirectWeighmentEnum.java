package dmMachine.weightTool.model;

public enum DirectWeighmentEnum {

	COM_PORT_NAME("COM Port Name", true), BAUD_RATE("Baud Rate", true), PARITY("Parity", true),
	DATA_BITS("Data Bits", true), STOP_BIT("Stop Bit", true);

	private final String name;
	private final boolean mandatory;

	public String getName() {
		return name;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	private DirectWeighmentEnum(String name, boolean mandatory) {
		this.name = name;
		this.mandatory = mandatory;
	}

	public static DirectWeighmentEnum getViaName(String name) {
		if (name != null) {
			for (DirectWeighmentEnum directWeighmentEnum : DirectWeighmentEnum.values()) {
				if (directWeighmentEnum.getName().equalsIgnoreCase(name)) {
					return directWeighmentEnum;
				}
			}
		}
		return null;

	}

}
