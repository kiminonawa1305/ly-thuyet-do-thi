package tuan1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class Graph {
	protected int numVertex;
	protected int[][] matrix;

	public Graph(String pathFile) throws IOException {
		loadGraph(pathFile);
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

	public void printMatrix(int[][] matrix) {
		System.out.print("  ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((char) ('A' + i) + " ");
		}

		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((char) ('A' + i) + " ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Phương thức kiểm tra xem ma trận này có phải là đơn đồ thị hay không;
	 * 
	 * @param matrix
	 * @return
	 */
	public boolean checkValid(int[][] matrix) {
		int i = 0;
		for (; i < matrix.length && matrix[i][i] == 0; i++) {
		}

		return i  == matrix.length;
	}

	/**
	 * Viết phương thức kiểm tra đồ thị có phải là đồ thị vô hướng hay không
	 * 
	 * @param matrix
	 */
	public abstract boolean checkUnGraph(int[][] matrix);

	/**
	 * Viết phương thức thêm một cạnh vào đồ thị
	 * 
	 * @param matrix
	 * @param v1
	 * @param v2
	 */
	public abstract void addEdge(int[][] matrix, int v1, int v2);

	/**
	 * Viết phương thức xóa một cạnh vào đồ thị
	 * 
	 * @param matrix
	 * @param v1
	 * @param v2
	 */
	public abstract void removeEdge(int[][] matrix, int v1, int v2);

	/**
	 * Viết phương tính tổng số đỉnh của đồ thị
	 * 
	 * @return
	 */
	public int numVertexs() {
		return numVertex;
	}

	/**
	 * Viết phương tính tổng số cạnh của đồ thị
	 * 
	 * @return
	 */
	public int numEdges() {
		int numEdges = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				numEdges += matrix[i][j];
			}
		}

		return numEdges;
	}

	/**
	 * Viết phương tính tổng bậc của mỗi đỉnh
	 * 
	 * @param v
	 * @return
	 */
	public abstract int deg(int v);

	/**
	 * Viết phương tính tổng bậc của đồ thị
	 * 
	 * @param v
	 * @return
	 */
	public int sumDeg(int v) {
		int sumDeg = 0;
		for (int i = 0; i < matrix.length; i++) {
			sumDeg += deg(i + 1);
		}

		return sumDeg;
	}

	public static void main(String[] args) throws IOException {
		Graph dnGraph = new DnGraph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\ltdt\\Bo du lieu test\\DnGraph2");
		dnGraph.printMatrix(dnGraph.matrix);
		System.out.println("Check đơn đồ thị: " + dnGraph.checkValid(dnGraph.matrix));
		System.out.println("Check đồ thị vô hướng: " + dnGraph.checkUnGraph(dnGraph.matrix));
		dnGraph.addEdge(dnGraph.matrix, 2, 4);
		System.out.println("================\tSau khi thêm cạnh");
		dnGraph.printMatrix(dnGraph.matrix);
		dnGraph.removeEdge(dnGraph.matrix, 2, 4);
		System.out.println("================\tSau khi xóa cạnh");
		dnGraph.printMatrix(dnGraph.matrix);
		System.out.println("Số đỉnh của đồ thị: " + dnGraph.numVertex);
		System.out.println("Số cạnh của đồ thị: " + dnGraph.numEdges());
		System.out.println("Bậc của đỉnh 4: " + dnGraph.deg(4));
		System.out.println("Tổng số bậc của đồ thị: " + dnGraph.sumDeg(0));
		
		System.out.println();
		System.out.println();
		
		Graph unGraph = new UnGraph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\ltdt\\Bo du lieu test\\graph.txt");
		unGraph.printMatrix(unGraph.matrix);
		System.out.println("Check đơn đồ thị: " + unGraph.checkValid(unGraph.matrix));
		System.out.println("Check đồ thị vô hướng: " + unGraph.checkUnGraph(unGraph.matrix));
		unGraph.addEdge(unGraph.matrix, 2, 4);
		System.out.println("================\tSau khi thêm cạnh");
		unGraph.printMatrix(unGraph.matrix);
		unGraph.removeEdge(unGraph.matrix, 2, 4);
		System.out.println("================\tSau khi xóa cạnh");
		unGraph.printMatrix(unGraph.matrix);
		System.out.println("Số đỉnh của đồ thị: " + unGraph.numVertex);
		System.out.println("Số cạnh của đồ thị: " + unGraph.numEdges());
		System.out.println("Bậc của đỉnh 4: " + unGraph.deg(4));
		System.out.println("Tổng số bậc của đồ thị: " + unGraph.sumDeg(0));
	}
}
