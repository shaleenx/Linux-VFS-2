
public class Printer {

	static boolean debug = true;

	static void println(Object log) {
		if (debug)
			System.out.println(log);
	}

	static void println() {
		if (debug)
			System.out.println();
	}

	static void print(String log) {
		if (debug)
			System.out.print(log);
	}
}
