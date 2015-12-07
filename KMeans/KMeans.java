import java.util.ArrayList;
import java.util.List;

public class KMeans {
	class Point {
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
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
			return String.format("Point: (%.2f, %.2f)", 
					new Object[] {x, y});
		}
		
		double x;
		double y;
	}
	
	class Cluster {
		public Cluster(Point centroid) {
			this.centroid = centroid;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object o) {
			if ( o instanceof Cluster ) {
				Cluster c = (Cluster)o;
				if ( c.centroid.equals(centroid) ) {
					return true;
				}
			}
			return false;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Cluster (Centroid: %s):\n", centroid));
			
			for ( Point p : points ) {
				sb.append(String.format("\t%s\n", p));
			}
			return sb.toString();
		}
		
		/**
		 * The center of the cluster.
		 */
		Point centroid;
		
		/**
		 * The points in this cluster. 
		 */
		List<Point> points = new ArrayList<Point>();
	}
	
	public Cluster[] getClusters(int k, Point[] points) {
		if ( k <= 0 || k >= points.length ) {
			return null;
		}
		
		Cluster[] clusters = getInitialClusters(k, points);
		Cluster[] newClusters = null;
		do {
			newClusters = getClusters(k, points, clusters);
			
			if ( isClustersTheSame(clusters, newClusters) ) {
				break;
			}
			clusters = newClusters;
		} while (true);
		return clusters;
	}
	
	private Cluster[] getInitialClusters(int k, Point[] points) {
		Cluster[] clusters = new Cluster[k];
		
		for ( int i = 0; i < k; ++ i ) {
			clusters[i] = new Cluster(points[i]);
		}
		return clusters;
	}
	
	private Cluster[] getClusters(int k, Point[] points, Cluster[] cluster) {
		for ( int i = 0; i < points.length; ++ i ) {
			Point currentPoint = points[i];
			Cluster c = getClosestClusters(currentPoint, cluster);
			c.points.add(currentPoint);
		}
		
		Cluster[] newClusters = new Cluster[k];
		for ( int i = 0; i < k; ++ i ) {
			Cluster c = cluster[i];
			int numberOfPointsInCluster = c.points.size();
			
			if ( numberOfPointsInCluster == 0 ) {
				// If the cluster is empty
				int randomIndex = (int)(Math.random() * points.length);
				newClusters[i] = new Cluster(points[randomIndex]);
			} else {
				// If the cluster is not empty
				double newCentroidX = 0;
				double newCentroidY = 0;
				for ( int j = 0; j < numberOfPointsInCluster; ++ j ) {
					Point p = c.points.get(j);
					newCentroidX += p.x;
					newCentroidY += p.y;
				}
				newCentroidX /= numberOfPointsInCluster;
				newCentroidY /= numberOfPointsInCluster;
				
				Cluster newCluster = new Cluster(new Point(newCentroidX, newCentroidY));
				newClusters[i] = newCluster;
			}
		}
		return newClusters;
	}
	
	private Cluster getClosestClusters(Point p, Cluster[] clusters) {
		int minIndex = 0;
		double minDistance = Integer.MAX_VALUE;
		
		for ( int i = 0; i < clusters.length; ++ i ) {
			Point centroid = clusters[i].centroid;
			double distance = Math.sqrt((centroid.x - p.x) * (centroid.x - p.x) + (centroid.y - p.y) * (centroid.y - p.y));
			
			if ( distance < minDistance ) {
				minDistance = distance;
				minIndex = i;
			}
		}
		return clusters[minIndex];
	}
	
	private boolean isClustersTheSame(Cluster[] cluster1, Cluster[] cluster2) {
		int k = cluster1.length;
		for ( int i = 0; i < k; ++ i ) {
			if ( !cluster1[i].equals(cluster2[i]) ) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		if ( args.length != 2 ) {
			System.out.println("Usage: java KMeans number_of_points number_of_clusters");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		
		KMeans km = new KMeans();
		Point[] points = new Point[n];
		
		System.out.println("Original Points:");
		for ( int i = 0; i < n; ++ i ) {
			Point p = km.new Point(Math.random() * 100, Math.random() * 100);
			points[i] = p;
			
			System.out.println(p);
		}
		System.out.println();
		
		System.out.println("Clusters:");
		Cluster[] clusters = km.getClusters(k, points);
		for ( Cluster c : clusters ) {
			System.out.print(c);
		}
		
	}
}