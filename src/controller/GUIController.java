
/* 
 * Student ID: 2016026
 * Name: Sameeha Rahman
 */

package controller;

import java.util.ArrayList;
import java.util.PriorityQueue;
import model.Grid;
import model.GridPiece;

public class GUIController {

	Grid gridview;

	public GUIController(int gridSize) {
		super();
		this.gridview = new Grid(gridSize);
	}

	public Grid getGridview() {
		return gridview;
	}

	// check if these possible x and y coordinates are valid
	public boolean isValid(int row, int col) {
		return (row >= 0) && (row < this.gridview.getGrid().length) && (col >= 0)
				&& (col < this.gridview.getGrid().length);
	}

	public ArrayList<GridPiece> search(GridPiece source, GridPiece destination, String heuType) {

		GridPiece[][] grid = this.gridview.getGrid();

		// the nodes that have been visited already
		ArrayList<GridPiece> visitedNodes = new ArrayList<>();

		// the path from source to destination
		ArrayList<GridPiece> pathList = new ArrayList<>();

		// temporary grid of grid pieces for the path finding
		GridPiece[][] cellDetails = new GridPiece[grid.length][grid.length];

		// calculate the heuristic for each cell from the current position to the
		// source.
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {

				cellDetails[i][j] = new GridPiece(i, j, grid[i][j].getWeight());

				if (grid[i][j].getWeight() != 0) {

					switch (heuType) {
					case "Manhattan":
						cellDetails[i][j].setHeuristic(manhattan(i, j, destination));
						break;

					case "Euclidean":
						cellDetails[i][j].setHeuristic(euclidean(i, j, destination));
						break;

					case "Chebyshev":
						cellDetails[i][j].setHeuristic(chebyshev(i, j, destination));
						break;
					case "None":
						cellDetails[i][j].setHeuristic(0);
						break;
					}

				} else {

					// set the heuristic to -1 if the current grid piece is an obstacle
					cellDetails[i][j].setHeuristic(-1);

				}
			}
		}

		/*
		 * creating a priority queue with a custom method of calculating priority. It
		 * takes into consideration the final cost of each grid piece Taken two grid
		 * pieces, if the final cost of the first piece is lower than the second, it is
		 * given a lower priority If first piece is higher than the second, it is given
		 * a higher priority, If they are the same, they have the same priority.
		 */

		PriorityQueue<GridPiece> unvisitedNodes = new PriorityQueue<>(10,
				(gridPie1, gridPie2) -> ((GridPiece) gridPie1).getFinalCost() < ((GridPiece) gridPie2).getFinalCost()
						? -1
						: ((GridPiece) gridPie1).getFinalCost() > ((GridPiece) gridPie2).getFinalCost() ? 1 : 0);

		// list of all possible neighbors from one cell.
		int[] xDirections = { 1, 0, 0, -1, -1, -1, 1, 1 };
		int[] yDirections = { 0, 1, -1, 0, 1, -1, 1, -1 };

		// add the source node to the unvisited node list
		unvisitedNodes.add(cellDetails[source.getX()][source.getY()]);

		int directions = xDirections.length;

		// while there are still more unvisited nodes
		while (!unvisitedNodes.isEmpty()) {

			/*
			 * When traversing from node to node found on the queue, the one with highest
			 * final cost is searched. The larger final cost means that the node is closer
			 * to the source due to the higher heuristic value from the destination.
			 */

			// get the next node from the unvisited list
			GridPiece node = unvisitedNodes.poll();

			// add this node to the list of visited nodes
			visitedNodes.add(node);

			// get out of the loop if the current node is the destination node.
			if (node == cellDetails[destination.getX()][destination.getY()]) {
				break;
			}

			// get the coordinates of the node on the graph
			int i = node.getX();
			int j = node.getY();

			// for the number of possible directions (neighbors)
			for (int x = 0; x < directions; x++) {

				// get the new direction coordinates
				int newI = i + xDirections[x];
				int newJ = j + yDirections[x];

				/*
				 * If the new nodes position is within the constraints of the grid, the new
				 * final cost is calculated for it and its added to the list of nodes yet to
				 * visit if their final cost is larger than the current nodes final cost
				 */

				// if this possible move is valid
				if (isValid(newI, newJ)) {

					// check if the node in this new position is not an obstacle, has not been
					// visited, or marked to be visited later
					if (cellDetails[newI][newJ].getHeuristic() != -1
							&& !unvisitedNodes.contains(cellDetails[newI][newJ])
							&& !visitedNodes.contains(cellDetails[newI][newJ])) {

						// find the final cost of this new position node
						double fValue = cellDetails[newI][newJ].getHeuristic() + cellDetails[newI][newJ].getWeight();

						// set the calculated final cost to this new position nodes f value
						cellDetails[newI][newJ].setFinalCost(fValue);

						// add this new position node to the list of node yet to be visited
						unvisitedNodes.add(cellDetails[newI][newJ]);

						// set the current node as the parent of this new position node so when finding
						// the path, we can traverse back to the source using the parent node
						cellDetails[newI][newJ].setParent(node);
					}
				}
			}
		}

		/*
		 * The last added node to the list of visited nodes is the destination node. The
		 * parent of this node is the node in that path before it. This repeats till the
		 * source node. The source node has no parent so the loop quits.
		 */

		GridPiece endNode = visitedNodes.get(visitedNodes.size() - 1);

		// while we traverse through the final path
		while (endNode.getParent() != null) {

			// the last node is the current node
			GridPiece currentNode = endNode;

			// add this node to the list of path nodes
			pathList.add(currentNode);

			// get the parent node of the current node. This node becomes the new endNode
			endNode = endNode.getParent();
		}

		// add the source to the path
		pathList.add(cellDetails[source.getX()][source.getY()]);

		// Clears the list of visited and unvisited nodes
		unvisitedNodes.clear();
		visitedNodes.clear();

		// return the list of nodes that are the path from source to destination.
		return pathList;
	}

	public double chebyshev(int currentX, int currentY, GridPiece target) {
		return Math.max(Math.abs(currentX - target.getX()), Math.abs(currentY - target.getY()));
	}

	public double manhattan(int currentX, int currentY, GridPiece target) {
		return (Math.abs(currentX - target.getX()) + Math.abs(currentY - target.getY()));
	}

	public double euclidean(int currentX, int currentY, GridPiece target) {
		return Math.sqrt(Math.pow(currentX - target.getX(), 2) + Math.pow(currentY - target.getY(), 2));
	}

}
