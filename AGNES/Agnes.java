import java.util.ArrayList;
import java.util.List;

public class Agnes {
	class Cluster {
		public Cluster() { }
		
		public Cluster(Cluster c) {
			this.cluster1 = c;
		}
		
		public Cluster(Cluster c1, Cluster c2) {
			this.cluster1 = c1;
			this.cluster2 = c2;
		}
		
		public Cluster[] getClusters() {
			Cluster[] clusters = null;
			
			if ( cluster1 != null && cluster2 != null ) {
				clusters = new Cluster[2];
				clusters[0] = cluster1;
				clusters[1] = cluster2;
			} else if ( cluster1 != null ) {
				clusters = new Cluster[1];
				clusters[0] = cluster1;
			} else if ( cluster2 != null ) {
				clusters = new Cluster[1];
				clusters[0] = cluster2;
			}
			return clusters;
		}
		
		public void setCluster(Cluster c) {
			if ( cluster1 == null ) {
				cluster1 = c;
			} else {
				cluster2 = c;
			}
		}
		
		public List<Point> getPoints() {
			List<Point> points = new ArrayList<Point>();
			
			if ( cluster1 instanceof Point ) {
				points.add((Point)cluster1);
			} else if ( cluster1 instanceof Cluster ) {
				points.addAll(cluster1.getPoints());
			}
			if ( cluster2 instanceof Point ) {
				points.add((Point)cluster2);
			} else if ( cluster2 instanceof Cluster ) {
				points.addAll(cluster2.getPoints());
			}
			return points;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object o) {
			if ( o instanceof Cluster ) {
				Cluster c = (Cluster)o;
				
				Cluster[] thisClusters = getClusters();
				Cluster[] otherClusters = c.getClusters();
				
				if ( thisClusters.length != otherClusters.length ) {
					return false;
				}
				for ( int i = 0; i < thisClusters.length; ++ i ) {
					if ( !thisClusters[i].equals(otherClusters[i]) ) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("<");
			if ( cluster1 != null ) {
				if ( cluster1 instanceof Point ) {
					sb.append(((Point)cluster1).toString());
				} else {
					sb.append(cluster1.toString());
				}
			}
			if ( cluster2 != null ) {
				sb.append(", ");
				if ( cluster2 instanceof Point ) {
					sb.append(((Point)cluster2).toString());
				} else {
					sb.append(cluster2.toString());
				}
			}
			sb.append(">");
			return sb.toString();
		}
		
		/**
		 * The sub-clusters in this cluster;
		 */
		private Cluster cluster1 = null;
		private Cluster cluster2 = null;
	}
	
	class Point extends Cluster {
		public Point(double x, double y) {
			super.cluster1 = this;
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
			return String.format("(%.2f, %.2f)", 
					new Object[] {x, y});
		}
		
		public double x;
		public double y;
	}
	
	public Cluster getCluster(List<Cluster> clusters) {
		while ( clusters.size() > 1 ) {
			double minProximity = Double.MAX_VALUE;
			int minProximityIndex1 = 0, minProximityIndex2 = 0;
			
			for ( int i = 0; i < clusters.size(); ++ i ) {
				for ( int j = i + 1; j < clusters.size(); ++ j ) {
					double proximity = getProximity(clusters.get(i), clusters.get(j));
					
					if ( proximity < minProximity ) {
						minProximity = proximity;
						minProximityIndex1 = i;
						minProximityIndex2 = j;
					}
				}
			}
			Cluster c = new Cluster(clusters.get(minProximityIndex1), clusters.get(minProximityIndex2));
			clusters.add(c);
			/*
			 * NOTE: j > i, minProximityIndex2 > minProximity1.
			 * You should remove  the element with index minProximityIndex2 first.
			 */
			clusters.remove(minProximityIndex2);
			clusters.remove(minProximityIndex1);
		}
		return clusters.size() == 0 ? null : clusters.get(0);
	}
	
	private double getProximity(Cluster cluster1, Cluster cluster2) {
		List<Point> points1 = cluster1.getPoints();
		List<Point> points2 = cluster2.getPoints();
		double totalDistance = 0;
		
		for ( int i = 0; i < points1.size(); ++ i ) {
			for ( int j = 0; j < points2.size(); ++ j ) {
				totalDistance += getDistance(points1.get(i), points2.get(j));
			}
		}
		return totalDistance / (points1.size() * points2.size());
	}
	
	private double getDistance(Point p1, Point p2) {
		return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
	}
	
	public List<Cluster> getPoints(Point center, double radius) {
		int generatedPoints = 0;
		int totalPoints = (int)(Math.random() * 64 + radius * radius / 4);
		List<Cluster> points = new ArrayList<Cluster>();
		
		double topRightX = center.x + radius;
		double topRightY = center.y + radius;
		System.out.println("\t[DEBUG] Total Points: " + totalPoints);
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
	
	public static void main(String[] args) {
		if ( args.length != 1 ) {
			System.out.println("Usage: java Agnes number_of_points");
			return;
		}
		int n = Integer.parseInt(args[0]);
		Agnes agnes = new Agnes();
		
		List<Cluster> points = new ArrayList<Cluster>();
		System.out.println("Original Points: ");
		for ( int i = 0; i < n; ++ i ) {
			Cluster p = agnes.new Point(Math.random() * 100, Math.random() * 100);
			points.add(p);
			
			System.out.println(p);
		}
		System.out.println();
		
		System.out.println("Clusters: ");
		Cluster c = agnes.getCluster(points);
		System.out.println(c);
	}
}
