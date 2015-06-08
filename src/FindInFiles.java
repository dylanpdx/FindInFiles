import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class FindInFiles {
	static ArrayList Found = new ArrayList();
	static int filesscanned = 0;
	static int dirsscanned = 0;
	static int calc = 0;
	static int filez = 0;
	static int percent;

	public static void main(String[] args) {
		new FindInFiles();
	}

	public static void search(String givpath, String what, boolean display) throws IOException {
		File path = new File(givpath);
		if (path.isDirectory()) {
			File[] files = path.listFiles();
			
			if (files != null){
				for (int i = 0; i < files.length; i++) { // Error: NullPointer
					if (files[i].isFile()) { // this line weeds out other
												// directories/folders
						// System.out.println(files[i]);
						filesscanned++;
						byte[] bytes = Files.readAllBytes(files[i].toPath());
						String str = new String(bytes, "UTF-8");
						if (str.contains(what)) {
							Found.add(files[i]);
						}
					} else {
						dirsscanned++;
						search(files[i].toString(), what, display);
					}
				}
			}
		}
		if (display) {
			System.out.println("Files Scanned: " + filesscanned);
			System.out.println("Directories Scanned: " + dirsscanned);
			System.out.println("Items Found: " + Found.size());
			if (calc != 0) {
				percent = (filesscanned * 100) / calc;
				System.out.println("Percentage: " + percent + "%  " + filesscanned + "   " + calc);
			}
			// System.out.println("Found: "+found);
		}
	}

	public static int calculate(String givpath) throws IOException {
		File path = new File(givpath);
		if (path.isDirectory()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) { // this line weeds out other
											// directories/folders
					filez++;
				} else {
					calculate(files[i].getPath());
				}
			}
		}
		// System.out.println("Files found: "+filez);
		return filez;
	}

	public FindInFiles() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the path you want to scan.");
		String inp = input.nextLine();
		System.out.println("What do you want to search for?");
		String inp2 = input.nextLine();
		System.out.println("Calculate? Y/N");
		String inp3 = input.nextLine().toUpperCase();
		filesscanned = 0;
		dirsscanned = 0;
		if (inp3.equals("Y")) {
			try {
				System.out.println("Calculating...");
				calc = calculate(inp);
				// filez=0;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			search(inp, inp2, true);
			System.out.println(Found.size());
			for (int i = 0; i < Found.size(); i++) {
				System.out.println(Found.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error!");
		}

	}
}
