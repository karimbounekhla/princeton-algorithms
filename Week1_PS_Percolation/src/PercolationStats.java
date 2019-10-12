import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int n;
    private int trials;
    private double[] results;
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        this.results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                openRandomSite(p);
            }
            results[i] = (double) p.getNumOpenSites() / (n*n);
        }
    }

    private void openRandomSite(Percolation p) {
        while (true) {
            int row = StdRandom.uniform(1, n+1);
            int col = StdRandom.uniform(1, n+1);
            p.open(row, col);
            if (p.isOpen(row, col)) { break; }
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - (1.96*stddev())/Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + (1.96*stddev())/Math.sqrt(trials);
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PercolationStats ps = new PercolationStats(200, 100);

        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = "
                + ps.confidenceLo() + ", " + ps.confidenceHi());
    }

}
