import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvexHullBruteForce {
	class Point implements Comparable<Point> {
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
			this.isSelected = true;
		}
		
		public int compareTo(Point o) {
			return this.x == o.x ? 0 :
					this.x < o.x ? -1 : 1;
		}
		
		public boolean equals(Object o) {
			if ( o instanceof Point ) {
				Point p = (Point)o;
				if ( p.x == this.x && p.y == this.y ) {
					return true;
				}
			}
			return false;
		}
		
		public String toString() {
			return String.format("(%.2f, %.2f)", 
					new Object[] {x, y});
		}
		
		double x;
		double y;
		boolean isSelected;
	}
	
	public List<Point> getConvexHull(Point[] points) {
		int totalPoints = points.length;
		Point p0 = getPointOnEdge(points);
		
		for ( int i = 0; i < totalPoints - 2; ++ i ) {
			for ( int j = i + 1; j < totalPoints - 1; ++ j ) {
				for ( int k = j + 1; k < totalPoints; ++ k ) {
					if ( points[i] == p0 || points[j] == p0 || points[k] == p0 ) {
						continue;
					} else if ( !points[i].isSelected || !points[j].isSelected
							 || !points[k].isSelected ) {
						continue;
					}
					
					Point p1 = points[i];
					Point p2 = points[j];
					Point p3 = points[k];
					
					if ( isPointInTriangle(p0, p1, p2, p3) ) {
						p3.isSelected = false;
					} else if ( isPointInTriangle(p0, p1, p3, p2) ) {
						p2.isSelected = false;
					} else if ( isPointInTriangle(p0, p2, p3, p1) ) {
						p1.isSelected = false;
					}
				}
			}
		}
		return getConvexHullPoints(points);
	}
	
	private Point getPointOnEdge(Point[] points) {
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
	
	private double getPointOnLine(Point p1, Point p2, Point p) {
		if ( p2.x == p1.x ) {
			return p.x - p1.x;
		}
		double k = (p2.y - p1.y) / (p2.x - p1.x);
		double b = p2.y - k * p2.x;
		
		return p.y - k * p.x - b;
	}
	
	private boolean isPointInTriangle(Point p1, Point p2, Point p3, Point p) {
		double a = getPointOnLine(p1, p2, p);
		double b = getPointOnLine(p1, p2, p3);
		double c = getPointOnLine(p1, p3, p);
		double d = getPointOnLine(p1, p3, p2);
		double e = getPointOnLine(p2, p3, p);
		double f = getPointOnLine(p2, p3, p1);
		
		return a * b >= 0 && c * d >= 0 && e * f >= 0 && 
				(b != 0 && d != 0 && f != 0);
	}
	
	private List<Point> getConvexHullPoints(Point[] points) {
		List<Point> convexHullPoints = new ArrayList<Point>();
		List<Point> aboveLinePoints = new ArrayList<Point>();
		List<Point> belowLinePoints = new ArrayList<Point>();
		double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
		int minIndex = 0, maxIndex = 0;
		for ( int i = 0; i < points.length; ++ i ) {
			if ( points[i].x < minX ) {
				minX = points[i].x;
				minIndex = i;
			}
			if ( points[i].x > maxX ) {
				maxX = points[i].x;
				maxIndex = i;
			}
		}
		
		Point leftPoint = points[minIndex];
		Point rightPoint = points[maxIndex];
		for ( Point p : points ) {
			if ( !p.isSelected ) {
				continue;
			}
			if ( getPointOnLine(leftPoint, rightPoint, p) >= 0 ) {
				aboveLinePoints.add(p);
			} else {
				belowLinePoints.add(p);
			}
		}
		Collections.sort(aboveLinePoints);
		Collections.sort(belowLinePoints);
		
		for ( int i = 0; i < belowLinePoints.size(); ++ i ) {
			convexHullPoints.add(belowLinePoints.get(i));
		}
		for ( int i = aboveLinePoints.size() - 1; i >= 0; -- i ) {
			convexHullPoints.add(aboveLinePoints.get(i));
		}
		return convexHullPoints;
	}
	
	public static void main(String[] args) {
		if ( args.length != 1 ) {
			System.out.println("java ConvexHullBruteForce n");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		Point[] points = new Point[n];
		
		ConvexHullBruteForce chbf = new ConvexHullBruteForce();
		System.out.println("Candidate Points:");
		for ( int i = 0; i < n; ++ i ) {
			double x = Math.random() * 100;
			double y = Math.random() * 100;
			Point p = chbf.new Point(x, y);
			points[i] = p;
			System.out.println(p);
		}
		
		long startTime = System.currentTimeMillis();
		List<Point> convexHullPoints = chbf.getConvexHull(points);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Convex Hull Points:");
		for ( Point p : convexHullPoints ) {
			System.out.println(p);
		}
		System.out.println("Total Time: " + (endTime - startTime) + " ms");
	}
}
