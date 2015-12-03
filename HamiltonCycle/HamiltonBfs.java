import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HamiltonBfs {
	private class Point {
		public Point(int point) {
			this.point = point;
			this.pathway = new ArrayList<Integer>();
		}
		
		public Point(int point, List<Integer> pathway) {
			this.point = point;
			this.pathway = pathway;
		}
		
		public int point;
		public List<Integer> pathway;
	}
	
	public Point getHamiltonPathway(int startPoint, boolean[][] isAccessable) {
		int totalPoints = isAccessable.length;
		Queue<Point> q = new LinkedList<Point>();
		q.offer(new Point(startPoint));
		
		while ( !q.isEmpty() ) {
			Point currentPoint = q.poll();
			
			if ( startPoint == currentPoint.point && 
					isAllVisited(totalPoints, currentPoint.pathway) ) {
				return currentPoint;
			}
			for ( int i = 0; i < totalPoints; ++ i ) {
				if ( isAccessable[currentPoint.point][i] &&
						!isVisited(startPoint, i, totalPoints, 
								currentPoint.pathway) ) {
					List<Integer> pathway = 
						new ArrayList<Integer>(currentPoint.pathway);
					pathway.add(currentPoint.point);
					
					q.offer(new Point(i, pathway));
				}
			}
		}
		return null;
	}
	
	private boolean isVisited(int startPoint, int currentPoint, 
			int totalPoints, List<Integer> pathway) {
		int startPointCounter = 0;
		for ( Integer e : pathway ) {
			if ( e.intValue() == startPoint && 
					pathway.size() == totalPoints - 1 &&
					startPointCounter == 0 ) {
				++ startPointCounter;
				continue;
			}
			if ( e.intValue() == currentPoint ) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAllVisited(
			int totalPoints, List<Integer> pathway) {
		for ( int i = 0; i < totalPoints; ++ i ) {
			if ( !pathway.contains(i) ) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		if ( args.length != 3 ) {
			System.out.println("Usage: java HamiltonBfs number_of_vertexes start_vertex posibility_vertex_has_edge");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		int startPoint = Integer.parseInt(args[1]);
		double edgeRatio = Double.parseDouble(args[2]);
		boolean[][] isAccessable = new boolean[n][n];
		
		for ( int i = 0; i < n; ++ i ) {
			for ( int j = 0; j < i; ++ j ) {
				isAccessable[i][j] = (int)(Math.random() * 100) % n <= Math.ceil(n * edgeRatio) ;
				isAccessable[j][i] = isAccessable[i][j];
			}
		}
		for ( int i = 0; i < n; ++ i ) {
			for ( int j = 0; j < n; ++ j ) {
				System.out.print((isAccessable[i][j] ? 1 : 0) + " ");
			}
			System.out.println();
		}
		
		HamiltonBfs hb = new HamiltonBfs();
		long startTime = System.currentTimeMillis();
		Point p = hb.getHamiltonPathway(startPoint, isAccessable);
		long endTime = System.currentTimeMillis();
		
		if ( p != null ) {
			for ( Integer e : p.pathway ) {
				System.out.print(e + 1 + " ");
			}
			System.out.println();
		} else {
			System.out.println("No Solution!");
		}
		System.out.println("Total Time: " + (endTime - startTime) + " ms");
	}
}
