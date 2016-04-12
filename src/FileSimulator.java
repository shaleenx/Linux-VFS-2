import Exceptions.BadFileNameException;

/**
 * Created by pradeet on 12/4/16.
 */
public class FileSimulator {

    public static void main(String[] args) {
        int capacity = 10240;
        FileSystem sda1 = new FileSystem(capacity);
//        String[][] disk = sda1.disk();
//        System.out.println(disk.length);
        try {
            sda1.dir(formatPath("root"));
            sda1.dir(formatPath("root/hello"));
        } catch (BadFileNameException e) {
            e.printStackTrace();
        }
        System.out.println(sda1.lsdir(formatPath("root")).length);
    }

    public static String[] formatPath(String path) {
        return path.split("/");
    }
}
