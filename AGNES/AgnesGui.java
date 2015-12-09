import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AgnesGui extends Application {
	public static void main(String[] args) {
		if ( args.length != 1 ) {
			System.out.println("Usage: java AgnesGui number_of_clusters");
			return;
		}
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final Parameters params = getParameters();
		final List<String> args = params.getRaw();
		int k = Integer.parseInt(args.get(0));
		Agnes.Cluster cluster = getCluster(k);

		primaryStage.setTitle("Agglomerative Hierarchical Clustering");
		Group root = new Group();
		final Canvas canvas = new Canvas(640, 480);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		drawClusters(gc, cluster);
	}

	private Agnes.Cluster getCluster(int k) {
		Agnes agnes = new Agnes();

		List<Agnes.Cluster> points = new ArrayList<Agnes.Cluster>();
		System.out.println("Original Points: ");
		for (int i = 0; i < k; ++i) {
			Agnes.Point center = agnes.new Point(Math.random() * 100, Math.random() * 100);
			points.addAll(agnes.getPoints(center, Math.random() * 24));
		}
		System.out.println("\t" + points);
		System.out.println();

		System.out.println("Clusters:");
		Agnes.Cluster cluster = agnes.getCluster(points);
		System.out.print(cluster);

		return cluster;
	}

	private void drawClusters(final GraphicsContext gc, final Agnes.Cluster cluster) {
		if (cluster == null) {
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				Queue<Agnes.Cluster> undrawedPoints = new LinkedList<Agnes.Cluster>();
				undrawedPoints.offer(cluster);
				
				do {
					Agnes.Cluster currentCluster = undrawedPoints.poll();
					List<Agnes.Point> points = currentCluster.getPoints();
					int r = (int) (Math.random() * 256), 
						g = (int) (Math.random() * 256), 
						b = (int) (Math.random() * 256);
					drawPoints(gc, Color.rgb(r, g, b), points);
					System.out.println("\t[DEBUG] Size of Current Cluster: " + points.size());
					
					Agnes.Cluster[] subClusters = currentCluster.getClusters();
					if (subClusters.length == 2) {
						for (int i = 0; i < subClusters.length; ++i) {
							undrawedPoints.offer(subClusters[i]);
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// Do Nothing
					}
				} while ( !undrawedPoints.isEmpty() );			
			}
		}).start();
	}

	private void drawPoints(final GraphicsContext gc, Color color, List<Agnes.Point> points) {
		for (int i = 0; i < points.size(); ++i) {
			drawPoint(gc, color, points.get(i));
		}
	}

	private void drawPoint(final GraphicsContext gc, Color color, Agnes.Point point) {
		drawPoint(gc, color, point, 4);
	}

	private void drawPoint(final GraphicsContext gc, Color color, Agnes.Point point, int radius) {
		double x = point.x * 4.8 + 80;
		double y = point.y * 4.8;
		gc.setFill(color);
		gc.fillOval(x, y, radius, radius);
	}
}
