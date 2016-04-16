import Exceptions.BadFileNameException;
import Exceptions.OutOfSpaceException;

/**
 * Created by pradeet on 12/4/16.
 */
public class FileSimulator {

	public static void main(String[] args) {
		int capacity = 10240;
		FileSystem sda1 = new FileSystem(capacity);
		try {
			sda1.dir(formatPath("root"));
			sda1.dir(formatPath("root/hello"));
			sda1.file(formatPath("root/sample1.txt"), 20);
			sda1.file(formatPath("root/hello/sample.txt"), 30);
			sda1.DirExists(formatPath("root"));
		} catch (BadFileNameException e) {
			e.printStackTrace();
		} catch (OutOfSpaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		printStringArray(sda1.lsdir(formatPath("root")));

		// String[][] disk = sda1.disk();
		// printdisk(disk);
		System.out.println(sda1.lsdir(formatPath("root")).length);
	}

	private static void printStringArray(String[] lsdir) {
		for (int i = 0; i < lsdir.length; i++) {
			System.out.println("printing: " + lsdir[i]);
		}
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
