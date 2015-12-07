import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
public class KMeansGui extends Application {
	public static void main(String[] args) {
		if ( args.length != 2 ) {
			System.out.println("Usage: java KMeansGui number_of_points number_of_clusters");
			return;
		}
		Application.launch(args);
	}
	
	private KMeans.Cluster[] getCluster(int n, int k) {
		KMeans km = new KMeans();
		KMeans.Point[] points = new KMeans.Point[n];
		
		System.out.println("Original Points: ");
		for ( int i = 0; i < n; ++ i ) {
			KMeans.Point p = km.new Point(Math.random() * 100, Math.random() * 100);
			points[i] = p;
			
			System.out.println(p);
		}
		System.out.println();
		
		System.out.println("Clusters:");
		KMeans.Cluster[] clusters = km.getClusters(k, points);
		for ( KMeans.Cluster c : clusters ) {
			System.out.print(c);
		}
		return clusters;
	}
 
	@Override
	public void start(Stage primaryStage) {
		final Parameters params = getParameters();
		final List<String> args = params.getRaw();
		int n = Integer.parseInt(args.get(0));
		int k = Integer.parseInt(args.get(1));
		KMeans.Cluster[] clusters = getCluster(n, k);
		
		primaryStage.setTitle("KMeans Clustering");
		Group root = new Group();
		Canvas canvas = new Canvas(640, 480);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawClusters(gc, clusters);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	private void drawClusters(GraphicsContext gc, KMeans.Cluster[] clusters) {
		for ( int i = 0; i < clusters.length; ++ i ) {
			int r = (int)(Math.random() * 256),
				g = (int)(Math.random() * 256),
				b = (int)(Math.random() * 256);
			
			drawCentroid(gc, Color.rgb(r, g, b), clusters[i].centroid);
			drawPoints(gc, Color.rgb(r, g, b), clusters[i].points);
		}
	}

	private void drawPoints(GraphicsContext gc, Color color, List<KMeans.Point> points) {
		for ( int i = 0; i < points.size(); ++ i ) {
			drawPoint(gc, color, points.get(i));
		}
	}
	
	private void drawPoint(GraphicsContext gc, Color color, KMeans.Point point) {
		drawPoint(gc, color, point, 4);
	}
	
	private void drawCentroid(GraphicsContext gc, Color color, KMeans.Point point) {
		drawPoint(gc, color, point, 8);
	}
	
	private void drawPoint(GraphicsContext gc, Color color, KMeans.Point point, int radius) {
		double x = point.x * 4.8 + 80;
		double y = point.y * 4.8;
		gc.setFill(color);
		gc.fillOval(x, y, radius, radius);
	}
}