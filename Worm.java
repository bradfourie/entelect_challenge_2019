import java.util.ArrayList;

public class Worm {
	private int motivation;
	private ArrayList<Day> schedule = new ArrayList();
	private String employmentStatus;
	private int busyCounter;
	private int offCounter;
	private boolean hasWorked;
	
	public Worm() {
		this.motivation = 15;
		this.employmentStatus = null;
		this.busyCounter = 0;
		hasWorked = false;
	}
	
	public boolean hasWorked() {
		return hasWorked;
	}
	
	public void setDay(Day day) {
		schedule.add(day);
	}
	
	public int getMotivation() {
		return motivation;
	}
	
	public void removeMotivation() {
		motivation--;
	}
	
	public ArrayList<Day> getSchedule(){
		return schedule;
	}
	
	public void setBusy(int busyCounter) {
		this.busyCounter = busyCounter;
		motivation--;
		hasWorked = true;
	}
	public void setOff(int offCounter) {
		this.offCounter = offCounter;
		motivation++;
	}
	
	public void decrementBusyCounter() {
		this.busyCounter--;
	}
	public void decrementOffCounter() {
		this.offCounter--;
	}
	
	public boolean isBusy() {
		if(busyCounter == 0) {
			return false;
		} else {
			return true;
		}
	}
	public boolean isOff() {
		if(offCounter == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean needWeekend() {
		boolean needWeekend = true;
		//first check if worm has even worked for 5 days
		if(schedule.size() > 2) {
			//check the last 5 elements
			for(int i = schedule.size()-1; i >= schedule.size()-1; i--) {
				Day day = schedule.get(i);
				if(!day.isWorkDay()) {
					needWeekend = false;
				}
			}
		} else {
			needWeekend = false;
		}
		
		if(motivation == 0) {
			needWeekend = true;
		}
		
		return needWeekend;
	}
	
	public boolean needFreeShift() {
		if(motivation == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getPreviousTask() {
		
		Day lastDay = schedule.get(schedule.size()-1);
		
		
		if(lastDay.getNight() != null) {
			return lastDay.getNight();
		} else {
			if(lastDay.getAfternoon() != null) {
				return lastDay.getAfternoon();
			} else {
				return lastDay.getMorning();
			}
		}
	}

}
