package tuan4;

import java.io.IOException;
import java.util.Arrays;

public class Test {
	public static void main(String[] args) throws IOException {
		Graph unGraph1 = new UnGraph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\BaiTapTrenLop\\src\\tuan4\\do_thi_vo_huong");
		Graph unGraph2 = new UnGraph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\BaiTapTrenLop\\src\\tuan4\\do_thi_vo_huong_2");
		DnGraph dnGraph1 = new DnGraph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\BaiTapTrenLop\\src\\tuan4\\do_thi_co_huong");
		DnGraph dnGraph2 = new DnGraph("D:\\Tai_lieu_hoc_tap\\LiThuyetDoThi\\Code\\BaiTapTrenLop\\src\\tuan4\\do_thi_co_huong_2");
		System.out.println("Check đồ thị unGraph1 có chu trình Euler: " + unGraph1.checkCycleEuler());
		System.out.println("Check đồ thị unGraph1 có đường đi Euler: " + unGraph1.checkPathEuler());
		System.out.println("=============================");
		unGraph1.removeEdge(5, 4);
		System.out.println("Check đồ thị unGraph1 có chu trình Euler: " + unGraph1.checkCycleEuler());
		System.out.println("Check đồ thị unGraph1 có đường đi Euler: " + unGraph1.checkPathEuler());
		System.out.print("Đồ thị có đường đi Euler là: "); unGraph1.findPathEuler();
		System.out.println("=============================");
		System.out.println("Bậc trong cua đỉnh 1: " + dnGraph1.degPlus(1));
		System.out.println("Bậc ngoài cua đỉnh 1: " + dnGraph1.degSub(1));
		System.out.println("Check đồ thị dnGraph1 có đường đi Euler: " + dnGraph1.checkPathEuler());
		System.out.println("=============================");
		dnGraph1.removeEdge(1, 4);
		System.out.println("Check đồ thị có đường đi Euler: " + dnGraph1.checkPathEuler());
		System.out.println("=============================");
		System.out.println("Check đồ thị dnGraph2 có chu trình Euler: " + dnGraph2.checkCycleEuler());
		System.out.println("=============================");
		dnGraph2.removeEdge(4, 2);
		System.out.println("Check đồ thị dnGraph2 có chu trình Euler: " + dnGraph2.checkCycleEuler());
		System.out.println("=============================");
		System.out.print("Check đồ thị có chu trình Euler: ");
		unGraph2.findCycleEuler(1);
	}
}
