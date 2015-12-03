import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ConvexHullGrahamScan {
	class Point implements Comparable<Point> {
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public void getPolarCoordinates(Point pole) {
			double x = this.x - pole.x;
			double y = this.y - pole.y;
			
			if ( y == 0 ) {
				polarAngle = x >= 0 ? 0 : Math.PI;
			} else if ( x == 0 ) {
				polarAngle = y > 0 ? Math.PI / 2 : Math.PI * 3 / 2;
			} else {
				polarAngle = y > 0 ? Math.atan2(y, x) : 
								Math.atan2(y, x) + Math.PI;
			}
			polarRadius = Math.sqrt(x *x + y * y);
		}
		
		public int compareTo(Point o) {
			if ( this.polarAngle < o.polarAngle ) {
				return -1;
			} else if ( this.polarAngle > o.polarAngle ) {
				return 1;
			} else {
				if ( this.polarRadius < o.polarRadius ) {
					return 1;
				} else if ( this.polarRadius > o.polarRadius ) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		
		public String toString() {
			return String.format("Cartesian: (%.2f, %.2f) / Polar: (%.2f PI, %.2f)", 
					new Object[] {x, y, polarAngle / Math.PI, polarRadius});
		}
		
		double x;
		double y;
		double polarAngle;
		double polarRadius;
	}
	
	public Point getPolePoint(Point[] points) {
		double maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		int minIndex = 0;
		
		for ( int i = 0; i < points.length; ++ i ) {
			Point currentPoint = points[i];
			if ( currentPoint.y < minY ) {
				minY = currentPoint.y;
				maxX = currentPoint.x;
				minIndex = i;
			} else if ( currentPoint.y == minY ) {
				if ( currentPoint.x > maxX ) {
					maxX = currentPoint.x;
					minIndex = i;
				}
			}
		}
		return points[minIndex];
	}
	
	public List<Point> getConvexHull(Point[] points) {
		if ( points.length < 2 ) {
			return null;
		}
		
		int startIndex = 0;
		int totalPoints = points.length;
		Point pole = points[startIndex ++];
		Stack<Point> candidatePoints = new Stack<Point>();
		candidatePoints.push(pole);
		
		for ( int i = startIndex; i < totalPoints; ++ i ) {
			Point currentPoint = points[i];
			
			if ( currentPoint.polarAngle != pole.polarAngle ) {
				startIndex = i;
				candidatePoints.push(currentPoint);
				break;
			}
		}
		for ( int i = startIndex; i < totalPoints; ++ i ) {
			Point currentPoint = points[i],
				  topPoint = candidatePoints.peek(),
				  nextToTopPoint = getNextToTopPoint(candidatePoints);
			
			if ( currentPoint.polarAngle == topPoint.polarAngle ) {
				continue;
			}
			while ( isPointInTriangle(pole, nextToTopPoint, currentPoint, topPoint) ) {
				candidatePoints.pop();
				topPoint = candidatePoints.peek();
				nextToTopPoint = getNextToTopPoint(candidatePoints);
			}
			candidatePoints.push(currentPoint);
		}
		return getConvexHullPoints(candidatePoints);
	}
	
	private Point getNextToTopPoint(Stack<Point> candidatePoints) {
		int totalPoints = candidatePoints.size();
		return candidatePoints.get(totalPoints - 2);
	}
	
	private boolean isPointInTriangle(Point pole, Point nextToTopPoint, 
			Point currentPoint, Point topPoint) {
		if ( pole == nextToTopPoint ) {
			return false;
		}
		double a = getPointOnLine(currentPoint, nextToTopPoint, pole);
		double b = getPointOnLine(currentPoint, nextToTopPoint, topPoint);
		
		return a * b >= 0;
	}
	
	private double getPointOnLine(Point p1, Point p2, Point p) {
		if ( p2.x == p1.x ) {
			return p.x - p1.x;
		}
		double k = (p2.y - p1.y) / (p2.x - p1.x);
		double b = p2.y - k * p2.x;
		
		return p.y - k * p.x - b;
	}
	
	private List<Point> getConvexHullPoints(Stack<Point> candidatePoints) {
		List<Point> convexHullPoints = new ArrayList<Point>();
		for ( Point p : candidatePoints ) {
			convexHullPoints.add(p);
		}
		return convexHullPoints; 
	}
	
	public static void main(String[] args) {
		if ( args.length != 1 ) {
			System.out.println("java ConvexHullGrahamScan n");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		Point[] points = new Point[n];
		
		ConvexHullGrahamScan chgs = new ConvexHullGrahamScan();
		for ( int i = 0; i < n; ++ i ) {
			double x = Math.random() * 100;
			double y = Math.random() * 100;
			points[i] = chgs.new Point(x, y);
		}
		Point pole = chgs.getPolePoint(points);
		for ( Point p : points ) {
			p.getPolarCoordinates(pole);
		}
		Arrays.sort(points);
		
		System.out.println("Candidate Points:");
		for ( Point p : points ) {
			System.out.println(p);
		}
		
		long startTime = System.currentTimeMillis();
		List<Point> convexHullPoints = chgs.getConvexHull(points);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Convex Hull Points:");
		for ( Point p : convexHullPoints ) {
			System.out.println(p);
		}
		System.out.println("Total Time: " + (endTime - startTime) + " ms");
	}
}
