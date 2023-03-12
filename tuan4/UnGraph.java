package tuan4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Đây là đồ thị vô hướng
 * 
 * @author Tu
 */
public class UnGraph extends Graph {

	public UnGraph(String pathFile) throws IOException {
		super(pathFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkUnGraph() {
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != matrix[j][i]) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void addEdge(int v1, int v2) {
		if (v1 >= 0 && v2 >= v2 && v1 < matrix.length && v2 < matrix.length) {
			matrix[v1][v2]++;
			matrix[v2][v1]++;
		} else {
			System.out.println("Vị trí nhập vào không hợp lệ!");
		}

	}

	@Override
	public void removeEdge(int v1, int v2) {
		if (v1 >= 0 && v2 >= v2 && v1 < matrix.length && v2 < matrix.length) {
			matrix[v1][v2]--;
			matrix[v2][v1]--;
		} else {
			System.out.println("Vị trí nhập vào không hợp lệ!");
		}
	}

	@Override
	public int deg(int v) {
		int deg = 0;
		if (v <= matrix.length && v > 0) {
			for (int i = 0; i < matrix.length; i++) {
				if (i == v && matrix[i][i] != 0) {
					deg += matrix[i][v - 1] * 2;
				} else {
					deg += matrix[i][v - 1];
				}
			}
			return deg;
		} else {
			return -1;
		}
	}

	@Override
	public boolean checkCycleEuler() {
		if (this.isConnected()) {
			int i = 0;
			for (; i < numVertex && deg(i + 1) % 2 == 0; i++)
				;
			return i == numVertex;
		}

		return false;
	}

	@Override
	/**
	 * Biến count dùng để đếm tổng số đỉnh bậc lẽ. Nếu nó khác 2 thì nó không có
	 * đường đi Euler
	 */
	public boolean checkPathEuler() {
		int count = 0;
		if (this.isConnected()) {
			int i = 0;
			for (; i < numVertex; i++) {
				if (deg(i + 1) % 2 == 1) {
					count++;
				}
			}

			return count == 2;
		}

		return false;
	}

	/**
	 * Phương thức sẽ xóa 1 chu trình nhỏ bắt đầu từ đỉnh v ra khỏi matrix
	 * 
	 * @param v
	 * @param matrix
	 * @return
	 */
	public ArrayList<Integer> removeSubCycle(int v, int[][] matrix) {
		ArrayList<Integer> re = new ArrayList<>();
		if (v < 0 && v > numVertex) {
			return null;
		}

		int end = v;
		int times = 0;
		while (true) {
			if (times > this.numEdges()) {
				return null;
			}

			re.add(v);
			times++;
			int i = 0;
			for (; i < numVertex && matrix[v - 1][i] == 0; i++)
				;

			if (i >= numVertex) {
				return null;
			}

			matrix[v - 1][i] = 0;
			matrix[i][v - 1] = 0;
			v = i + 1;

			if (v == end) {
				break;
			}
		}

		re.add(v);
		return re;
	}

	@Override
	public void findPathEuler() {

	}
}
