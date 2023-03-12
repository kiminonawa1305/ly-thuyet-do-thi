package tuan4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public abstract class Graph {
	protected int numVertex;
	protected int[][] matrix;
	private int index = 0;
	private int vertex = 0;
	private int[] result;
	private boolean visited[];
	private int saveIndex = 0;
	private ArrayList<Integer> queueBFS = new ArrayList<>();

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

	public void printMatrix() {
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
	public boolean checkValid() {
		int i = 0;
		for (; i < matrix.length && matrix[i][i] == 0; i++) {
		}

		return i == matrix.length;
	}

	/**
	 * Viết phương thức kiểm tra đồ thị có phải là đồ thị vô hướng hay không
	 * 
	 * @param matrix
	 */
	public abstract boolean checkUnGraph();

	/**
	 * Viết phương thức thêm một cạnh vào đồ thị
	 * 
	 * @param matrix
	 * @param v1
	 * @param v2
	 */
	public abstract void addEdge(int v1, int v2);

	/**
	 * Viết phương thức xóa một cạnh vào đồ thị
	 * 
	 * @param matrix
	 * @param v1
	 * @param v2
	 */
	public abstract void removeEdge(int v1, int v2);

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
	public int sumDeg() {
		int sumDeg = 0;
		for (int i = 0; i < matrix.length; i++) {
			sumDeg += deg(i + 1);
		}

		return sumDeg;
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

	public ArrayList<Integer> diTimCacDinhLienThong1(int dinh) {
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
	 * Xét tính liên thông của đồ thị, đếm số thành phần liên thông cũng như là xuất
	 * ra các đỉnh thuộc cũng 1 thành phần liên thông.
	 * 
	 * Chạy vòng lặp để kiểm tra xem khi chạy thuật toán do tìm thì các đỉnh đã được
	 * thăp hay chưa.
	 * 
	 * Nếu khi chạy xong mà vẫn có đỉnh chưa được dò đến thì điểm tiếp theo được xét
	 * để chạy thuật toán dò là đỉnh đó
	 * 
	 * các đỉnh thuộc cùng thành phần sẽ được lưu vào 1 mảng và các đỉnh thuộc các
	 * thành phần khác nhau sẽ các nhau bằng số -1
	 * 
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
				} else {
					if (!dinhThuocCungThanhPhan.contains(i + 1)) {
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
			System.out.println("Đồ thị trên không liên thông và có số thành phần là: " + count);
			while (i < dinhThuocCungThanhPhan.size()) {
				System.out.print("Cac dinh thuoc thanh phan lien thong thu: " + thanhPhan + " la: ");
				for (; i < dinhThuocCungThanhPhan.size() && dinhThuocCungThanhPhan.get(i) != -1; i++) {
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

		while (!vertexes.isEmpty()) {
			vertex = vertexes.pop();
			result[i] = vertex + 1;
			i++;

			for (int index = numVertex - 1; index >= 0; index--) {
				if (this.matrix[vertex][index] != 0 && !this.visited[index] && !vertexes.contains(index)) {
					vertexes.add(index);
					visited[index] = true;
				}
			}
		}

		return result;
	}

	public int[] DFSGraph(int startVex) {
		int i = 0;
		result = new int[matrix.length];
		visited = new boolean[matrix.length];
		Stack<Integer> stack = new Stack<>();

		stack.add(startVex);
		visited[startVex] = true;

		while (!stack.isEmpty()) {
			vertex = stack.pop();
			result[i] = vertex + 1;
			i++;

			for (int index = numVertex - 1; index >= 0; index--) {
				if (this.matrix[vertex][index] != 0 && !this.visited[index]) {
					stack.add(index);
					visited[index] = true;
				}
			}
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

	/**
	 * Kiểm tra tính liên thông của đồ thị
	 * 
	 * @return
	 */
	public boolean isConnected() {
		this.DFSGraph();

		for (int i = 0; i < numVertex; i++) {
			if (!visited[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Tìm đường đi từ đỉnh s đến đỉnh t bằng thuật toán DFS
	 * 
	 * @param s
	 * @param t
	 */
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
	}

	/**
	 * Tìm đường đi từ đỉnh s đến đỉnh t bằng thuật toán BFS
	 * 
	 * @param s
	 * @param t
	 */
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

	/**
	 * Phương thức checkBipartiteGraph dùng để kiểm tra xem 1 đồ thị có liên trong
	 * hay không.
	 * 
	 * @return true nếu đồ thị là đồ thị hai phần, false nếu không phải.
	 */
	public boolean checkBipartiteGraph() {
		Set<Integer> x = new HashSet<>();
		Set<Integer> y = new HashSet<>();
		x.add(0);
		while (true) {
			for (int e : x) {
				y.addAll(diTimCacDinhLienThong1(e));
			}

			if (isHollow(x, y)) {
				if (x.size() + y.size() == numVertex) {
					return true;
				} else {
					x.removeAll(x);
					x.addAll(y);
					y.removeAll(y);
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * Xét sự giao nhau của 2 mảng.
	 * 
	 * Kiểm tra xem 2 Set này có thành phần nào của Set y chứa trứa trong Set x hay
	 * không.
	 * 
	 * Dùng phương thức retainAll để kiểm tra phần giao của 2 Set. Nó hoạt động bằng
	 * các kiểm tra xem có phần tử nào của y trong x không Các phần tử chung đó sẽ
	 * lưu vào x. Ta tạo 1 Set khác và add các phần tử của x vào và thực hiện kiểm
	 * tra để không ảnh hưởng đến mảng x. Chỉ cần 1 phần từ của y có trong x thì
	 * phương thức sẽ trả về true và ngược lại.
	 * 
	 * @param <E>
	 * @param x
	 * @param y
	 * @return false nếu có 1 thành phần của y có trong
	 * @return true nếu không có thành phần nào của y có trong x.
	 */
	public <E> boolean isHollow(Set<E> x, Set<E> y) {
		Set<E> intersection = new HashSet<>(x);
		return intersection.retainAll(y);
	}

	@Override
	public String toString() {
		printMatrix();
		return "";
	}

	/**
	 * Viết phương thức kiểm tra đồ thị G có chu trình Euler hay không?
	 * 
	 * @return
	 */
	public abstract boolean checkCycleEuler();

	/**
	 * Viết phương thức kiểm tra đồ thị G có đường đi Euler hay không?
	 * 
	 * @return
	 */
	public abstract boolean checkPathEuler();

	/**
	 * Phương thức sẽ xóa 1 chu trình nhỏ bắt đầu từ đỉnh v ra khỏi matrix
	 * 
	 * @param v
	 * @param matrix
	 * @return
	 */
	public abstract ArrayList<Integer> removeSubCycle(int v, int[][] matrix);

	/**
	 * Nối cái chu trình con của chu trình Euler lại
	 * 
	 * @param eulerCycles
	 */
	public void joinEulerCycles(ArrayList<ArrayList<Integer>> eulerCycles) {
		LinkedList<Integer> link = new LinkedList<>();
		link.addAll(eulerCycles.get(0));
		eulerCycles.remove(0);

		while (!eulerCycles.isEmpty()) {
			for (int i = 0; i < eulerCycles.size(); i++) {
				int obj = eulerCycles.get(i).get(0);
				if (link.contains(obj)) {
					int index = link.indexOf(obj);
					link.remove(index);
					for (int j = 0; j < eulerCycles.get(i).size(); j++) {
						link.add(index + j, eulerCycles.get(i).get(j));
					}
					eulerCycles.remove(i);
				}
			}
		}

		for (int i = 0; i < link.size() - 1; i++) {
			System.out.print(link.get(i) + "->");
		}
		System.out.println(link.getLast());
	}

	/**
	 * Viết phương thức tìm chu trình Euler của đồ thị G?
	 */
	public void findCycleEuler(int v) {
		int[][] coppy = this.cloneMatrix();
		ArrayList<ArrayList<Integer>> re = new ArrayList<>();

		if (this.checkCycleEuler()) {
			a: while (true) {
				ArrayList<Integer> subCycle = removeSubCycle(v, coppy);
				re.add(subCycle);

				for (int i = 0; i < numVertex; i++) {
					for (int j = 0; j < this.matrix[i].length; j++) {
						if (coppy[i][j] != 0) {
							v = i + 1;
							continue a;
						}
					}
				}
				break;
			}
		}

		joinEulerCycles(re);
	}

	public void findCycleEulerStack(int v) {
		ArrayList<Integer> cycle = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();
		stack.add(v);
		int[][] coppy = this.cloneMatrix();

		if (this.checkCycleEuler()) {
			a: while (!stack.isEmpty()) {
				v = stack.peek();
				b: for (int i = 0; i < matrix.length; i++) {
					if (matrix[v - 1][i] != 0) {
						stack.add(i + 1);
						this.removeEdge(v, i + 1);
						continue a;
					}
				}

				cycle.add(v);
				stack.pop();
			}

			for(int i = cycle.size() - 1; i > 0; i--) {
				System.out.print(cycle.get(i) + "->");
			}
			System.out.println(cycle.get(0));
			this.matrix = coppy;
			return;
		}

		System.out.println("Đồ thị không có chu trình Euler");
	}

	/**
	 * Viết phương thức tìm đường đi Euler của đồ thị G?
	 */
	public void findPathEuler() {
		int v = 1;
		int min = numVertex;
		for(int i = 0; i < numVertex; i++) {
			if(deg(i + 1)%2 == 1 && deg(i + 1) < min) {
				min = deg(i + 1);
				v = i + 1;
			}
		}
		
		ArrayList<Integer> path = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();
		stack.add(v);
		int[][] coppy = this.cloneMatrix();

		if (this.checkPathEuler()) {
			a: while (!stack.isEmpty()) {
				v = stack.peek();
				for (int i = 0; i < matrix.length; i++) {
					if (matrix[v - 1][i] != 0) {
						stack.add(i + 1);
						this.removeEdge(v, i + 1);
						continue a;
					}
				}
				
				path.add(v);
				stack.pop();
			}

			for(int i = path.size() - 1; i > 0; i--) {
				System.out.print(path.get(i) + "->");
			}
			System.out.println(path.get(0));
			this.matrix = coppy;
			return;
		}

		System.out.println("Đồ thị không có đường đi Euler");
	}

	public int[][] cloneMatrix() {
		int resul[][] = new int[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				resul[i][j] = matrix[i][j];
			}
		}

		return resul;
	}
}
