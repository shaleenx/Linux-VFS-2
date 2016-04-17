import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Exceptions.BadFileNameException;
import Exceptions.DirectoryNotEmptyException;
import Exceptions.OutOfSpaceException;

/**
 * Created by pradeet on 12/4/16.
 */
public class FileSimulator {

	public static void main(String[] args) {

		ArrayList<String> prompt = new ArrayList<>();
		prompt.add("root");
		String cmd = "";
		FileSystem sda = null;
		try {
			sda = new FileSystem(10240);
		} catch (IOException e) {
			System.out.println("Unable to Create FileSystem");
			cmd = "exit";
		}
		Scanner sc = new Scanner(System.in);
		while (!cmd.equalsIgnoreCase("exit")) {
			System.out.print("pradeet@OS: /" + formatPrompt(prompt) + "$  ");
			cmd = sc.next();
			switch (cmd) {
			case "sda":
				System.out.println("Check version 2 for this feature :P");
				break;
			case "mkdir":
				try {
					String folderName = sc.next();
					if (folderName.contains("/")) {
						System.out.println(
								"mkdir: cannot create directory \'" + folderName + "\': No such file or Directory");
						break;
					}
					sda.dir(formatPath(formatPrompt(prompt) + "/" + folderName));
				} catch (BadFileNameException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "cd":
				try {
					String folderName = sc.next();
					if (folderName.contains("..")) {
						if (prompt.size() > 1)
							prompt.remove(prompt.size() - 1);
						break;
					}
					Tree tree = sda.DirExists(formatPath(formatPrompt(prompt) + "/" + folderName));
					if (tree != null) {
						prompt.addAll(Arrays.asList(folderName.split("/")));
					} else {
						System.out.println("cd: " + folderName + ": No such Directory");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "rmdir":
				try {
					String folderName = sc.next();
					sda.rmdir(formatPath(formatPrompt(prompt) + "/" + folderName));
				} catch (DirectoryNotEmptyException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "touch":
				try {
					String fileName = sc.next();
					int size = Integer.parseInt(sc.next());

					sda.file(formatPath(formatPrompt(prompt) + "/" + fileName), size);
				} catch (BadFileNameException e) {
					System.out.println(e.getMessage());
				} catch (OutOfSpaceException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "rm":
				try {
					String rm_flag = sc.next();
					if (rm_flag.toLowerCase().equals("-r")) {

					} else {
						String fileName = rm_flag;
						sda.rmfile(formatPath(formatPrompt(prompt) + "/" + fileName));
					}
				} catch (BadFileNameException e) {
					System.out.println(e.getMessage());
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
				try {
					String fileName = sc.next();
					String data = sda.read(formatPath(formatPrompt(prompt) + "/" + fileName));
					System.out.println(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "echo":
				try {
					String argument = sc.nextLine();
					String input = argument.substring(1, argument.length());
					input = input.substring(1, input.lastIndexOf("\""));
					System.out.println(input);
					String[] arguments = argument.substring(argument.lastIndexOf("\"") + 1).trim().split(" ");
					System.out.println(arguments[0]);
					if (">".equals(arguments[0])) {
						sda.write(formatPath(formatPrompt(prompt) + "/" + arguments[1]), input);
					} else if (">>".equals(arguments[0])) {
						sda.append(formatPath(formatPrompt(prompt) + "/" + arguments[1]), input);
					}
				} catch (BadFileNameException e) {
					System.out.println(e.getMessage());
				} catch (OutOfSpaceException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "pwd":
				System.out.println(formatPrompt(prompt));
			case "exit":
				break;
			case "help":
				printHelp();
				break;
			default:
				System.out.println("Command not found");
				printHelp();
				break;
			}
		}
		sc.close();
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

	private static void printHelp() {
		System.out.println("Command                            Details");
		System.out.println("=======================            ===========================================");
		System.out.println("mkdir <Directory Name>             Create a new Directory");
		System.out.println("pwd                                Displays the current working Directory");
		System.out.println("cd <Directory Name>                Change Directory, .. to go to parent Directory");
		System.out.println("rmdir <Directory Name>             removes Empty repository");
		System.out.println("touch <File name> <File Size>      Creates a file with size <File Size>");
		System.out.println("rm <File Name>                     Delete the file");
		System.out.println("ls                                 List all files and directories in current directory");
		System.out.println("cat <File Name>                    Prints the contents of the file on the console");
		System.out.println(
				"echo \"<Input>\" > <File name>       Write the contents of Input into file, creates the file if the file doesnot exists");
		System.out.println(
				"echo \"<Input>\" >> <File name>      Appends the contents of Input to file, creates the file if the file doesnot exists");
		System.out.println("help                               Displays all the commands available");
		System.out.println("exit                               exits the file simulator");
	}

	public static String[] formatPath(String path) {
		return path.split("/");
	}
}
