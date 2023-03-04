package tuan3;

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

	/**
	 * Tìm các đỉnh có liên thông với 1 đỉnh cho trước.
	 * 
	 * @param dinh
	 */
	public void diTimCacDinhLienThong(int dinh) {
		ArrayList<Integer> re = new ArrayList<>();
		visited = new boolean[numVertex];
		for (int i = 0; i < numVertex; i++) {
			if (matrix[dinh][i] != 0 && !visited[i]) {
				re.add(i);
			}
		}

		System.out.println("Các đỉnh liên thông với đỉnh: " + dinh + " là " + re);
	}
	
	public ArrayList diTimCacDinhLienThong1(int dinh) {
		ArrayList<Integer> re = new ArrayList<>();
		visited = new boolean[numVertex];
		for (int i = 0; i < numVertex; i++) {
			if (matrix[dinh][i] != 0 && !visited[i]) {
				re.add(i);
			}
		}

		return re;
	}

	/**
	 * Xét tính liên thông của đồ thị, đếm số thành phần liên thông cũng như là xuất ra các đỉnh thuộc cũng 1 thành phần liên thông.
	 * 
	 * Chạy vòng lặp để kiểm tra xem khi chạy thuật toán do tìm thì các đỉnh đã được thăp hay chưa.
	 * 
	 * Nếu khi chạy xong mà vẫn có đỉnh chưa được dò đến thì điểm tiếp theo được xét để chạy thuật toán dò là đỉnh đó
	 * 
	 * các đỉnh thuộc cùng thành phần sẽ được lưu vào 1 mảng và các đỉnh thuộc các thành phần khác nhau sẽ các nhau bằng số -1
	 * @param matrixAdj
	 */
	public void xetTinhLienThong() {
		this.DFSGraph();
		boolean check = false;
		int startVex = 0;
		int count = 0;
		ArrayList<Integer> dinhThuocCungThanhPhan = new ArrayList<>();
		
		while (!check) {
			/**
			 * 
			 */
			this.DFSGraphDeQuy(startVex);
			check = true;
			count++;
			for (int i = 0; i < numVertex; i++) {
				if (!visited[i]) {
					check = false;
					startVex = i;
				}else {
					if(!dinhThuocCungThanhPhan.contains(i  + 1)) {
						dinhThuocCungThanhPhan.add(i + 1);
					}
				}
			}
			dinhThuocCungThanhPhan.add(-1);
		}

		if (count == 1) {
			System.out.println("Đồ thị trên là đồ thị liên thông");
		} else {
			int i = 0;
			int thanhPhan = 1;
			while(i < dinhThuocCungThanhPhan.size()) {
				System.out.print("Cac dinh thuoc thanh phan lien thong thu: " + thanhPhan + " la: ");
				for(; i < dinhThuocCungThanhPhan.size() && dinhThuocCungThanhPhan.get(i) != -1; i++) {
					System.out.print(dinhThuocCungThanhPhan.get(i) + " ");
				}
				System.out.println();
				i++;
				thanhPhan++;
			}
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
		int i = 0;
		int startVex = 0;
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
			for (int index = numVertex - 1; index >= 0; index--) {
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
			for (int index = numVertex - 1; index >= 0; index--) {
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

	public boolean isConnected() {
		this.DFSGraph();

		for (int i = 0; i < numVertex; i++) {
			if (!visited[i]) {
				return false;
			}
		}

		return true;
	}

	public void findPathTwoVexsByDFS(int s, int t) {
		if (s < 0 && s >= numVertex) {
			System.out.println("Không tồn tại đỉnh: " + s);
			return;
		}

		if (t < 0 && t >= numVertex) {
			System.out.println("Không tồn tại đỉnh: " + t);
			return;
		}

		String re = "Đường đi từ đỉnh: " + (s + 1) + " đến đỉnh: " + t + " là: ";
		int[] check = this.DFSGraph(s);
		for (int i = 0; i < check.length; i++) {
			if (check[i] == t) {
				re += check[i];
				System.out.println(re);
				return;
			} else {
				re += check[i] + "->";
			}
		}
		
		public void findPathTwoVexsByBFS(int s, int t) {
			if (s < 0 && s >= numVertex) {
				System.out.println("Không tồn tại đỉnh: " + s);
				return;
			}

			if (t < 0 && t >= numVertex) {
				System.out.println("Không tồn tại đỉnh: " + t);
				return;
			}

			String re = "Đường đi từ đỉnh: " + (s + 1) + " đến đỉnh: " + t + " là: ";
			int[] check = this.BFSGraph(s);
			for (int i = 0; i < check.length; i++) {
				if (check[i] == t) {
					re += check[i];
					System.out.println(re);
					return;
				} else {
					re += check[i] + "->";
				}
			}

		System.out.println("Không có đường đi từ đỉnh: " + s + " đến đỉnh: " + t);
	}

	public boolean checkBipartiteGraph() {
		ArrayList<Integer> x = new ArrayList<>();
		ArrayList<Integer> y = new ArrayList<>();
		x.add(0);
		while(true) {
			this.diTimCacDinhLienThong1(x.get(0));
		}
	}

	public static void main(String[] args) throws IOException {
		Graph test = new Graph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\ltdt\\Bo du lieu test\\graph");
		int[] DFS = test.DFSGraph(4);
		System.out.println(Arrays.toString(DFS));

		test = new Graph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\ltdt\\Bo du lieu test\\graph");
		DFS = test.DFSGraphDeQuy(4);
		System.out.println(Arrays.toString(DFS));
		test.findPathTwoVexsByDFS(4, 7);

		Graph graph = new Graph(
				"D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\ltdt\\Bo du lieu test\\doThiKhongLienThong");
		graph.xetTinhLienThong();

	}
}
