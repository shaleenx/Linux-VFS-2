import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Exceptions.BadFileNameException;

/**
 * Created by pradeet on 12/4/16.
 */
public class FileSimulator {

	public static void main(String[] args) {
		try {
			ArrayList<FileSystem> sdas = new ArrayList<>();
			ArrayList<String> prompt = new ArrayList<>();
			prompt.add("root");
			String cmd = "";
			FileSystem sda = new FileSystem(10240);
			Scanner sc = new Scanner(System.in);
			while (!cmd.equalsIgnoreCase("exit")) {
				System.out.print("pradeet@OS: /" + formatPrompt(prompt) + "$  ");
				cmd = sc.next();
				switch (cmd) {
				case "sda":
					// int count = sdas.size();
					// FileSystem newFS = new
					// FileSystem(Integer.parseInt(sc.next()));
					// sdas.add(newFS);
					// int presentSda = count + 1;
					// prompt.clear();
					// prompt.add("sda" + presentSda);
					// sda = sdas.get(presentSda - 1);
					// System.out.println("created new sda" + presentSda);
					System.out.println("Check version 2 for this feature :P");
					break;
				case "mkdir":
					String folderName = sc.next();
					if (folderName.contains("/")) {
						System.out.println(
								"mkdir: cannot create directory \'" + folderName + "\': No such file or Directory");
					}
					// System.out.println(formatPrompt(prompt) + "/" +
					// folderName);
					sda.dir(formatPath(formatPrompt(prompt) + "/" + folderName));
					break;
				case "cd":
					folderName = sc.next();
					System.out.println("||" + folderName + "||");
					if (folderName.contains("..")) {
						prompt.remove(prompt.size() - 1);
					}
					Tree tree = sda.DirExists(formatPath(formatPrompt(prompt) + "/" + folderName));
					if (tree != null) {
						prompt.addAll(Arrays.asList(folderName.split("/")));
					} else {
						System.out.println("cd: " + folderName + ": No such file or directory");
					}
					break;
				case "rmdir":
					break;
				case "touch":
					break;
				case "rm":
					break;
				case "mv":
					break;
				case "ls":
					String[] list = sda.lsdir(formatPath(formatPrompt(prompt)));
					printStringArray(list);
					break;
				case "cat":
					break;
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// try {
		// sda1.dir(formatPath("root"));
		// sda1.dir(formatPath("root/hello"));
		// sda1.file(formatPath("root/sample1.txt"), 20);
		// sda1.file(formatPath("root/hello/sample.txt"), 30);
		// sda1.DirExists(formatPath("root"));
		// } catch (BadFileNameException e) {
		// e.printStackTrace();
		// } catch (OutOfSpaceException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// printStringArray(sda1.lsdir(formatPath("root")));
		//
		// // String[][] disk = sda1.disk();
		// // printdisk(disk);
		// System.out.println(sda1.lsdir(formatPath("root")).length);
	}

	private static String formatPrompt(ArrayList<String> prompt) {
		String promptText = "";
		for (int i = 0; i < prompt.size(); i++) {
			promptText += prompt.get(i);
			if (i < prompt.size() - 1) {
				promptText += "/";
			}
		}
		return promptText;
	}

	private static void printStringArray(String[] lsdir) {
		if (lsdir == null) {
			return;
		}
		for (int i = 0; i < lsdir.length; i++) {
			System.out.print(lsdir[i] + "  ");
		}
		System.out.println();
	}

	private static void printdisk(String[][] disk) {
		System.out.println("Printing Disk:");
		int count = 0;
		for (int i = 0; i < disk.length; i++) {
			if (disk[i] != null) {
				count++;
				System.out.println(i + ":");
				for (int j = 0; j < disk[i].length; j++) {
					System.out.println(disk[i][j]);
				}
			}
		}
		System.out.println("NOT NULL Disks: " + count);
	}

	public static String[] formatPath(String path) {
		return path.split("/");
	}
}
