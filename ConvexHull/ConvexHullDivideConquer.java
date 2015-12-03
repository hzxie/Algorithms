import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ConvexHullDivideConquer {
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
		
		public void getPolarCoordinates(Point pole, Point anotherPoint) {
			double x = this.x - pole.x;
			double y = this.y - pole.y;
			polarRadius = Math.sqrt(x * x + y * y);
			
			// TODO What if anotherPoint == pole
			double cosine = 1;
			if ( polarRadius != 0 ) {
				Point polarAxis = new Point(anotherPoint.x - pole.x, anotherPoint.y - pole.y);
				double polarAxisLength = Math.sqrt(polarAxis.x * polarAxis.x + polarAxis.y * polarAxis.y);
				
				cosine = ((x * polarAxis.x) + (y * polarAxis.y)) / (polarRadius * polarAxisLength);
				if ( cosine > 1 ) {
					cosine = 1;
				} else if ( cosine < -1 ) {
					cosine = -1;
				}
			}
			
			double pointOnLine = getPointOnLine(pole, anotherPoint, this);
			if ( pointOnLine == 0 ) {
				polarAngle = cosine > 0 ? 0 : Math.PI;
			} else if ( pointOnLine > 0 ) {
				polarAngle = Math.acos(cosine);
			} else {
				polarAngle = 2 * Math.PI - Math.acos(cosine);
			}
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
	
	public List<Point> getConvexHull(Point[] points) {
		if ( points.length <= 2 ) {
			return getPointsList(points);
		}
		
		// Divide
		List<Point> leftConvexHullCandidatePoints = new ArrayList<Point>();
		List<Point> rightConvexHullCandidatePoints = new ArrayList<Point>();
		double medianXValue = getMedianPoint(points);
		for ( Point p : points ) {
			if ( p.x <= medianXValue ) {
				leftConvexHullCandidatePoints.add(p);
			} else {
				rightConvexHullCandidatePoints.add(p);
			}
		}
		
		// Solve
		List<Point> leftConvexHullPoints =  leftConvexHullCandidatePoints;
		List<Point> rightConvexHullPoints =  rightConvexHullCandidatePoints;
		if ( leftConvexHullCandidatePoints.size() != 0 && 
				rightConvexHullCandidatePoints.size() != 0 ) {
			leftConvexHullPoints =  getConvexHull(
					getPointsArray(leftConvexHullCandidatePoints));
			rightConvexHullPoints =  getConvexHull(
					getPointsArray(rightConvexHullCandidatePoints));
		}
		
		// Conquer
		Point pole = getPolePoint(leftConvexHullPoints);
		Point leftMinYPoint = getMinYPoint(leftConvexHullPoints);
		for ( Point p : points ) {
			p.getPolarCoordinates(pole, leftMinYPoint);
		}
		
		List<Point> rightPointAboveLine = new ArrayList<Point>();
		List<Point> rightPointUnderLine = new ArrayList<Point>();
		if ( !rightConvexHullPoints.isEmpty() ) {
			Point rightMinYPoint = getMinYPoint(rightConvexHullPoints);
			Point rightMaxYPoint = getMaxYPoint(rightConvexHullPoints);
			for ( Point p : rightConvexHullPoints ) {
				if ( getPointOnLine(rightMaxYPoint, rightMinYPoint, p) >= 0 ) {
					rightPointAboveLine.add(p);
				} else {
					rightPointUnderLine.add(p);
				}
			}
		}
		List<Point> mergedConvexHullCandidatePoints = threeWayMergeSort(
				leftConvexHullPoints, rightPointAboveLine, rightPointUnderLine);
		return getConvexHullUsingGrahamScan(
				getPointsArray(mergedConvexHullCandidatePoints));
	}
	
	private double getMedianPoint(Point[] points) {
		int n = points.length;
		double[] xValues = new double[n];
		
		for ( int i = 0; i < n; ++ i ) {
			xValues[i] = points[i].x;
		}
		Arrays.sort(xValues);
		
		if ( n % 2 == 0 ) {
			return (xValues[n / 2 - 1] + xValues[n / 2]) / 2;
		} else {
			return xValues[n / 2];
		}
	}
	
	private List<Point> getPointsList(Point[] points) {
		List<Point> pointsList = new ArrayList<Point>();
		for ( Point p : points ) {
			pointsList.add(p);
		}
		return pointsList;
	}
	
	private Point[] getPointsArray(List<Point> points) {
		int n = points.size();
		Point[] pointsArray = new Point[n];
		for ( int i = 0; i < n; ++ i ) {
			pointsArray[i] = points.get(i);
		}
		return pointsArray;
	}
	
	private Point getPolePoint(List<Point> points) {
		int totalPoints = points.size();
		double totalX = 0;
		double totalY = 0;
		
		for ( Point p : points ) {
			totalX += p.x;
			totalY += p.y;
		}
		return new Point(totalX / totalPoints, totalY / totalPoints);
	}
	
	public Point getMinYPoint(List<Point> points) {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		int minIndex = 0;
		
		for ( int i = 0; i < points.size(); ++ i ) {
			Point currentPoint = points.get(i);
			if ( currentPoint.y < minY ) {
				minY = currentPoint.y;
				minIndex = i;
			} else if ( currentPoint.y == minY ) {
				if ( currentPoint.x < minX ) {
					minX = currentPoint.x;
					minIndex = i;
				}
			}
		}
		return points.get(minIndex);
	}
	
	public Point getMaxYPoint(List<Point> points) {
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		int maxIndex = 0;
		
		for ( int i = 0; i < points.size(); ++ i ) {
			Point currentPoint = points.get(i);
			if ( currentPoint.y > maxY ) {
				maxY = currentPoint.y;
				maxIndex = i;
			} else if ( currentPoint.y == maxY ) {
				if ( currentPoint.x > maxX ) {
					maxX = currentPoint.x;
					maxIndex = i;
				}
			}
		}
		return points.get(maxIndex);
	}
	
	private double getPointOnLine(Point p1, Point p2, Point p) {
		if ( p2.x == p1.x ) {
			return p.x - p1.x;
		}
		double k = (p2.y - p1.y) / (p2.x - p1.x);
		double b = p2.y - k * p2.x;
		
		return p.y - k * p.x - b;
	}
	
	private List<Point> threeWayMergeSort(List<Point> leftPoints, 
			List<Point> rightPoints1, List<Point> rightPoints2) {
		List<Point> rightPoints = twoWayMergeSort(rightPoints1, rightPoints2);
		List<Point> mergedPoints = twoWayMergeSort(leftPoints, rightPoints);
		return mergedPoints;
	}
	
	private List<Point> twoWayMergeSort(List<Point> points1, List<Point> points2) {
		int i = 0, j = 0;
		List<Point> mergedPoints = new ArrayList<Point>();
		
		while ( i < points1.size() && j < points2.size() ) {
			Point p1 = points1.get(i), p2 = points2.get(j);
			if ( p1.compareTo(p2) < 0 ) {
				mergedPoints.add(p1);
				++ i;
			} else {
				mergedPoints.add(p2);
				++ j;
			}
		}
		while ( i < points1.size() ) {
			mergedPoints.add(points1.get(i ++));
		}
		while ( j < points2.size() ) {
			mergedPoints.add(points2.get(j ++));
		}
		return mergedPoints;
	}
	
	private List<Point> getConvexHullUsingGrahamScan(Point[] points) {
		if ( points.length <= 2 ) {
			return getPointsList(points);
		}
		Point pole = getMinYPoint(getPointsList(points));
		for ( Point p : points ) {
			p.getPolarCoordinates(pole);
		}
		Arrays.sort(points);
		
		int startIndex = 1;
		int totalPoints = points.length;
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
				if ( currentPoint.polarRadius > topPoint.polarRadius ) {
					candidatePoints.pop();
					candidatePoints.push(currentPoint);
				}
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
	
	private List<Point> getConvexHullPoints(Stack<Point> candidatePoints) {
		List<Point> convexHullPoints = new ArrayList<Point>();
		for ( Point p : candidatePoints ) {
			convexHullPoints.add(p);
		}
		return convexHullPoints; 
	}
	
	public static void main(String[] args) {
		if ( args.length != 1 ) {
			System.out.println("java ConvexHullDivideConquer n");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		Point[] points = new Point[n];
		
		ConvexHullDivideConquer chdc = new ConvexHullDivideConquer();
		for ( int i = 0; i < n; ++ i ) {
			double x = Math.random() * 100;
			double y = Math.random() * 100;
			points[i] = chdc.new Point(x, y);
		}
		
		System.out.println("Candidate Points:");
		for ( Point p : points ) {
			System.out.println(p);
		}
		
		long startTime = System.currentTimeMillis();
		List<Point> convexHullPoints = chdc.getConvexHull(points);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Convex Hull Points:");
		for ( Point p : convexHullPoints ) {
			System.out.println(p);
		}
		System.out.println("Total Time: " + (endTime - startTime) + " ms");
	}
}
