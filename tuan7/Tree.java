package tuan7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Tree {
	private int numVertex;
	private int[][] matrix;
	private boolean visited[];
	private int color[];
	private int[] result;

	public Tree(String pathFile) throws IOException {
		loadGraph(pathFile);
		color = new int[this.numVertex];
		visited = new boolean[this.numVertex];
		result = new int[this.numVertex];
	}

	public int[][] getMatrix() {
		return matrix;
	}

	/**
	 * 
	 * @param pathFile
	 * @throws IOException
	 */
	public void loadGraph(String pathFile) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(pathFile));

		String line = read.readLine();
		if (line != null) {
			numVertex = Integer.parseInt(line);
			matrix = new int[numVertex][numVertex];
		} else {
			return;
		}

		int row = 0;
		while (true) {
			line = read.readLine();

			if (line == null) {
				break;
			}

			for (int i = 0; i < matrix[row].length; i++) {
				matrix[row][i] = Integer.parseInt(line.split(" ")[i]);
			}
			row++;
		}
		read.close();
	}

	public void printMatrix() {
		System.out.print("  ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((1 + i) + " ");
		}

		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((1 + i) + " ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printMatrix(int[][] matrix) {
		System.out.print("  ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((1 + i) + " ");
		}

		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((1 + i) + " ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void addEdge(int x, int y, int[][] matrix) {
		if (x >= 0 && y >= 0 && x < matrix.length && y < matrix.length) {
			matrix[x][y]++;
			matrix[y][x]++;
		}
	}

	public boolean checkUnGraph(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != matrix[j][i]) {
					return false;
				}
			}
		}

		return true;
	}

	public int totalEdge(int[][] matrix) {
		int result = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (i == j && matrix[i][j] != 0) {
					result += matrix[i][j] * 2;
				} else {
					result += matrix[i][j];
				}
			}
		}

		return result/2;
	}

	public boolean checkTree(int[][] matrix) {
		if (this.checkUnGraph(matrix)) {
			return matrix.length - 1 == this.totalEdge(matrix);
		} else {
			return false;
		}
	}

	public int[][] BFSTree(int v) {
		int[][] result = new int[matrix.length][matrix.length];
		Stack<Integer> stack = new Stack<>();
		visited = new boolean[matrix.length];
		stack.add(v);
		visited[v] = true;
		a: while (!stack.isEmpty()) {
			v = stack.peek();
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i] && matrix[v][i] != 0) {
					this.addEdge(v, i, result);
					visited[i] = true;
					stack.add(i);
					continue a;
				}
			}

			stack.pop();
		}

		return result;
	}

	public int[][] DFSTree(int v) {
		int[][] result = new int[matrix.length][matrix.length];
		Queue<Integer> queue = new LinkedList<>();
		visited = new boolean[matrix.length];
		queue.add(v);
		visited[v] = true;
		while (!queue.isEmpty()) {
			v = queue.peek();
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i] && matrix[v][i] != 0) {
					queue.add(i);
					visited[i] = true;
					addEdge(v, i, result);
				}
			}

			queue.poll();
		}

		return result;
	}

	public static void main(String[] args) throws IOException {
		Tree test1 = new Tree("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\BaiTapTrenLop\\src\\tuan7\\DFSBFSgraph.txt");
		test1.printMatrix(test1.BFSTree(0));
		System.out.println("=========================");
		test1.printMatrix(test1.DFSTree(0));
		System.out.println("==========================");
		Tree test2 = new Tree("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\BaiTapTrenLop\\src\\tuan7\\tree_3_phan");
		System.out.println(test2.checkTree(test2.matrix));
		System.out.println(test2.checkTree(test1.matrix));
	}
}
