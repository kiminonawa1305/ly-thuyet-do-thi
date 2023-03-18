package tuan5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Đây là đồ thị có hướng
 * 
 * @author Tu
 *
 */
public class DnGraph extends Graph {

	public DnGraph(String pathFile) throws IOException {
		super(pathFile);
	}

	@Override
	public boolean checkUnGraph() {
		return false;
	}

	/**
	 * Phương thức addEdge dùng để thêm cạnh vào đồ thị với chiều sẽ đi từ v1 đến
	 * v2;
	 */
	@Override
	public void addEdge(int v1, int v2) {
		if (v1 > 0 && v2 > 0 && v1 <= matrix.length && v2 <= matrix.length) {
			matrix[v1 - 1][v2 - 1]++;
		} else {
			System.out.println("Vị trí đưa vào không hợp lệ");
		}
	}

	/**
	 * Phương thức removeEdge dùng để xóa cạnh vào đồ thị với chiều sẽ đi từ v1 đến
	 * v2;
	 */
	@Override
	public void removeEdge(int v1, int v2) {
		if (v1 > 0 && v2 > 0 && v1 <= matrix.length && v2 <= matrix.length) {
			if (matrix[v1 - 1][v2 - 1] == 0) {
				System.out.println("Không có cạnh đi từ " + v1 + " đến " + v2);
			} else {
				matrix[v1 - 1][v2 - 1]--;
			}
		} else {
			System.out.println("Vị trí đưa vào không hợp lệ");
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
					deg += matrix[i][v - 1] + matrix[v - 1][i];
				}
			}
			return deg;
		} else {
			return -1;
		}
	}

	public int degPlus(int v) {
		int deg = 0;
		for (int vum : matrix[v - 1])
			deg += vum;
		return deg;
	}

	public int degSub(int v) {
		int deg = 0;
		for (int i = 0; i < numVertex; i++) {
			if (matrix[i][v - 1] != 0) {
				deg += matrix[i][v - 1];
			}
		}
		return deg;
	}

	public boolean checkCycleEuler() {
		if (this.isConnected()) {
			int i = 0;
			for (; i < numVertex && this.degPlus(i + 1) == this.degSub(i + 1); i++)
				;
			return i == numVertex;
		}
		return false;
	}

	/**
	 * Biến count dùng để đếm số lần thảo mảng điều kiện degPlus(x) == degSub(x) +
	 * 1; Nếu count == 2 thì đồ thị có đường đi Euler và không có chu trình.
	 */
	public boolean checkPathEuler() {
		int countPlus = 0;
		int countSub = 0;
		if (this.isConnected()) {
			int i = 0;
			for (; i < numVertex; i++) {
				if (degPlus(i + 1) != degSub(i + 1)) {
					if (degPlus(i + 1) == degSub(i + 1) + 1) {
						countPlus++;
						continue;
					}

					if (degSub(i + 1) == degPlus(i + 1) + 1) {
						countSub++;
						continue;
					}

					return false;
				}
			}
			return (countPlus == 1 && countSub == countPlus) || (countPlus == 0 && countSub == 0);
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
			for (; i < matrix.length && matrix[v - 1][i] == 0; i++)
				;

			if (i >= numVertex) {
				return null;
			}

			matrix[v - 1][i] = 0;
			v = i + 1;

			if (v == end) {
				break;
			}
		}

		re.add(v);
		return re;
	}

	@Override
	public boolean checkBipartiteGraphByPaintVerter() {
		return false;
	}

	@Override
	public boolean checkPathHamilton() {
		if (this.checkCycleHamilton()) {
			return true;
		}

		if (!this.isConnected()) {
			return false;
		}

		int degSub = 0;
		int degPlus = 0;
		int degPre = 0;
		for (int i = 1; i <= this.numVertex; i++) {
			if (deg(i) == 1) {
				if (degPlus(i) == 1) {
					degPlus++;
				}

				degSub++;
			} else {
				if (degPlus(i) != 0 && degSub(i) != 0) {
					degPre++;
				}
			}
		}

		return degPlus <= 1 && degSub <= 1 && degPre == this.numVertex - (degPlus - degSub);
	}

	@Override
	public boolean checkCycleHamilton() {
		for (int i = 1; i <= this.numVertex; i++) {
			if (degPlus(i) == 0 || degSub(i) == 0) {
				return false;
			}
		}

		return super.checkCycleHamilton();
	}
}
