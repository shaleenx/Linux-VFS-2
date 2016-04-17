import java.io.IOException;
import java.util.Arrays;

import Exceptions.BadFileNameException;
import Exceptions.DirectoryNotEmptyException;
import Exceptions.OutOfSpaceException;

public class FileSystem {

	private Tree fileSystemTree;

	/**
	 * Stores file->block allocations
	 */
	public static Space fileStorage;

	public static DiskManager diskManager;

	/**
	 * Ctor - Initialise filesystem with empty root directory and \c m KB of
	 * space
	 *
	 * @param m
	 *            Amount, in KB, of disk space to allocate
	 * @throws IOException
	 */
	public FileSystem(int m) throws IOException {

		fileSystemTree = new Tree("root");

		fileStorage = new Space(m);

		diskManager = new DiskManager(m);

	}

	/**
	 * Create an empty directory, with path provided in \c name.
	 *
	 * @param name
	 *            String array containing path to directory to be created, as in
	 *            \c file().
	 */
	public void dir(String[] name) throws BadFileNameException {

		Tree workingTree = fileSystemTree;

		if (!name[0].equals("root") || (FileExists(name) != null)) {
			// System.out.println("not root");
			throw new BadFileNameException("Invalid File Exception");

		}

		if (DirExists(name) != null) {
			return;
		}

		// loop all the way, creating as we go down if necessary
		for (int i = 0; i < name.length; i++) {
			// System.out.println("in loop");
			workingTree = workingTree.GetChildByName(name[i]);
		}

	}

	/**
	 * List allocation of blocks on disk.
	 *
	 * @return A 2D String array of block/file allocations. Each index
	 *         corresponds to one disk block and the entry is either null if the
	 *         blocks is free or an array of strings which is the path to the
	 *         file occupying that block.
	 */
	public String[][] disk() {

		Leaf[] alloc = FileSystem.fileStorage.getAlloc();
		String[][] disk = new String[alloc.length][];
		int i = 0;

		for (Leaf elem : alloc) {

			if (elem == null) {

				i++;
				continue;

			} else {

				disk[i++] = elem.getPath();

			}

		}

		return disk;

	}

	/**
	 * Create a \c k KB file, path provided in name.
	 *
	 * @param name
	 *            String array, each element of which is an element in the path
	 *            to file. Must start with root. Any nonexistent directories
	 *            along the path will be created.
	 * @param k
	 *            Size of file to create, in KB.
	 * @throws BadFileNameException
	 *             First directory is not root.
	 * @throws OutOfSpaceException
	 *             Adding child failed; not enough free space.
	 */
	public void file(String[] name, int k) throws BadFileNameException, OutOfSpaceException {

		Tree workingTree = fileSystemTree;
		String fileName = name[name.length - 1];
		// System.out.println("WTF: " + name[0]);
		if (!name[0].equals("root")) {

			throw new BadFileNameException();

		}

		if (k > FileSystem.fileStorage.countFreeSpace()) {
			// not enough space free

			Leaf file = FileExists(name);

			if (file == null) {

				throw new OutOfSpaceException();

			} else if (k <= (FileSystem.fileStorage.countFreeSpace() - file.allocations.length)) {
				// if there will be enough space free after deleting the old
				// file, do it

				rmfile(name);

			}

		}

		// loop until level containing file
		for (int i = 0; i < name.length - 1; i++) {

			workingTree = workingTree.GetChildByName(name[i]);

		}

		// will now be at same level as file, contained in workingTree file
		// exists, remove (reached this point, so file can fit)

		if (workingTree.children.containsKey(fileName)) {

			if (workingTree.children.get(fileName).getClass().getName() == "Tree") { // name
																						// of
																						// existing
																						// directory

				throw new BadFileNameException();

			}

			// enough space free, remove old file
			rmfile(name);

		}

		Leaf newLeaf = new Leaf(fileName, k);
		newLeaf.parent = workingTree;
		newLeaf.depth = newLeaf.parent.depth + 1;

		workingTree.children.put(fileName, newLeaf);

	}

