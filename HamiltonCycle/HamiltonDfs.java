import java.util.ArrayList;
import java.util.List;

public class HamiltonDfs {
	public boolean getHamiltonPathway(int startPoint, int currentPoint, 
			List<Integer> pathway, boolean[][] isAccessable) {
		boolean hasPathway = false;
		int totalPoints = isAccessable.length;
		
		if ( startPoint == currentPoint && 
				isAllVisited(totalPoints, pathway) ) {
			return true;
		}
		for ( int i = 0; i < totalPoints; ++ i ) {
			if ( isAccessable[currentPoint][i] && 
					!isVisited(startPoint, i, totalPoints, pathway) ) {
				pathway.add(i);
				hasPathway = getHamiltonPathway(
						startPoint, i, pathway, isAccessable);
				
				if ( hasPathway ) {
					break;
				}
				pathway.remove(pathway.size() - 1);
			}
		}
		return hasPathway;
	}
	
	private boolean isVisited(int startPoint, int currentPoint,
			int totalPoints, List<Integer> pathway) {
		int startPointCounter = 0;
		for ( Integer e : pathway ) {
			if ( e.intValue() == startPoint && 
					pathway.size() == totalPoints &&
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
			System.out.println("Usage: java HamiltonDfs number_of_vertexes start_vertex posibility_vertex_has_edge");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		int startPoint = Integer.parseInt(args[1]);
		double edgeRatio = Double.parseDouble(args[2]);
		boolean[][] isAccessable = new boolean[n][n];
		List<Integer> pathway = new ArrayList<Integer>();
		pathway.add(startPoint);
		
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
		
		HamiltonDfs hd = new HamiltonDfs();
		long startTime = System.currentTimeMillis();
		boolean hasPathway = hd.getHamiltonPathway(startPoint, startPoint, pathway, isAccessable);
		long endTime = System.currentTimeMillis();
		
		if ( hasPathway ) {
			for ( Integer e : pathway ) {
				System.out.print(e + 1 + " ");
			}
			System.out.println();
		} else {
			System.out.println("No Solution!");
		}
		System.out.println("Total Time: " + (endTime - startTime) + " ms");
	}
}
