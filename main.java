import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	private static int numBioChems = 0;
	private static int numMechEngs = 0;
	private static int numPlumbers = 0;
	private static int numXenos = 0;

	public static ArrayList<Integer> listDomeRepairs = new ArrayList();
	public static ArrayList<Integer> listRoverRepairs = new ArrayList();
	public static ArrayList<Integer> listPlumbing = new ArrayList();
	public static ArrayList<Integer> listXenoClassif = new ArrayList();

	public static ArrayList<Worm> listWorms = new ArrayList();

	public static int numDays = 0;

	public static void main(String[] args) {
		parseTextFIle();

		printSchedules();

		init();

		yeet();

		yeetAgain();

		yeet();

		printGrid();

		printMoves();

	}

	public static void parseTextFIle() {
		File txtfile = new File("map_4.input");

		try {
			Scanner sc = new Scanner(txtfile);
			int lineCounter = 0;

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				if (lineCounter == 0) {
					readFirstLine(line);
				} else if (lineCounter > 0) {
					readOtherLine(line);
				}

				lineCounter++;
			}
			sc.close();

		} catch (FileNotFoundException fnfe) {

		}
	}

	public static void readFirstLine(String line) {
		// char[] row = line.toCharArray();
		String[] row = line.split(",");
		int argnum = 0;

		for (int i = 0; i < line.length(); i++) {
			argnum++;
			if (argnum == 1) {
				numBioChems = Integer.parseInt(row[i]);
			}
			if (argnum == 2) {
				numMechEngs = Integer.parseInt(row[i]);
			}
			if (argnum == 3) {
				numPlumbers = Integer.parseInt(row[i]);
			}
			if (argnum == 4) {
				numXenos = Integer.parseInt(row[i]);
			}
		}
		System.out.println("First line parsed: \nBioChemists: " + numBioChems + "\nMechEngs: " + numMechEngs
				+ "\nPlumbers: " + numPlumbers + "\nXenoBiologists: " + numXenos + "\n");
		System.out.println("");
	}

	public static void readOtherLine(String line) {
		String[] row = line.split(",");
		numDays++;

		if (row[0].equals("D")) {
			for (int i = 1; i < row.length; i++) {
				listDomeRepairs.add(Integer.parseInt(row[i]));
			}
		}
		if (row[0].equals("R")) {
			for (int i = 1; i < row.length; i++) {
				listRoverRepairs.add(Integer.parseInt(row[i]));
			}
		}
		if (row[0].equals("P")) {
			for (int i = 1; i < row.length; i++) {
				listPlumbing.add(Integer.parseInt(row[i]));
			}
		}
		if (row[0].equals("A")) {
			for (int i = 1; i < row.length; i++) {
				listXenoClassif.add(Integer.parseInt(row[i]));
			}
		}
	}

	public static void printSchedules() {
		System.out.println("Printing Schedule:");
		System.out.print("Dome Repairs: ");
		for (int i = 0; i < listDomeRepairs.size(); i++) {
			System.out.print(listDomeRepairs.get(i) + " ");
		}
		System.out.print("\nRover Repairs: ");
		for (int i = 0; i < listRoverRepairs.size(); i++) {
			System.out.print(listRoverRepairs.get(i) + " ");
		}
		System.out.print("\nPlumbing: ");
		for (int i = 0; i < listPlumbing.size(); i++) {
			System.out.print(listPlumbing.get(i) + " ");
		}
		System.out.print("\nXenoClassif: ");
		for (int i = 0; i < listXenoClassif.size(); i++) {
			System.out.print(listXenoClassif.get(i) + " ");
		}
		System.out.println("\n");
	}

	public static void init() {
		System.out.println("Creating Worms");
		for (int i = 0; i < numBioChems; i++) {
			listWorms.add(new BioChemist());
		}
		for (int i = 0; i < numMechEngs; i++) {
			listWorms.add(new MechEng());
		}
		for (int i = 0; i < numPlumbers; i++) {
			listWorms.add(new SpacePlumber());
		}
		for (int i = 0; i < numXenos; i++) {
			listWorms.add(new XenoBiologist());
		}
	}

	public static void yeet() {
		System.out.println("Running Solution");
		boolean run = true;
		int index = 0;

		while (run) {
			// for all worms:
			for (int i = 0; i < listWorms.size(); i++) {
				Worm currentWorm = listWorms.get(i);
				int priority = -1;
				// boolean available = false;

				Day day = new Day();
				currentWorm.setDay(day);

				// -1 none
				// 0 dome repairs
				// 1 rover repairs
				// 2 plumbing
				// 3 xeno classific

				if (currentWorm instanceof BioChemist) {
					priority = 0;
				} else if (currentWorm instanceof SpacePlumber) {
					priority = 1;
				} else if (currentWorm instanceof XenoBiologist) {
					priority = 2;
				} else if (currentWorm instanceof MechEng) {
					priority = 3;
				}

				if (!currentWorm.isBusy() && currentWorm.getMotivation() > 14 && !currentWorm.hasWorked() ) {
					// System.out.println("Morning, worm not busy");
					// check if worm needs a weekend
					if (currentWorm.needWeekend()) {
						currentWorm.setOff(6);
						day.setMorning("F");
					} else {
						// get all morning slots from that schedule
						int domeAvail = listDomeRepairs.get(index);
						int roverAvail = listRoverRepairs.get(index);
						int plumbAvail = listPlumbing.get(index);
						int classifAvail = listXenoClassif.get(index);

						// check if any specialty jobs available
						if (priority == 0 && domeAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setMorning("D");
							listDomeRepairs.set(index, (domeAvail - 1));
							currentWorm.setBusy(1);
						} else if (priority == 1 && roverAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setMorning("R");
							listRoverRepairs.set(index, (roverAvail - 1));
							currentWorm.setBusy(1);
						} else if (priority == 2 && plumbAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setMorning("P");
							listPlumbing.set(index, (plumbAvail - 1));
							currentWorm.setBusy(1);
						} else if (priority == 3 && classifAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setMorning("A");
							listXenoClassif.set(index, (classifAvail - 1));
							currentWorm.setBusy(1);
						} else {
							day.setMorning("F");
							currentWorm.setOff(1);
						}

						// check if no specialty jobs available

						
						
						  if(domeAvail != 0) { day.setMorning("D");
						  listDomeRepairs.set(index,(domeAvail-1)); currentWorm.setBusy(2); } else
						  if(roverAvail != 0) { day.setMorning("R"); listRoverRepairs.set(index,
						  (roverAvail-1)); currentWorm.setBusy(2); } else if(plumbAvail != 0) {
						  day.setMorning("P"); listPlumbing.set(index, (plumbAvail-1));
						  currentWorm.setBusy(2); } else if(classifAvail != 0) { day.setMorning("A");
						  listXenoClassif.set(index,(classifAvail-1)); currentWorm.setBusy(2); } else {
						  currentWorm.setOff(1); day.setMorning("F"); }
						
					}
				} else {
					// worm is busy with previous task, add to the worms schedule
					// get previous task
					/*
					 if(currentWorm.getPreviousTask() == null) { day.setMorning("F"); }else {
					 * day.setMorning(currentWorm.getPreviousTask()); }
					 */
					if (currentWorm.getPreviousTask() == null) {
						day.setMorning("F");
					} else {

						day.setMorning(currentWorm.getPreviousTask());
					}
				}

				if (currentWorm.isBusy()) {
					currentWorm.decrementBusyCounter();
				}
				if (currentWorm.isOff()) {
					currentWorm.decrementOffCounter();
				}

				/******************************************************************************************/

				if (!currentWorm.isBusy() && currentWorm.getMotivation() > 14  && !currentWorm.hasWorked() ) {
					// check if worm needs a weekend
					if (currentWorm.needWeekend()) {
						currentWorm.setOff(6);
						day.setAfternoon("F");
					} else {
						// get all morning slots from that schedule
						int domeAvail = listDomeRepairs.get(index);
						int roverAvail = listRoverRepairs.get(index);
						int plumbAvail = listPlumbing.get(index);
						int classifAvail = listXenoClassif.get(index);

						// check if any specialty jobs available
						if (priority == 0 && domeAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setAfternoon("D");
							listDomeRepairs.set(index, (domeAvail - 1));
							currentWorm.setBusy(1);
						} else if (priority == 1 && roverAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setAfternoon("R");
							listRoverRepairs.set(index, (roverAvail - 1));
							currentWorm.setBusy(1);
						} else if (priority == 2 && plumbAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setAfternoon("P");
							listPlumbing.set(index, (plumbAvail - 1));
							currentWorm.setBusy(1);
						} else if (priority == 3 && classifAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setAfternoon("A");
							listXenoClassif.set(index, (classifAvail - 1));
							currentWorm.setBusy(1);
						} else {
							day.setAfternoon("F");
							currentWorm.setOff(1);
						}

						// check if no specialty jobs available

						
						
						 if(domeAvail != 0) { day.setAfternoon("D"); listDomeRepairs.set(index,
						  (domeAvail-1)); currentWorm.setBusy(2); } else if(roverAvail != 0) {
						  day.setAfternoon("R"); listRoverRepairs.set(index, (roverAvail-1));
						  currentWorm.setBusy(2); } else if(plumbAvail != 0) { day.setAfternoon("P");
						  listPlumbing.set(index, (plumbAvail-1)); currentWorm.setBusy(2); } else
						  if(classifAvail != 0) { day.setAfternoon("A"); listXenoClassif.set(index,
						  (classifAvail-1)); currentWorm.setBusy(2); } else { currentWorm.setOff(1);
						  day.setAfternoon("F"); }
						 */

					}
				} else {
					// worm is busy with previous task, add to the worms schedule
					// get previous task
					/*
					 * if(currentWorm.getPreviousTask() == null) { day.setAfternoon("F"); }else {
					 * day.setAfternoon(currentWorm.getPreviousTask()); }
					 */
					if(currentWorm.getPreviousTask() == null) { day.setAfternoon("F"); }else {
						 day.setAfternoon(currentWorm.getPreviousTask()); }
				}

				if (currentWorm.isBusy()) {
					currentWorm.decrementBusyCounter();
				}
				if (currentWorm.isOff()) {
					currentWorm.decrementOffCounter();
				}

				// *********************************************************************************
				if (!currentWorm.isBusy() && currentWorm.getMotivation() > 14 && !currentWorm.hasWorked() ) {
					// System.out.println("Night, worm not busy");

					// check if worm needs a weekend
					if (currentWorm.needWeekend()) {
						currentWorm.setOff(6);
						day.setNight("F");
					} else {
						// get all morning slots from that schedule
						int domeAvail = listDomeRepairs.get(index);
						int roverAvail = listRoverRepairs.get(index);
						int plumbAvail = listPlumbing.get(index);
						int classifAvail = listXenoClassif.get(index);

						// check if any specialty jobs available
						if (priority == 0 && domeAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setNight("D");
							listDomeRepairs.set(index, (domeAvail - 1));
							currentWorm.setBusy(1);
							//currentWorm.removeMotivation();
						} else if (priority == 1 && roverAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setNight("R");
							listRoverRepairs.set(index, (roverAvail - 1));
							currentWorm.setBusy(1);
							//currentWorm.removeMotivation();
						} else if (priority == 2 && plumbAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setNight("P");
							listPlumbing.set(index, (plumbAvail - 1));
							currentWorm.setBusy(1);
							//currentWorm.removeMotivation();
						} else if (priority == 3 && classifAvail > 0) {
							// set worms job to that letter
							// decrement number of jobs available

							day.setNight("A");
							listXenoClassif.set(index, (classifAvail - 1));
							currentWorm.setBusy(1);
							//currentWorm.removeMotivation();
						} else {
							day.setNight("F");
							currentWorm.setOff(1);
						}

						// check if no specialty jobs available

						
						/*
						 * if(domeAvail != 0) { day.setNight("D"); listDomeRepairs.set(index,
						 * (domeAvail-1)); currentWorm.setBusy(2); } else if(roverAvail != 0) {
						 * day.setNight("R"); listRoverRepairs.set(index, (roverAvail-1));
						 * currentWorm.setBusy(2); } else if(plumbAvail != 0) { day.setNight("P");
						 * listPlumbing.set(index, (plumbAvail-1)); currentWorm.setBusy(2); } else
						 * if(classifAvail != 0) { day.setNight("A"); listXenoClassif.set(index,
						 * (classifAvail-1)); currentWorm.setBusy(2); } else { currentWorm.setOff(1);
						 * day.setNight("F"); }
						 */

					}
				} else {
					// worm is busy with previous task, add to the worms schedule
					// get previous task
					day.setNight("F");
				}

				if (currentWorm.isBusy()) {
					currentWorm.decrementBusyCounter();
					currentWorm.removeMotivation();
				}
				if (currentWorm.isOff()) {
					currentWorm.decrementOffCounter();
				}

				System.out.println("Executing schedule for worm: " + day.getMorning() + " " + day.getAfternoon() + " "
						+ day.getNight());

			}
			System.out.println("next day\n");

			index++;
			if ((numDays == index)) {
				run = false;
			}
		}

	}

	public static void completeShift(Worm worm) {
		// spending motivation

		// gaining motivation

	}

	public static void printGrid() {
		System.out.println("Printing jobs not completed");

		// for(int j=0; j<listWorms.size(); j++) {
		for (int i = 0; i < listDomeRepairs.size(); i++) {
			System.out.println("" + listDomeRepairs.get(i) + " " + listRoverRepairs.get(i) + " " + listPlumbing.get(i)
					+ " " + listXenoClassif.get(i));

		}
		System.out.println("\n\n");
	}

	public static void yeetAgain() {
		ArrayList<Integer> listDomeRepairsLeft = new ArrayList();
		ArrayList<Integer> listRoverRepairsLeft = new ArrayList();
		ArrayList<Integer> listPlumbingLeft = new ArrayList();
		ArrayList<Integer> listXenoClassifLeft = new ArrayList();

		for (int i = 0; i < listXenoClassif.size(); i++) {
			if (listDomeRepairs.get(i) != 0 || listRoverRepairs.get(i) != 0 || listPlumbing.get(i) != 0
					|| listXenoClassif.get(i) != 0) {
				listDomeRepairsLeft.add(listDomeRepairs.get(i));
				listRoverRepairsLeft.add(listRoverRepairs.get(i));
				listPlumbingLeft.add(listPlumbing.get(i));
				listXenoClassifLeft.add(listXenoClassif.get(i));
				System.out.println("" + listDomeRepairs.get(i) + " " + listRoverRepairs.get(i) + " "
						+ listPlumbing.get(i) + " " + listXenoClassif.get(i));
			}
		}

		listDomeRepairs = listDomeRepairsLeft;
		listRoverRepairs = listRoverRepairsLeft;
		listPlumbing = listPlumbingLeft;
		listXenoClassif = listXenoClassifLeft;

	}

	public static void printMoves() {
		System.out.println("Printing worm moves");

		Writer wr;
		try {
			wr = new FileWriter("output.txt");

			for (Worm w : listWorms) {
				ArrayList<Day> wormMoves = new ArrayList();
				wormMoves = w.getSchedule();

				for (int i = 0; i < wormMoves.size(); i++) {
					if (i == 0) {
						if (w instanceof BioChemist) {
							wr.write("B");
						}
						if (w instanceof MechEng) {
							wr.write("M");
						}
						if (w instanceof SpacePlumber) {
							wr.write("S");
						}
						if (w instanceof XenoBiologist) {
							wr.write("X");
						}
					}

					if (i != wormMoves.size() - 1) {
						System.out.print(wormMoves.get(i).getMorning() + "," + wormMoves.get(i).getAfternoon() + ","
								+ wormMoves.get(i).getNight() + ",");
						wr.write(wormMoves.get(i).getMorning() + "" + wormMoves.get(i).getAfternoon() + ""
								+ wormMoves.get(i).getNight() + "");
					} else {
						System.out.print(wormMoves.get(i).getMorning() + "," + wormMoves.get(i).getAfternoon() + ","
								+ wormMoves.get(i).getNight());
						wr.write(wormMoves.get(i).getMorning() + "" + wormMoves.get(i).getAfternoon() + ""
								+ wormMoves.get(i).getNight());
					}
				}
				wr.write("\n");
				System.out.println("");
			}
			wr.flush();
			wr.close();
			System.out.println("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
