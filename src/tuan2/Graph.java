package tuan2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
	private int numVertex;
	private int[][] matrix;
	private int index = 0;
	private int vertex = 0;
	private int[] result;
	private boolean visited[];
	private int saveIndex = 0;
	private ArrayList<Integer> queueBFS = new ArrayList<>();

	public Graph(String pathFile) throws IOException {
		loadGraph(pathFile);
		result = new int[numVertex];
		visited = new boolean[numVertex];
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

	public void BFS() {
		int re[] = new int[matrix.length];
		int form = 0;
		int count = 0;

		while (form < matrix.length) {
			int check[] = this.BFSGraph();
			System.arraycopy(check, 0, re, form, check.length);
			form = check.length;
			count++;
		}

		if (count != 1) {
			System.out.println("Đồ thị trên không liên thông và có " + count + " thành phần");
		} else {
			System.out.println("Đây là một đồ thị liên thông");
		}
	}

	/**
	 * (2) Viết phương thức dùng giải thuật BFS duyệt đồ thị G,
	 * 
	 * @param matrix
	 * @return
	 */
	public int[] BFSGraph() {
		int startVex = 0;
		int i = 0;
		result = new int[matrix.length];
		visited = new boolean[matrix.length];
		Queue<Integer> vertexes = new LinkedList<>();

		vertexes.add(startVex);
		result[i] = startVex + 1;
		visited[startVex] = true;

		run: while (!vertexes.isEmpty()) {
			int vertex = vertexes.peek();
			for (int j = 0; j < numVertex; j++) {
				if (!visited[j] && matrix[vertex][j] != 0) {
					result[++i] = j + 1;
					visited[j] = true;
					vertexes.add(j);
					continue run;
				}
			}
			vertexes.poll();
		}

		return result;
	}

	public int[] BFSGraph(int startVex) {
		int i = 0;
		result = new int[matrix.length];
		visited = new boolean[matrix.length];
		Queue<Integer> vertexes = new LinkedList<>();

		vertexes.add(startVex);
		result[i] = startVex + 1;
		visited[startVex] = true;

		run: while (!vertexes.isEmpty()) {
			int vertex = vertexes.peek();
			for (int j = 0; j < numVertex; j++) {
				if (!visited[j] && matrix[vertex][j] != 0) {
					result[++i] = j + 1;
					visited[j] = true;
					vertexes.add(j);
					continue run;
				}
			}
			vertexes.poll();
		}

		return result;
	}

	public int[] BFSGraphDeQuy(int startVex) {
		result[saveIndex] = startVex + 1;
		if (!visited[startVex]) {
			queueBFS.add(startVex);
		}
		visited[startVex] = true;

		for (int i = 0; i < this.numVertex; i++) {
			if (!visited[i] && matrix[startVex][i] != 0) {
				result[++index] = i + 1;
				visited[i] = true;
				queueBFS.add(i);
			}
		}

		queueBFS.remove(0);
		if (!queueBFS.isEmpty()) {
			saveIndex++;
			BFSGraphDeQuy(queueBFS.get(0));
		}

		return result;
	}

	public int[] BFSGraphDeQuy() {
		result[saveIndex] = vertex + 1;
		if (!visited[vertex]) {
			queueBFS.add(vertex);
		}
		visited[vertex] = true;

		for (int i = 0; i < this.numVertex; i++) {
			if (!visited[i] && matrix[vertex][i] != 0) {
				result[++index] = i + 1;
				visited[i] = true;
				queueBFS.add(i);
			}
		}

		queueBFS.remove(0);
		if (!queueBFS.isEmpty()) {
			saveIndex++;
			BFSGraphDeQuy(queueBFS.get(0));
		}

		return result;
	}

	public void DFS() {
		int re[] = new int[matrix.length];
		int form = 0;
		int count = 0;

		while (form < matrix.length) {
			int check[] = this.DFSGraph();
			System.arraycopy(check, 0, re, form, check.length);
			form = check.length;
			count++;
		}

		if (count != 1) {
			System.out.println("Đồ thị trên không liên thông và có " + count + " thành phần");
		} else {
			System.out.println("Đây là một đồ thị liên thông");
		}
	}

	/**
	 * (2) Viết phương thức dùng giải thuật DFS duyệt đồ thị G bằng đệ quy
	 * 
	 * @param matrix
	 * @return
	 */
	public int[] DFSGraphDeQuy() {
		visited[vertex] = true;
		result[index] = vertex + 1;
		int save = vertex + 1;
		index++;
		for (int i = 0; i < numVertex; i++) {
			if (visited[i] == false && (matrix[vertex][i] != 0)) {
				vertex = i;
				DFSGraphDeQuy();
			}
		}

		vertex = save;
		return result;
	}

	public int[] DFSGraph() {
		int startVex = 0;
		int i = 0;
		result = new int[matrix.length];
		visited = new boolean[matrix.length];
		Stack<Integer> vertexes = new Stack<>();

		vertexes.add(startVex);
		result[i] = startVex + 1;
		visited[startVex] = true;

		run: while (!vertexes.isEmpty()) {
			int vertex = vertexes.peek();
			for (int j = 0; j < numVertex; j++) {
				if (!visited[j] && matrix[vertex][j] != 0) {
					result[++i] = j + 1;
					visited[j] = true;
					vertexes.add(j);
					continue run;
				}
			}
			vertexes.pop();
		}

		return result;
	}

	public int[] DFSGraph(int startVex) {
		int i = 0;
		result = new int[matrix.length];
		visited = new boolean[matrix.length];
		Stack<Integer> vertexes = new Stack<>();

		vertexes.add(startVex);
		visited[startVex] = true;
		boolean canPop = true;

		run: while (!vertexes.isEmpty()) {
			if (canPop) {
				vertex = vertexes.pop();
				result[i] = vertex + 1;
				i++;
			}

			canPop = false;
			for (int index = numVertex - 1; index >= 0 ; index--) {
				if (this.matrix[vertex][index] != 0 && !this.visited[index] && !vertexes.contains(index)) {
					vertexes.add(index);
					visited[index] = true;
					continue run;
				}
			}
			canPop = true;
		}

		return result;
	}

	public int[] DFSGraphDeQuy(int startVex) {
		visited[startVex] = true;
		result[index] = startVex + 1;
		index++;

		for (int i = 0; i < matrix.length; i++) {
			if (matrix[startVex][i] != 0 && !visited[i]) {
				DFSGraphDeQuy(i);
			}
		}

		return result;
	}

	public static void main(String[] args) throws IOException {
		Graph test = new Graph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\ltdt\\Bo du lieu test\\graph");
		int[] DFS = test.DFSGraph(4);
		System.out.println(Arrays.toString(DFS));

		test = new Graph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\ltdt\\Bo du lieu test\\graph");
		DFS = test.DFSGraphDeQuy(4);
		System.out.println(Arrays.toString(DFS));

	}
}
