import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
public class DbscanGui extends Application {
	public static void main(String[] args) {
		if ( args.length != 3 ) {
			System.out.println("Usage: java DbscanGui number_of_clusters min_points eps");
			return;
		}
		Application.launch(args);
	}
 
	@Override
	public void start(Stage primaryStage) {
		final Parameters params = getParameters();
		final List<String> args = params.getRaw();
		int k = Integer.parseInt(args.get(0));
		int minPoints = Integer.parseInt(args.get(1));
		double eps = Double.parseDouble(args.get(2));
		
		List<Dbscan.Point> points = getPoints(k);
		getClusters(points, minPoints, eps);
		
		primaryStage.setTitle("Dbscan Clustering");
		Group root = new Group();
		Canvas canvas = new Canvas(640, 480);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		drawPoints(gc, points);
		
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	private List<Dbscan.Point> getPoints(int k) {
		Dbscan dbscan = new Dbscan();
		List<Dbscan.Point> points = new ArrayList<Dbscan.Point>();
		
		System.out.println("Original Points: ");
		for ( int i = 0; i < k; ++ i ) {
			Dbscan.Point center = dbscan.new Point(Math.random() * 100, Math.random() * 100);
			points.addAll(dbscan.getPoints(center, Math.random() * 24));
		}
		System.out.println("\t" + points);
		System.out.println();
		
		return points;
	}
	
	private List<Dbscan.Cluster> getClusters(List<Dbscan.Point> points, int minPoints, double eps) {
		Dbscan dbscan = new Dbscan();
		
		System.out.println("Clusters:");
		List<Dbscan.Cluster> clusters = dbscan.getClusters(points, minPoints, eps);
		for ( Dbscan.Cluster c : clusters ) {
			System.out.print(c);
		}
		return clusters;
	}

	private void drawPoints(GraphicsContext gc, List<Dbscan.Point> points) {
		for ( int i = 0; i < points.size(); ++ i ) {
			if ( points.get(i).pointType == Dbscan.PointType.CorePoint ) {
				drawPoint(gc, Color.GREEN, points.get(i));
			} else if ( points.get(i).pointType == Dbscan.PointType.BorderPoint ) {
				drawPoint(gc, Color.BLUE, points.get(i));
			} else {
				drawPoint(gc, Color.RED, points.get(i));
			}
		}
	}
	
	private void drawPoint(GraphicsContext gc, Color color, Dbscan.Point point) {
		drawPoint(gc, color, point, 4);
	}
	
	private void drawPoint(GraphicsContext gc, Color color, Dbscan.Point point, int radius) {
		double x = point.x * 4.8 + 80;
		double y = point.y * 4.8;
		gc.setFill(color);
		gc.fillOval(x, y, radius, radius);
	}
}