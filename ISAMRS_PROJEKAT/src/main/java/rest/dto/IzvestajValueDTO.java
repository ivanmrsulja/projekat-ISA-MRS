package rest.dto;

public class IzvestajValueDTO {

	private String timePeriod;
	private int value;

	public IzvestajValueDTO() {}

	public IzvestajValueDTO(String timePeriod, int value) {
		super();
		this.timePeriod = timePeriod;
		this.value = value;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "IzvestajPregledValueDTO [timePeriod=" + timePeriod + ", value=" + value + "]";
	}

}
