package tuan1;

import java.io.IOException;
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
	public boolean checkUnGraph(int[][] matrix) {
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
	public void addEdge(int[][] matrix, int v1, int v2) {
		if(v1 >= 0 && v2 >= v2 && v1 < matrix.length && v2 < matrix.length) {
			matrix[v1][v2]++;
			matrix[v2][v1]++;
		}else {
			System.out.println("Vị trí nhập vào không hợp lệ!");
		}
		
	}


	@Override
	public void removeEdge(int[][] matrix, int v1, int v2) {
		if(v1 >= 0 && v2 >= v2 && v1 < matrix.length && v2 < matrix.length) {
			matrix[v1][v2]--;
			matrix[v2][v1]--;
		}else {
			System.out.println("Vị trí nhập vào không hợp lệ!");
		}
	}
	
	@Override
	public int deg(int v) {
		int deg = 0;
		if(v <= matrix.length && v > 0) {
			for (int i = 0; i < matrix.length; i++) {
				if(i == v && matrix[i][i] != 0) {
					deg += matrix[i][v - 1]*2;
				}else {
					deg += matrix[i][v - 1];
				}
			}
			return deg;			
		}else {
			return -1;
		}
		System.out.println("chinh sua cho nay na");
	}
}
