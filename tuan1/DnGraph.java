package tuan1;

import java.io.IOException;

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
	public boolean checkUnGraph(int[][] matrix) {
		return false;
	}

	/**
	 * Phương thức addEdge dùng để thêm cạnh vào đồ thị với chiều sẽ đi từ v1 đến
	 * v2;
	 */
	@Override
	public void addEdge(int[][] matrix, int v1, int v2) {
		if (v1 >= 0 && v2 >= v2 && v1 < matrix.length && v2 < matrix.length) {
			matrix[v2][v1]++;
		} else {
			System.out.println("Vị trí đưa vào không hợp lệ");
		}
	}

	/**
	 * Phương thức removeEdge dùng để xóa cạnh vào đồ thị với chiều sẽ đi từ v1 đến
	 * v2;
	 */
	@Override
	public void removeEdge(int[][] matrix, int v1, int v2) {
		if (v1 >= 0 && v2 >= v2 && v1 < matrix.length && v2 < matrix.length) {
			if (matrix[v2][v1] == 0) {
				System.out.println("Không có cạnh đi từ v1 đến v2");
			} else {
				matrix[v2][v1]--;
			}
		} else {
			System.out.println("Vị trí đưa vào không hợp lệ");
		}
	}

	@Override
	public int deg(int v) {
		int deg = 0;
		if(v <= matrix.length && v > 0) {
			for (int i = 0; i < matrix.length; i++) {
				deg += matrix[i][v - 1] + matrix[v - 1][i];
			}
			return deg;			
		}else {
			return -1;
		}
	}
}
