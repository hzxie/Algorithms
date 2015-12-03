
public class KnapsackTest {
	public static void main(String[] args) {
		if ( args.length != 2 ) {
			System.out.println("Usage: java Knapsack n capcity");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		int capcity = Integer.parseInt(args[1]);
		int totalValue = 0;
		int[] weight = new int[n];
		int[] value = new int[n];
		
		for ( int i = 0; i < n; ++ i ) {
			weight[i] = (int)(Math.random() * capcity);
			value[i] = (int)(Math.random() * capcity);
		}
		
		for ( int i = 0; i < n; ++ i ) { 
			totalValue += value[i];
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
		boolean[] isPicked1 = ak.getKnapsackSolution(weight, value, capcity, totalValue);
    	totalValue = 0;
    	for ( int i = 0; i < n; ++ i ) {
			if ( isPicked1[i] ) {
				totalValue += value[i];
			}
			System.out.print((isPicked1[i] ? 1 : 0) + " ");
		}
    	System.out.println();
    	System.out.println("Max Value = " + totalValue);
    	
    	Knapsack k = new Knapsack();
		boolean[] isPicked2 = k.getKnapsackSolution(weight, value, capcity);
    	totalValue = 0;
    	for ( int i = 0; i < n; ++ i ) {
			if ( isPicked2[i] ) {
				totalValue += value[i];
			}
			System.out.print((isPicked2[i] ? 1 : 0) + " ");
		}
    	System.out.println();
    	System.out.println("Max Value = " + totalValue);
	}
}
