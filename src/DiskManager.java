import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class DiskManager {

	RandomAccessFile sda;
	
	/**
	 * 
	 * @param size size of Disk in KBs
	 * @throws IOException
	 */
	public DiskManager(int size) throws IOException {
		sda = new RandomAccessFile("sda1", "rw");
		byte[] buf = new byte[size * 1024];
		sda.write(buf);
	}

	public void writeToDisk(Leaf file, String input) throws IOException {
		input += "\u001a";
		ArrayList<String> input_blocks = splitEqually(input, 1024);
		for (int i = 0; i < input_blocks.size(); i++) {
			sda.seek(file.allocations[i] * 1024);
			String write = input_blocks.get(i);
			sda.writeBytes(write);

		}
	}

	public static ArrayList<String> splitEqually(String text, int size) {
		ArrayList<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

		for (int start = 0; start < text.length(); start += size) {
			ret.add(text.substring(start, Math.min(text.length(), start + size)));
		}
		return ret;
	}

	public String readFromDisk(Leaf file) throws IOException {
		String data = "";

		for (int i = 0; i < file.allocations.length; i++) {
			sda.seek(file.allocations[i] * 1024);
			byte read[] = new byte[1024];
			sda.read(read);
			
			data += new String(read);
		}

		return data;
	}
}
