
/* 
 * Student ID: 2016026
 * Name: Sameeha Rahman
 */

package model;

public class GridPiece {
	private int x;
	private int y;
	private GridPiece parent;
	private double heuristic;
	private double finalCost;
	private double weight;

	public GridPiece(int x, int y, double weight) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
		this.heuristic = 0;
		this.finalCost = 0;
	}

	public GridPiece(int x, int y, double heuristic, double weight) {
		super();
		this.x = x;
		this.y = y;
		this.heuristic = heuristic;
		this.weight = weight;
		this.finalCost = 0;
	}

	// x coordinates
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	// y coordinates
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	// the node before the current node in the path.
	public GridPiece getParent() {
		return parent;
	}

	public void setParent(GridPiece parent) {
		this.parent = parent;
	}

	// heuristic of the current node
	public double getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}

	// the final cost to travel to the current node
	public double getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(double finalCost) {
		this.finalCost = finalCost;
	}

	// the weight of the path
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "GridPiece [x=" + x + ", y=" + y + ", heuristic=" + heuristic + ", finalCost="
				+ finalCost + ", weight=" + weight + "]";
	}

}
