package com.thyme.smalam119.routeplannerapplication.Utils.TSPEngine;

/**
 * Created by smalam119 on 12/10/17.
 */

import java.util.ArrayList;

public class TSPEngine {

    private ArrayList<Integer> outputArray = new ArrayList<Integer>();
    private int g[][], p[][], d[][], nPow, N;
    public static long time;

    public TSPEngine() {

    }

    public ArrayList<Integer> computeTSP(int[][] inputArray, int n) {

        long start = System.nanoTime();

        N = n;
        nPow = (int) Math.pow(2, n);
        g = new int[n][nPow];
        p = new int[n][nPow];
        d = inputArray;

        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < nPow; j++) {
                g[i][j] = -1;
                p[i][j] = -1;
            }
        }

        for (i = 0; i < n; i++) {
            g[i][0] = inputArray[i][0];
        }

        int result = tsp(0, nPow - 2);
        outputArray.add(0);
        getPath(0, nPow - 2);
        outputArray.add(result);
        long end = System.nanoTime();
        time = (end - start) / 1000;
        return outputArray;
    }

    private int tsp(int start, int set) {
        int masked, mask, result = -1, temp;
        if (g[start][set] != -1) {
            return g[start][set];
        } else {
            for (int x = 0; x < N; x++) {
                mask = nPow - 1 - (int) Math.pow(2, x);
                masked = set & mask;
                if (masked != set) {
                    temp = d[start][x] + tsp(x, masked);
                    if (result == -1 || result > temp) {
                        result = temp;
                        p[start][set] = x;
                    }
                }
            }
        }
        g[start][set] = result;
        return result;
    }

    private void getPath(int start, int set) {
        if (p[start][set] == -1) {
            return;
        }
        int x = p[start][set];
        int mask = nPow - 1 - (int) Math.pow(2, x);
        int masked = set & mask;
        outputArray.add(x);
        getPath(x, masked);
    }

}
