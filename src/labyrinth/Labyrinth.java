package labyrinth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @author Martin Bierbaumer
 *
 * Lustiger DFS
 */

public class Labyrinth {
	public static String[][] maps = {{
		"############",
		"#  #     # #",
		"## # ### # #",
		"#  # # # # #",
		"## ### # # #",
		"#        # #",
		"## ####### #",
		"#          #",
		"# ######## #",
		"# #   #    #",
		"#   #   # ##",
		"######A#####"
	}, {
		"################################",
		"#                              #",
		"# ############################ #",
		"# # ###       ##  #          # #",
		"# #     ##### ### # ########## #",
		"# #   ##### #     # #      ### #",
		"# # ##### #   ###   # # ## # # #",
		"# # ### # ## ######## # ##   # #",
		"# ##### #  # #   #    #    ### #",
		"# # ### ## # # # # ####### # # #",
		"# #        # #   #     #     # #",
		"# ######## # ######### # ### # #",
		"# ####     #  # #   #  # ##### #",
		"# # #### #### # # # # ## # ### #",
		"#                      # #     #",
		"###########################A####"
	}, {
		"###########################A####",
		"#   #      ## # # ###  #     # #",
		"# ###### #### # # #### ##### # #",
		"# # ###  ## # # # #          # #",
		"# # ### ### # # # # # #### # # #",
		"# #     ### # # # # # ## # # # #",
		"# # # # ### # # # # ######## # #",
		"# # # #     #          #     # #",
		"# ### ################ # # # # #",
		"# #   #             ## # #   # #",
		"# # #### ############# # #   # #",
		"# #                    #     # #",
		"# # #################### # # # #",
		"# # #### #           ###     # #",
		"# # ## # ### ### ### ### # ### #",
		"# #    #     ##  ##  # ###   # #",
		"# ####   ###### #### # ###  ## #",
		"###########################A####"
	}, {
		"#############",
		"#           #",
		"########### #",
		"#           #",
		"###########A#"
	}};

	/**
	 * Wandelt (unveränderliche) Strings in Char-Arrays
	 * @param map  der Plan, ein String je Zeile
	 * @return char[][] des Plans
	 */
	public static char[][] fromStrings(String[] map) {
		char[][] chars = new char[map.length][];
		for (int i = 0; i < map.length; i++)
			chars[i] = map[i].toCharArray();
		return chars;
	}


	/**
	 * Ausgabe des Layrinths
	 * @param lab
	 */
	public static void printLabyrinth(char[][] lab) {
		for (char[] line: lab) {
			for (char c: line)
				System.out.print(c);
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Suche den Weg
	 * @param zeile     aktuelle Position
	 * @param spalte     aktuelle Position
	 * @param lab
	 * @throws InterruptedException    für die verlangsamte Ausgabe mit sleep()
	 */

	public static boolean suchen(int zeile, int spalte, char[][] lab) throws InterruptedException {
		boolean erg = such(zeile, spalte, lab);
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				if (lab[i][j] == 'X') lab[i][j] = ' ';
			}
		}
		return erg;
	}

	private static boolean such(int zeile, int spalte, char[][] lab) throws InterruptedException {
		if (zeile < 0 || zeile >= lab.length || spalte < 0 || spalte >= lab[zeile].length) return false;
		if (lab[zeile][spalte] == 'A') return true;
		if (lab[zeile][spalte] != ' ') return false;
		lab[zeile][spalte] = 'X';
		printLabyrinth(lab);
		TimeUnit.MILLISECONDS.sleep(500);
		return such(zeile - 1, spalte, lab) || such(zeile + 1, spalte, lab) || such(zeile, spalte - 1, lab) || such(zeile, spalte + 1, lab);
	}

	record Point(int first, int second) {}

	/**
	 * Suche die Anzahl aller möglicher Wege
	 * @param zeile     aktuelle Position
	 * @param spalte     aktuelle Position
	 * @param lab
	 */

	public static long sucheAlle(int zeile, int spalte, char[][] lab) throws InterruptedException {
		return sucheAlle(zeile, spalte, lab, new HashSet<>());
	}
	private static long sucheAlle(int zeile, int spalte, char[][] lab, HashSet<Point> path) {
		Point t = new Point(zeile, spalte);
		if (path.contains(t) || zeile < 0 || zeile >= lab.length || spalte < 0 || spalte >= lab[zeile].length || lab[zeile][spalte] == '#') return 0;
		if (lab[zeile][spalte] == 'A') {
			path.remove(t);
			return 1;
		}
		path.add(t);
		long count = 0;
		for (Point p: new Point[]{new Point(zeile - 1, spalte), new Point(zeile + 1, spalte), new Point(zeile, spalte - 1), new Point(zeile, spalte + 1)}) {
			count += sucheAlle(p.first, p.second, lab, path);
		}
		path.remove(t);
		return count;
	}


	public static char[][] lesen(Path p) throws IOException {
		Object[] obj = Files.readAllLines(p).stream().map(String::toCharArray).toArray();
		char[][] chars = new char[obj.length][];
		for (int i = 0; i < obj.length; i++) {
			chars[i] = (char[]) obj[i];
		}
		return chars;
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		//System.out.println("Anzahl Wege: " + sucheAlle(1, 1, lesen(Path.of("src/labyrinth/l1.txt"))));
		System.out.println(suchen(1, 1, lesen(Path.of("src/labyrinth/l2.txt"))));
	}
}
