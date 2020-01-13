
public class Day {
	
	String morning;
	String afternoon;
	String night;

	public Day() {
		
	}

	public void setMorning(String morning) {
		this.morning = morning;
	}
	public void setAfternoon(String afternoon) {
		this.afternoon = afternoon;
	}
	public void setNight(String night) {
		this.night = night;
	}
	
	public String getMorning() {
		return morning;
	}
	public String getAfternoon() {
		return afternoon;
	}
	public String getNight() {
		return night;
	}
	
	public boolean isWorkDay() {
		boolean isWorkDay = true;
		if(morning == null && afternoon == null && night == null) {
			isWorkDay = false;
		}
		return isWorkDay;
	}
}
