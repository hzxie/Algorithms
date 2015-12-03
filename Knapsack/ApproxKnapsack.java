public class ApproxKnapsack {
	public boolean[] getKnapsackSolution(
		int[] weight, int[] value, int capcity, int totalValue) {
		int n = weight.length;
		int[][] cost = new int[n][totalValue + 1];
		
		for ( int i = 1; i <= totalValue; ++ i ) {
			int currentWeight = weight[n - 1],
				currentValue = value[n - 1];
			cost[n - 1][i] = i == currentValue ? 
					currentWeight : INFINITY;
		}
		for ( int i = n - 2; i >= 0; -- i ) {
			int currentWeight = weight[i],
				currentValue = value[i];
			for ( int j = 0; j <= totalValue; ++ j ) {
				cost[i][j] = j < currentValue ? cost[i + 1][j] : 
					Math.min(cost[i + 1][j], 
						cost[i + 1][j - currentValue] + currentWeight);
			}
		}
		return getPickedItem(cost, weight, value, capcity, totalValue);
	}
	
	public boolean[] getPickedItem(int[][] cost, int[] weight, 
		int[] value, int capcity, int totalValue) {
		int n = cost.length;
		int j = totalValue;
		boolean[] isPicked = new boolean[n];
		
		while ( cost[0][j] > capcity ) {
			-- j;
		}
		for ( int i = 0; i < n - 1; ++ i ) {
			if ( cost[i][j] != cost[i + 1][j] ) {
				isPicked[i] = true;
				j -= value[i];
			}
		}
		isPicked[n - 1] = cost[n - 1][j] == weight[n - 1];
		return isPicked;
	}
	
	private static final int INFINITY = Integer.MAX_VALUE / 2;
	
	public static void main(String[] args) {
		if ( args.length != 3 ) {
			System.out.println("Usage: java ApproxKnapsack n capcity epsilon");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		int capcity = Integer.parseInt(args[1]);
		double epsilon = Double.parseDouble(args[2]);
		int totalValue = 0;
		int[] weight = new int[n];
		int[] value = new int[n];
		int[] valueApprox = new int[n];
		
		for ( int i = 0; i < n; ++ i ) {
			weight[i] = (int)(Math.random() * capcity);
			value[i] = (int)(Math.random() * 100);
		}
		
		System.out.println("[DEBUG] Weight");
		for ( int i = 0; i < n; ++ i ) { 
			System.out.print(weight[i] + " ");
		}
		System.out.println();
		System.out.println("[DEBUG] Value");
		for ( int i = 0; i < n; ++ i ) { 
			System.out.print(value[i] + " ");
		}
		System.out.println();
		
		ApproxKnapsack ak = new ApproxKnapsack();
		// Calculate Accurate Solution
		totalValue = 0;
		for ( int i = 0; i < n; ++ i ) {
			totalValue += value[i];
		}
		boolean[] isPickedAccurate = ak.getKnapsackSolution(weight, value, capcity, totalValue);
		totalValue = 0;
		for ( int i = 0; i < n; ++ i ) {
			if ( isPickedAccurate[i] ) {
				totalValue += value[i];
			}
			System.out.print((isPickedAccurate[i] ? 1 : 0) + " ");
		}
		System.out.println();
		System.out.println("Max Value (Accurate) = " + totalValue);
		
		// Rounding
		int maxValue = 0;
		double k = n / epsilon;
		for ( int i = 0; i < n; ++ i ) { 
			if ( value[i] > maxValue ) {
				maxValue = value[i];
			}
		}
		totalValue = 0;
		for ( int i = 0; i < n; ++ i ) {
			valueApprox[i] = (int)Math.floor(value[i] * k / maxValue);
			totalValue += valueApprox[i];
		}
		System.out.println("[DEBUG] Rounding Value");
		for ( int i = 0; i < n; ++ i ) { 
			System.out.print(valueApprox[i] + " ");
		}
		System.out.println();
		
		// Calculate Approximation Solution
		long startTime = System.currentTimeMillis();
		boolean[] isPickedApprox = ak.getKnapsackSolution(weight, value, capcity, totalValue);
		long endTime = System.currentTimeMillis();
		
		totalValue = 0;
		for ( int i = 0; i < n; ++ i ) {
			if ( isPickedApprox[i] ) {
				totalValue += value[i];
			}
			System.out.print((isPickedApprox[i] ? 1 : 0) + " ");
		}
		System.out.println();
		System.out.println("Max Value (Approx) = " + totalValue);
		System.out.println("Total Time: " + (endTime - startTime) + " ms");
	}
}
