import java.util.*;

public class GTS2 {
    static final int INF = Integer.MAX_VALUE;
    
    // Ma tran khoang cach
    static int[][] distanceMatrix = {
        {INF, 20, 42, 30, 6, 25},
        {12, INF, 16, 7, 33, 19},
        {23, 5, INF, 28, 14, 9},
        {12, 9, 24, INF, 31, 15},
        {14, 7, 21, 15, INF, 45},
        {36, 15, 16, 5, 205, INF}
    };
    
    static int numCities = distanceMatrix.length;
    static List<Integer> startCities = Arrays.asList(1, 2, 4, 6);
    
    public static void main(String[] args) {
        List<Integer> optimalPath = new ArrayList<>();
        int optimalCost = INF;
        
        for (int start : startCities) {
            List<Integer> path = new ArrayList<>();
            Set<Integer> remainingCities = new HashSet<>();
            for (int i = 1; i <= numCities; i++) {
                remainingCities.add(i);
            }
            
            path.add(start);
            remainingCities.remove(start);
            int currentCost = 0;
            
            System.out.println("Xet thanh pho bat dau: " + start);
            
            while (!remainingCities.isEmpty()) {
                int lastCity = path.get(path.size() - 1);
                int nextCity = remainingCities.stream()
                        .min(Comparator.comparingInt(city -> distanceMatrix[lastCity - 1][city - 1]))
                        .orElse(-1);
                
                if (nextCity != -1) {
                    path.add(nextCity);
                    currentCost += distanceMatrix[lastCity - 1][nextCity - 1];
                    remainingCities.remove(nextCity);
                }
            }
            
            String finalRouteStr = String.join(" -> ", path.stream().map(String::valueOf).toArray(String[]::new));
            System.out.println("Hanh trinh hoan chinh: " + finalRouteStr + " - Chi phi: " + currentCost);
            
            if (currentCost < optimalCost) {
                optimalCost = currentCost;
                optimalPath = new ArrayList<>(path);
            }
        }
        
        String optimalRouteStr = String.join(" -> ", optimalPath.stream().map(String::valueOf).toArray(String[]::new));
        System.out.println("\nHanh trinh toi uu: " + optimalRouteStr);
        System.out.println("Tong chi phi: " + optimalCost);
    }
}
