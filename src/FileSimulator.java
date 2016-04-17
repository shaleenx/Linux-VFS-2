import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Exceptions.DirectoryNotEmptyException;

/**
 * Created by pradeet on 12/4/16.
 */
public class FileSimulator {

	public static void main(String[] args) {
		try {
			// ArrayList<FileSystem> sdas = new ArrayList<>();
			ArrayList<String> prompt = new ArrayList<>();
			prompt.add("root");
			String cmd = "";
			FileSystem sda = new FileSystem(1024);
			Printer.println("HELLO");
			Scanner sc = new Scanner(System.in);
			while (!cmd.equalsIgnoreCase("exit")) {
				Printer.print("pradeet@OS: /" + formatPrompt(prompt) + "$  ");
				cmd = sc.next();
				switch (cmd) {
				case "sda":
					Printer.println("Check version 2 for this feature :P");
					break;
				case "mkdir":
					String folderName = sc.next();
					if (folderName.contains("/")) {
						Printer.println(
								"mkdir: cannot create directory \'" + folderName + "\': No such file or Directory");
					}
					sda.dir(formatPath(formatPrompt(prompt) + "/" + folderName));
					break;
				case "cd":
					folderName = sc.next();
					if (folderName.contains("..")) {
						prompt.remove(prompt.size() - 1);
						break;
					}
					Tree tree = sda.DirExists(formatPath(formatPrompt(prompt) + "/" + folderName));
					if (tree != null) {
						prompt.addAll(Arrays.asList(folderName.split("/")));
					} else {
						Printer.println("cd: " + folderName + ": No such file or directory");
					}
					break;
				case "rmdir":
					folderName = sc.next();
					try {
						sda.rmdir(formatPath(formatPrompt(prompt) + "/" + folderName));
					} catch (DirectoryNotEmptyException e) {
						e.printStackTrace();
					}
					break;
				case "touch":
					String fileName = sc.next();
					int size = Integer.parseInt(sc.next());
					Printer.println("size: " + size);
					sda.file(formatPath(formatPrompt(prompt) + "/" + fileName), size);
					break;
				case "rm":
					String rm_flag = sc.next();
					if (rm_flag.toLowerCase().equals("-r")) {

					} else {
						fileName = rm_flag;
						sda.rmfile(formatPath(formatPrompt(prompt) + "/" + fileName));
					}
					break;
				case "mv":
					Printer.println("Check version 2 for this feature :P");
					break;
				case "ls":
					String[] list = sda.lsdir(formatPath(formatPrompt(prompt)));
					printStringArray(list);
					break;
				case "cat":
					fileName = sc.next();
					String data = sda.read(formatPath(formatPrompt(prompt) + "/" + fileName));
					Printer.println(data);
					break;
				case "echo":
					String argument = sc.nextLine();
					String input = argument.substring(1, argument.length());
					input = input.substring(1, input.lastIndexOf("\""));
					Printer.println("input: " + input);
					String[] arguments = argument.substring(argument.lastIndexOf("\"") + 1).trim().split(" ");
					Printer.println(Arrays.toString(arguments));
					if (">".equals(arguments[0])) {
						Printer.println((formatPrompt(prompt) + "/" + arguments[1]));
						Printer.println("input: " + input);
						sda.write(formatPath(formatPrompt(prompt) + "/" + arguments[1]), input);
					} else if (">>".equals(arguments[0])) {
						Printer.println("INTO APPEND");
						sda.append(formatPath(formatPrompt(prompt) + "/" + arguments[1]), input);
					}
					break;
				case "pwd":
					Printer.println(formatPrompt(prompt));
				case "exit":
					break;
				case "help":
				default:
					break;
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			Printer.print(lsdir[i] + "  ");
		}
		Printer.println();
	}

	// private static void printdisk(String[][] disk) {
	// Printer.println("Printing Disk:");
	// int count = 0;
	// for (int i = 0; i < disk.length; i++) {
	// if (disk[i] != null) {
	// count++;
	// Printer.println(i + ":");
	// for (int j = 0; j < disk[i].length; j++) {
	// Printer.println(disk[i][j]);
	// }
	// }
	// }
	// Printer.println("NOT NULL Disks: " + count);
	// }

	public static String[] formatPath(String path) {
		return path.split("/");
	}
}
