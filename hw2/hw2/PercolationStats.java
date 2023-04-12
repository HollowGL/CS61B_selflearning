package hw2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Date;

public class PercolationStats {
    private double[] xt;
    private final int t;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        xt = new double[T];
        t = T;
        long seed = new Date().getTime();
        StdRandom.setSeed(seed);
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            xt[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        // double sum = 0;
        // for (int i = 0; i < t; i++) {
        //     sum += xt[i];
        // }
        // return sum / t;
        return edu.princeton.cs.algs4.StdStats.mean(xt);
    }

    public double stddev() {
        // double sum = 0;
        // double u = mean();
        // for (int i = 0; i < t; i++) {
        //     sum += Math.pow(xt[i] - u, 2);
        // }
        // return sum / (t - 1);
        return edu.princeton.cs.algs4.StdStats.stddev(xt);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.pow(t, 0.5);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.pow(t, 0.5);
    }
}
