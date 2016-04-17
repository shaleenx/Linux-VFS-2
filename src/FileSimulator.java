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
			Scanner sc = new Scanner(System.in);
			while (!cmd.equalsIgnoreCase("exit")) {
				System.out.print("pradeet@OS: /" + formatPrompt(prompt) + "$  ");
				cmd = sc.next();
				switch (cmd) {
				case "sda":
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
					if (folderName.contains("..")) {
						prompt.remove(prompt.size() - 1);
						break;
					}
					Tree tree = sda.DirExists(formatPath(formatPrompt(prompt) + "/" + folderName));
					if (tree != null) {
						prompt.addAll(Arrays.asList(folderName.split("/")));
					} else {
						System.out.println("cd: " + folderName + ": No such file or directory");
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
					System.out.println("size: " + size);
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
					System.out.println("Check version 2 for this feature :P");
					break;
				case "ls":
					String[] list = sda.lsdir(formatPath(formatPrompt(prompt)));
					printStringArray(list);
					break;
				case "cat":
					fileName = sc.next();
					String data = sda.read(formatPath(formatPrompt(prompt) + "/" + fileName));
					System.out.println(data);
					break;
				case "echo":
					String argument = sc.nextLine();
					String input = argument.substring(1, argument.length());
					input = input.substring(1, input.lastIndexOf("\""));
					System.out.println("input: " + input);
					String[] arguments = argument.substring(argument.lastIndexOf("\"") + 1).trim().split(" ");
					System.out.println(Arrays.toString(arguments));
					if (">".equals(arguments[0])) {
						System.out.println((formatPrompt(prompt) + "/" + arguments[1]));
						System.out.println("input: " + input);
						sda.write(formatPath(formatPrompt(prompt) + "/" + arguments[1]), input);
					} else if (">>".equals(arguments[0])) {
						sda.append(formatPath(formatPrompt(prompt) + "/" + arguments[1]), input);
					}
					break;
				case "pwd":
					System.out.println(formatPrompt(prompt));
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
			System.out.print(lsdir[i] + "  ");
		}
		System.out.println();
	}

	// private static void printdisk(String[][] disk) {
	// System.out.println("Printing Disk:");
	// int count = 0;
	// for (int i = 0; i < disk.length; i++) {
	// if (disk[i] != null) {
	// count++;
	// System.out.println(i + ":");
	// for (int j = 0; j < disk[i].length; j++) {
	// System.out.println(disk[i][j]);
	// }
	// }
	// }
	// System.out.println("NOT NULL Disks: " + count);
	// }

	public static String[] formatPath(String path) {
		return path.split("/");
	}
}
