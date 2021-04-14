package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDetails {

	private final String directory;

	private final String name;

	private final String type;

	public FileDetails(@JsonProperty("directory") String directory, @JsonProperty("name") String name,
			@JsonProperty("type") String type) {
		super();
		this.directory = directory;
		this.name = name;
		this.type = type;
	}

	public String getDirectory() {
		return directory;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

}
