package dmMachine.weightTool.model;

public enum ThirdPartyWeighmentEnum {

	EXE_PATH("EXE Path", true), FILE_PATH("File Path", true);

	private final String name;
	private final boolean mandatory;

	public String getName() {
		return name;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	private ThirdPartyWeighmentEnum(String name, boolean mandatory) {
		this.name = name;
		this.mandatory = mandatory;
	}

	public static ThirdPartyWeighmentEnum getViaName(String name) {
		if (name != null) {
			for (ThirdPartyWeighmentEnum directWeighmentEnum : ThirdPartyWeighmentEnum.values()) {
				if (directWeighmentEnum.getName().equalsIgnoreCase(name)) {
					return directWeighmentEnum;
				}
			}
		}
		return null;

	}

}
