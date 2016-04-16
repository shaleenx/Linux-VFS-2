import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskManager {

	FileOutputStream sda;

	public DiskManager(int size) throws IOException {
		sda = new FileOutputStream("myDisk");
		byte[] buf = new byte[size];
		sda.write(buf);
		sda.flush();
	}

	public void writeToDisk(Leaf file, String input) {

	}

	public String read(Leaf file) {
		return null;
	}

}
