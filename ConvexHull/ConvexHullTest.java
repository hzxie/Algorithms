import java.util.Arrays;
import java.util.List;

public class ConvexHullTest {
	public static void main(String[] args) {
		if ( args.length != 1 ) {
			System.out.println("java ConvexHullTest n");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		ConvexHullBruteForce.Point[] chbfPoints = new ConvexHullBruteForce.Point[n];
		ConvexHullGrahamScan.Point[] chgsPoints = new ConvexHullGrahamScan.Point[n];
		ConvexHullDivideConquer.Point[] chdcPoints = new ConvexHullDivideConquer.Point[n];
		
		ConvexHullBruteForce chbf = new ConvexHullBruteForce();
		ConvexHullGrahamScan chgs = new ConvexHullGrahamScan();
		ConvexHullDivideConquer chdc = new ConvexHullDivideConquer();
		for ( int i = 0; i < n; ++ i ) {
			double x = (int)(Math.random() * 100);
			double y = (int)(Math.random() * 100);
			chbfPoints[i] = chbf.new Point(x, y);
			chgsPoints[i] = chgs.new Point(x, y);
			chdcPoints[i] = chdc.new Point(x, y);
		}
		
		/* Test Case 1 */
		/*
		chbfPoints[0] = chbf.new Point(8,5);
		chbfPoints[1] = chbf.new Point(5,2);
		chbfPoints[2] = chbf.new Point(8,2);
		chbfPoints[3] = chbf.new Point(9,3);
		chbfPoints[4] = chbf.new Point(7,4);
		chbfPoints[5] = chbf.new Point(2,1);
		chbfPoints[6] = chbf.new Point(4,7);
		chbfPoints[7] = chbf.new Point(2,5);
		chbfPoints[8] = chbf.new Point(1,3);
		chbfPoints[9] = chbf.new Point(3,3);
		chbfPoints[10] = chbf.new Point(1,1);
		chbfPoints[11] = chbf.new Point(2,2);
		chbfPoints[12] = chbf.new Point(7,8);
		chbfPoints[13] = chbf.new Point(5,4);
		chbfPoints[14] = chbf.new Point(3,6);
		chbfPoints[15] = chbf.new Point(8,1);
		chbfPoints[16] = chbf.new Point(9,1);
		chbfPoints[17] = chbf.new Point(1,2);

		chgsPoints[0] = chgs.new Point(8,5);
		chgsPoints[1] = chgs.new Point(5,2);
		chgsPoints[2] = chgs.new Point(8,2);
		chgsPoints[3] = chgs.new Point(9,3);
		chgsPoints[4] = chgs.new Point(7,4);
		chgsPoints[5] = chgs.new Point(2,1);
		chgsPoints[6] = chgs.new Point(4,7);
		chgsPoints[7] = chgs.new Point(2,5);
		chgsPoints[8] = chgs.new Point(1,3);
		chgsPoints[9] = chgs.new Point(3,3);
		chgsPoints[10] = chgs.new Point(1,1);
		chgsPoints[11] = chgs.new Point(2,2);
		chgsPoints[12] = chgs.new Point(7,8);
		chgsPoints[13] = chgs.new Point(5,4);
		chgsPoints[14] = chgs.new Point(3,6);
		chgsPoints[15] = chgs.new Point(8,1);
		chgsPoints[16] = chgs.new Point(9,1);
		chgsPoints[17] = chgs.new Point(1,2);
		*/
		
		/* Test Case 2 */
		/*
		chbfPoints[0] = chbf.new Point(6,10);
		chbfPoints[1] = chbf.new Point(1,5);
		chbfPoints[2] = chbf.new Point(1,10);
		chbfPoints[3] = chbf.new Point(2,9);
		chbfPoints[4] = chbf.new Point(2,9);
		chbfPoints[5] = chbf.new Point(3,6);
		chbfPoints[6] = chbf.new Point(4,5);
		chbfPoints[7] = chbf.new Point(5,5);
		chbfPoints[8] = chbf.new Point(9,4);
		chbfPoints[9] = chbf.new Point(3,2);
		chbfPoints[10] = chbf.new Point(5,6);
		chbfPoints[11] = chbf.new Point(5,8);
		chbfPoints[12] = chbf.new Point(6,2);
		chbfPoints[13] = chbf.new Point(6,10);

		chgsPoints[0] = chgs.new Point(1,5);
		chgsPoints[1] = chgs.new Point(1,10);
		chgsPoints[2] = chgs.new Point(2,9);
		chgsPoints[3] = chgs.new Point(2,9);
		chgsPoints[4] = chgs.new Point(3,6);
		chgsPoints[5] = chgs.new Point(4,5);
		chgsPoints[6] = chgs.new Point(5,5);
		chgsPoints[7] = chgs.new Point(9,4);
		chgsPoints[8] = chgs.new Point(3,2);
		chgsPoints[9] = chgs.new Point(5,6);
		chgsPoints[10] = chgs.new Point(5,8);
		chgsPoints[11] = chgs.new Point(6,2);
		chgsPoints[12] = chgs.new Point(6,10);
		chgsPoints[13] = chgs.new Point(6,10);
		*/
		
		/* Test Case 3 */
		/*chbfPoints[0] = chbf.new Point(5, 0);
		chbfPoints[1] = chbf.new Point(5, 0);
		chbfPoints[2] = chbf.new Point(7, 7);
		chbfPoints[3] = chbf.new Point(5, 1);
		chbfPoints[4] = chbf.new Point(4, 9);
		chbfPoints[5] = chbf.new Point(4, 6);
		chbfPoints[6] = chbf.new Point(0, 5);
		chbfPoints[7] = chbf.new Point(4, 1);
		chbfPoints[8] = chbf.new Point(2, 1);
		chbfPoints[9] = chbf.new Point(4, 0);

		chgsPoints[0] = chgs.new Point(5, 0);
		chgsPoints[1] = chgs.new Point(5, 0);
		chgsPoints[2] = chgs.new Point(7, 7);
		chgsPoints[3] = chgs.new Point(5, 1);
		chgsPoints[4] = chgs.new Point(4, 9);
		chgsPoints[5] = chgs.new Point(4, 6);
		chgsPoints[6] = chgs.new Point(0, 5);
		chgsPoints[7] = chgs.new Point(4, 1);
		chgsPoints[8] = chgs.new Point(2, 1);
		chgsPoints[9] = chgs.new Point(4, 0);

		chdcPoints[0] = chdc.new Point(5, 0);
		chdcPoints[1] = chdc.new Point(5, 0);
		chdcPoints[2] = chdc.new Point(7, 7);
		chdcPoints[3] = chdc.new Point(5, 1);
		chdcPoints[4] = chdc.new Point(4, 9);
		chdcPoints[5] = chdc.new Point(4, 6);
		chdcPoints[6] = chdc.new Point(0, 5);
		chdcPoints[7] = chdc.new Point(4, 1);
		chdcPoints[8] = chdc.new Point(2, 1);
		chdcPoints[9] = chdc.new Point(4, 0);*/
		
		/* Test Case 4 */
		/*chbfPoints[0] = chbf.new Point(9, 1);
		chbfPoints[1] = chbf.new Point(8, 1);
		chbfPoints[2] = chbf.new Point(9, 7);
		chbfPoints[3] = chbf.new Point(8, 6);
		chbfPoints[4] = chbf.new Point(8, 3);
		chbfPoints[5] = chbf.new Point(6, 7);
		chbfPoints[6] = chbf.new Point(2, 7);
		chbfPoints[7] = chbf.new Point(2, 4);
		chbfPoints[8] = chbf.new Point(3, 3);
		chbfPoints[9] = chbf.new Point(2, 1);

		chgsPoints[0] = chgs.new Point(9, 1);
		chgsPoints[1] = chgs.new Point(8, 1);
		chgsPoints[2] = chgs.new Point(9, 7);
		chgsPoints[3] = chgs.new Point(8, 6);
		chgsPoints[4] = chgs.new Point(8, 3);
		chgsPoints[5] = chgs.new Point(6, 7);
		chgsPoints[6] = chgs.new Point(2, 7);
		chgsPoints[7] = chgs.new Point(2, 4);
		chgsPoints[8] = chgs.new Point(3, 3);
		chgsPoints[9] = chgs.new Point(2, 1);

		chdcPoints[0] = chdc.new Point(9, 1);
		chdcPoints[1] = chdc.new Point(8, 1);
		chdcPoints[2] = chdc.new Point(9, 7);
		chdcPoints[3] = chdc.new Point(8, 6);
		chdcPoints[4] = chdc.new Point(8, 3);
		chdcPoints[5] = chdc.new Point(6, 7);
		chdcPoints[6] = chdc.new Point(2, 7);
		chdcPoints[7] = chdc.new Point(2, 4);
		chdcPoints[8] = chdc.new Point(3, 3);
		chdcPoints[9] = chdc.new Point(2, 1);*/
		
		/* Test Case 5 */
		/*chbfPoints[0] = chbf.new Point(3.00, 1.00);
		chbfPoints[1] = chbf.new Point(6.00, 2.00);
		chbfPoints[2] = chbf.new Point(5.00, 2.00);
		chbfPoints[3] = chbf.new Point(6.00, 4.00);
		chbfPoints[4] = chbf.new Point(9.00, 9.00);
		chbfPoints[5] = chbf.new Point(6.00, 6.00);
		chbfPoints[6] = chbf.new Point(4.00, 3.00);
		chbfPoints[7] = chbf.new Point(5.00, 7.00);
		chbfPoints[8] = chbf.new Point(3.00, 7.00);
		chbfPoints[9] = chbf.new Point(0.00, 3.00);

		chgsPoints[0] = chgs.new Point(3.00, 1.00);
		chgsPoints[1] = chgs.new Point(6.00, 2.00);
		chgsPoints[2] = chgs.new Point(5.00, 2.00);
		chgsPoints[3] = chgs.new Point(6.00, 4.00);
		chgsPoints[4] = chgs.new Point(9.00, 9.00);
		chgsPoints[5] = chgs.new Point(6.00, 6.00);
		chgsPoints[6] = chgs.new Point(4.00, 3.00);
		chgsPoints[7] = chgs.new Point(5.00, 7.00);
		chgsPoints[8] = chgs.new Point(3.00, 7.00);
		chgsPoints[9] = chgs.new Point(0.00, 3.00);

		chdcPoints[0] = chdc.new Point(3.00, 1.00);
		chdcPoints[1] = chdc.new Point(6.00, 2.00);
		chdcPoints[2] = chdc.new Point(5.00, 2.00);
		chdcPoints[3] = chdc.new Point(6.00, 4.00);
		chdcPoints[4] = chdc.new Point(9.00, 9.00);
		chdcPoints[5] = chdc.new Point(6.00, 6.00);
		chdcPoints[6] = chdc.new Point(4.00, 3.00);
		chdcPoints[7] = chdc.new Point(5.00, 7.00);
		chdcPoints[8] = chdc.new Point(3.00, 7.00);
		chdcPoints[9] = chdc.new Point(0.00, 3.00);*/
		
		/* Test Case 6 */
		/*chbfPoints[0] = chbf.new Point (6.00, 0.00);
		chbfPoints[1] = chbf.new Point (9.00, 3.00);
		chbfPoints[2] = chbf.new Point (7.00, 1.00);
		chbfPoints[3] = chbf.new Point (8.00, 4.00);
		chbfPoints[4] = chbf.new Point (7.00, 5.00);
		chbfPoints[5] = chbf.new Point (5.00, 5.00);
		chbfPoints[6] = chbf.new Point (4.00, 9.00);
		chbfPoints[7] = chbf.new Point (3.00, 9.00);
		chbfPoints[8] = chbf.new Point (2.00, 2.00);
		chbfPoints[9] = chbf.new Point (4.00, 1.00);

		chgsPoints[0] = chgs.new Point (6.00, 0.00);
		chgsPoints[1] = chgs.new Point (9.00, 3.00);
		chgsPoints[2] = chgs.new Point (7.00, 1.00);
		chgsPoints[3] = chgs.new Point (8.00, 4.00);
		chgsPoints[4] = chgs.new Point (7.00, 5.00);
		chgsPoints[5] = chgs.new Point (5.00, 5.00);
		chgsPoints[6] = chgs.new Point (4.00, 9.00);
		chgsPoints[7] = chgs.new Point (3.00, 9.00);
		chgsPoints[8] = chgs.new Point (2.00, 2.00);
		chgsPoints[9] = chgs.new Point (4.00, 1.00);

		chdcPoints[0] = chdc.new Point (6.00, 0.00);
		chdcPoints[1] = chdc.new Point (9.00, 3.00);
		chdcPoints[2] = chdc.new Point (7.00, 1.00);
		chdcPoints[3] = chdc.new Point (8.00, 4.00);
		chdcPoints[4] = chdc.new Point (7.00, 5.00);
		chdcPoints[5] = chdc.new Point (5.00, 5.00);
		chdcPoints[6] = chdc.new Point (4.00, 9.00);
		chdcPoints[7] = chdc.new Point (3.00, 9.00);
		chdcPoints[8] = chdc.new Point (2.00, 2.00);
		chdcPoints[9] = chdc.new Point (4.00, 1.00);*/
		
		ConvexHullGrahamScan.Point pole = chgs.getPolePoint(chgsPoints);
		for ( ConvexHullGrahamScan.Point p : chgsPoints ) {
			p.getPolarCoordinates(pole);
		}
		Arrays.sort(chgsPoints);
		
		System.out.println("Candidate Points:");
		for ( ConvexHullGrahamScan.Point p : chgsPoints ) {
			System.out.println(p);
		}
		
		List<ConvexHullBruteForce.Point> chbfConvexHullPoints = chbf.getConvexHull(chbfPoints);
		System.out.println("\n[Brute Force] Convex Hull Points:");
		for ( ConvexHullBruteForce.Point p : chbfConvexHullPoints ) {
			System.out.println(p);
		}
		
		List<ConvexHullGrahamScan.Point> chgsConvexHullPoints = chgs.getConvexHull(chgsPoints);
		System.out.println("\n[Graham Scan] Convex Hull Points:");
		for ( ConvexHullGrahamScan.Point p : chgsConvexHullPoints ) {
			System.out.println(p);
		}
		
		List<ConvexHullDivideConquer.Point> chdcConvexHullPoints = chdc.getConvexHull(chdcPoints);
		System.out.println("\n[Divide and Conquer] Convex Hull Points:");
		for ( ConvexHullDivideConquer.Point p : chdcConvexHullPoints ) {
			System.out.println(p);
		}
	}
}
