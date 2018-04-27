
/* 
 * Student ID: 2016026
 * Name: Sameeha Rahman
 */

package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Grid {

	private GridPiece[][] grid;

	public Grid(int gridSize) {
		try {
			
			// multiply for 20 for the size of grid
			gridSize = gridSize * 20;

			// file for grid
			FileReader file = new FileReader("landscape" + gridSize + ".txt");

			BufferedReader buff = new BufferedReader(file);
			String line;

			// read one line of the file
			line = buff.readLine();

			// grid
			this.grid = new GridPiece[gridSize][gridSize];

			int count = 0;

			do {

				// split the line into an array, divided by spaces
				String weights[] = line.split(" ");

				// populate the array
				for (int i = 0; i < weights.length; i++) {
					this.grid[count][i] = new GridPiece(count, i, Integer.parseInt(weights[i]));
				}

				count++;

				// read the next line
				line = buff.readLine();

			} while (line != null);

			// close file once all values are read from it.
			buff.close();

		} catch (FileNotFoundException e) {

			System.err.println("Error. File not found");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GridPiece[][] getGrid() {
		return grid;
	}

}