	/**
	 * List files and subdirectories contained in name.
	 *
	 * @param name
	 *            String array containing path to directory to list, as in \c
	 *            file().
	 * @return A String array containing the filename (only) of all files in the
	 *         directory.
	 */
	public String[] lsdir(String[] name) {

		Tree file = DirExists(name);
		String[] fileList;

		if (file == null) {

			return null;

		}

		fileList = new String[file.children.size()];
		fileList = file.children.keySet().toArray(fileList);

		// sort array - not essential, but nice!
		Arrays.sort(fileList);

		return fileList;

	}

	/**
	 * Remove a file.
	 *
	 * @param name
	 *            String array containing path to file to be removed, as in \c
	 *            file().
	 * @throws BadFileNameException
	 */
	public void rmfile(String[] name) throws BadFileNameException {

		Leaf file = FileExists(name);

		if (file == null) { // file doesn't exist

			throw new BadFileNameException("File Doesnot exists");

		}

		FileSystem.fileStorage.Dealloc(file);

	}

	/**
	 * Remove an empty directory.
	 *
	 * @param name
	 *            String array containing path to directory to be removed, as in
	 *            \c file().
	 * @throws DirectoryNotEmptyException
	 *             The directory is not empty.
	 */
	public void rmdir(String[] name) throws DirectoryNotEmptyException {

		Tree dir = DirExists(name);

		if (dir == null) {

			return;

		}

		if (dir.children.size() > 0) {

			throw new DirectoryNotEmptyException();

		}

		dir.parent.children.remove(dir.name);

	}

	private Node PathExists(String[] name) {

		Tree workingTree = fileSystemTree;

		for (int i = 0; i < name.length - 1; i++) {

			if (!workingTree.children.containsKey(name[i])) {

				return null;

			}

			workingTree = (Tree) workingTree.children.get(name[i]);

		}

		return workingTree.children.get(name[name.length - 1]);

	}

	/**
	 * Checks whether the specified file exists
	 *
	 * @param name
	 *            Path to the file
	 * @return File if exists, \c null otherwise
	 */
	public Leaf FileExists(String[] name) {

		Node found = PathExists(name);

		if (found == null || found.getClass().getName() == "Node") {

			return null;

		}

		return (Leaf) found;

	}

	/**
	 * Checks whether the specified directory exists
	 *
	 * @param name
	 *            Path to directory
	 * @return Directory if exists, null otherwise
	 */
	public Tree DirExists(String[] name) {

		Node found = PathExists(name);

		if (found == null || found.getClass().getName() == "Leaf") {

			return null;

		}

		return (Tree) found;

	}

	/**
	 * Writes to the file specified in /c fromat.
	 * 
	 * @param name
	 *            Name of the file to be written.
	 * @param input
	 *            The text to be written to the file.
	 * @throws OutOfSpaceException
	 * @throws BadFileNameException
	 * @throws IOException
	 */
	public void write(String[] name, String input) throws BadFileNameException, OutOfSpaceException, IOException {

		int input_size = input.length();
		int num_blocks = (input.length() % 1024 == 0) ? input_size / 1024 : input_size / 1024 + 1;

		Leaf file = FileExists(name);

		if (file == null) {
			file(name, num_blocks);
		}

		file = FileExists(name);
		System.out.println(file.toString());

		int file_size = file.size * 1024;
		if (input_size < file_size)
			diskManager.writeToDisk(file, input);
		else {
			// To implement the case when the input is bigger than the file
			// itself.
		}
	}

	public void append(String[] name, String input) throws BadFileNameException, OutOfSpaceException, IOException {

		int input_size = input.length();
		int num_blocks = (input.length() % 1024 == 0) ? input_size / 1024 : input_size / 1024 + 1;

		Leaf file = FileExists(name);

		if (file == null) {
			file(name, num_blocks);
		} else {
			int size = file.size;
			int blocksRequired = num_blocks;
			if (fileStorage.countFreeSpace() < blocksRequired) {
				throw new OutOfSpaceException("DiskSpace Full!");
			}
			String filedata = diskManager.readFromDisk(file);
			this.rmfile(name);
			file(name, num_blocks + size);
			file = FileExists(name);
			diskManager.writeToDisk(file, filedata + input);
		}

	}

	public String read(String[] formatPath) {

		return null;
	}
}