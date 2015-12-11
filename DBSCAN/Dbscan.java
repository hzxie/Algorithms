import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Dbscan {
	class Point implements Comparable<Point> {
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return (int)(x * 10000) % 37 + (int)(y * 10000) % 83;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object o) {
			if ( o instanceof Point ) {
				Point p = (Point)o;
				if ( Math.abs(p.x - this.x) <= 10e-4 &&
						Math.abs(p.y - this.y) <= 10e-4 ) {
					return true;
				}
			}
			return false;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return String.format("(%.2f, %.2f)", 
					new Object[] {x, y});
		}
		@Override
		public int compareTo(Point o) {
			if ( x != o.x ) {
				return (int)((x - o.x) * 10000);
			} else {
				return (int)((y - o.y) * 10000); 
			}
		}
		
		public boolean isPointsInEpsCircle(Point p, double eps) {
			return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) <= eps * eps;
		}
		
		public double x;
		public double y;
		
		public PointType pointType = PointType.NoisePoint;
		Point representPoint = null;
	}
	
	public static enum PointType {
		CorePoint, BorderPoint, NoisePoint
	};
	
	class Cluster {
		public Cluster(Point representPoint) {
			points = new ArrayList<Point>();
			points.add(representPoint);
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Cluster:\n");
			
			for ( Point p : points ) {
				sb.append(String.format("\t%s\t[%s]\n", p, p.pointType));
			}
			return sb.toString();
		}
		
		public List<Point> points;
	}

	public List<Cluster> getClusters(List<Point> points, int minPoints, double eps) {
		List<Point> corePoints = getCorePoints(points, minPoints, eps);
		Map<Point, Cluster> clusters = getClustersOfCorePoints(corePoints, eps);
		
		List<Point> borderPoints = getBorderPoints(points, corePoints, minPoints, eps);
		getClustersOfBorderPoints(corePoints, borderPoints, clusters, eps);
		clusters = mergeClusters(clusters, eps);
		
		return new ArrayList<Cluster>(clusters.values());
	}
	
	private List<Point> getCorePoints(List<Point> points, int minPoints, double eps) {
		List<Point> corePoints = new ArrayList<Point>();
		
		for ( int i = 0; i < points.size(); ++ i ) {
			Point currentPoint = points.get(i);
			int numberOfPointsInEps = 0;
			for ( int j = 0; j < points.size(); ++ j ) {
				Point anotherPoint = points.get(j);
				if ( currentPoint.isPointsInEpsCircle(anotherPoint, eps) ) {
					++ numberOfPointsInEps;
				}
			}
			if ( numberOfPointsInEps >= minPoints ) {
				currentPoint.pointType = PointType.CorePoint;
				corePoints.add(currentPoint);
			}
		}
		return corePoints;
	}
	
	private List<Point> getBorderPoints(List<Point> points, List<Point> corePoints, int minPoints, double eps) {
		List<Point> borderPoints = new ArrayList<Point>();
		
		for ( int i = 0; i < points.size(); ++ i ) {
			Point currentPoint = points.get(i);
			
			if ( currentPoint.pointType == PointType.CorePoint ) {
				continue;
			}
			for ( int j = 0; j < corePoints.size(); ++ j ) {
				Point anotherCorePoint = corePoints.get(j);
				if ( currentPoint.isPointsInEpsCircle(anotherCorePoint, eps) ) {
					currentPoint.pointType = PointType.BorderPoint;
					borderPoints.add(currentPoint);
					break;
				}
			}
		}
		return borderPoints;
	}
	
	private Map<Point, Cluster> getClustersOfCorePoints(List<Point> corePoints, double eps) {
		Map<Point, Cluster> clusters = new HashMap<Point, Cluster>();
		
		for ( int i = 0; i < corePoints.size(); ++ i ) {
			Point currentPoint = corePoints.get(i);
			Point representPoint = null;
			for ( int j = 0; j < i; ++ j ) {
				Point anotherPoint = corePoints.get(j);
				if ( currentPoint.isPointsInEpsCircle(anotherPoint, eps) ) {
					representPoint = anotherPoint.representPoint;
					currentPoint.representPoint = representPoint;
					break;
				}
			}
			if ( representPoint == null ) {
				currentPoint.representPoint = currentPoint;
				clusters.put(currentPoint, new Cluster(currentPoint));
			} else {
				Cluster cluster = clusters.get(representPoint);
				cluster.points.add(currentPoint);
			}
		}
		return clusters;
	}
	
	private void getClustersOfBorderPoints(List<Point> corePoints, List<Point> borderPoints, 
			Map<Point, Cluster> clusters, double eps) {
		for ( int i = 0; i < borderPoints.size(); ++ i ) {
			Point currentPoint = borderPoints.get(i);
			Point representPoint = null;
			for ( int j = 0; j < corePoints.size(); ++ j ) {
				Point anotherCorePoint = corePoints.get(j);
				if ( currentPoint.isPointsInEpsCircle(anotherCorePoint, eps) ) {
					representPoint = anotherCorePoint.representPoint;
					break;
				}
			}
			if ( representPoint != null ) {
				Cluster cluster = clusters.get(representPoint);
				cluster.points.add(currentPoint);
			}
		}
	}
	
	public List<Point> getPoints(Point center, double radius) {
		int generatedPoints = 0;
		int totalPoints = (int)(Math.random() * 64 + radius * radius / 4);
		List<Point> points = new ArrayList<Point>();
		
		double topRightX = center.x + radius;
		double topRightY = center.y + radius;
		do {
			Point p = new Point(topRightX - Math.random() * radius * 2, topRightY - Math.random() * radius * 2);
			if ( isPointInCircle(p, center, radius) ) {
				points.add(p);
				++ generatedPoints;
			}
		} while ( generatedPoints < totalPoints );
		return points;
	}
	
	private boolean isPointInCircle(Point p, Point center, double radius) {
		return (p.x - center.x) * (p.x - center.x) + (p.y - center.y) * (p.y - center.y) <= radius * radius;
	}
	
	private Map<Point, Cluster> mergeClusters(Map<Point, Cluster> clusters, double eps) {
		Map<Point, Cluster> copiedClusters = new HashMap<Point, Cluster>(clusters);
		Set<Entry<Point, Cluster>> clusterEntrySet = clusters.entrySet();
		
		int i = 0;
		for ( Entry<Point, Cluster> e1 : clusterEntrySet ) {
			int j = 0;
			for ( Entry<Point, Cluster> e2 : clusterEntrySet ) {
				if ( j < i ) {
					if ( isTwoClusterInEps(e1.getValue(), e2.getValue(), eps) ) {
						List<Point> points1 = e1.getValue().points;
						List<Point> points2 = e2.getValue().points;
						
						points1.addAll(points2);
						copiedClusters.remove(e2.getKey());
					}
				}
				++ j;
			}
			++ i;
		}
		return copiedClusters;
	}
	
	private boolean isTwoClusterInEps(Cluster c1, Cluster c2, double eps) {
		List<Point> points1 = c1.points;
		List<Point> points2 = c2.points;
		
		for ( int i = 0; i < points1.size(); ++ i ) {
			for ( int j = 0; j < points2.size(); ++ j ) {
				if ( isPointInCircle(points1.get(i), points2.get(j), eps) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		if ( args.length != 3 ) {
			System.out.println("Usage: java DbscanGui number_of_clusters min_points eps");
			return;
		}
		int n = Integer.parseInt(args[0]);
		int minPoints = Integer.parseInt(args[1]);
		double eps = Integer.parseInt(args[2]);
		Dbscan dbscan = new Dbscan();
		
		List<Point> points = new ArrayList<Point>();
		System.out.println("Original Points: ");
		for ( int i = 0; i < n; ++ i ) {
			Point p = dbscan.new Point(Math.random() * 10, Math.random() * 10);
			points.add(p);
			
			System.out.println("\t" + p);
		}
		System.out.println();
		
		Collections.sort(points);
		
		List<Cluster> clusters = dbscan.getClusters(points, minPoints, eps);
		for ( Cluster c : clusters ) {
			System.out.println(c);
		}
	}
}
