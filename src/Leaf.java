import java.util.Arrays;

import Exceptions.OutOfSpaceException;

class Leaf extends Node {

	/**
	 * Data store in the leaf (in B)
	 */
	public int data_size;
	/**
	 * Array of blocks containing Leaf data
	 */

	public int[] allocations;

	/**
	 * Ctor - create leaf.
	 *
	 * @param name
	 *            Name of the leaf.
	 * @param size
	 *            Size, in KB, of the leaf.
	 * @throws OutOfSpaceException
	 *             Allocating space failed.
	 */
	public Leaf(String name, int size) throws OutOfSpaceException {

		this.name = name;
		this.data_size = 0;
		allocateSpace(size);

	}

	private void allocateSpace(int size) throws OutOfSpaceException {

		FileSystem.fileStorage.Alloc(size, this);
		System.out.println(Arrays.toString(allocations));

	}

	@Override
	public String toString() {
		return "Leaf:" + "\n\tname: " + name + "\n\tsize: " + data_size + "\n\tAllocations: "
				+ Arrays.toString(allocations);
	}

}
