
//Nisarg Joshi CS610 2922 prp NJIT

import java.io.*;
import java.text.DecimalFormat;

public class hits2922 {

    private static BufferedReader br;
    private static double[] hub_value, authority_value;
    private static int num_itrs, AT[][], adj_mat[][], len, err_rate, isLarge = 0;
    private static DecimalFormat df;
    public static final double _10M = 10000000.0;

    public static void sopln(String s) {
	System.out.println(s);
    }

    public static void sop(String s) {
	System.out.print(s);
    }

    public static void initializeAdjacentMatrixAndValues2922(int initVal) {

	double first_value = 0.0;
	if (len <= 10) {
	    if (initVal == 0) {
		first_value = 0.0;
	    } else if (initVal == 1) {
		first_value = 1.0;
	    } else if (initVal == -1) {
		double value = 1 / ((double) len);
		first_value = value;
	    } else if (initVal == -2) {
		first_value = 1 / Math.sqrt((double) len);
	    }
	} else {
	    first_value = 1 / ((double) len);
	    num_itrs = 0;
	    isLarge = 1;
	}
	sopln("Vectors are initialized to " + first_value);
	int i = 0;
	while (i < len) {
	    hub_value[i] = first_value;
	    authority_value[i] = first_value;
	    i++;
	}
    }

    public static void buildMatrix2922(int initVal) throws IOException {
	String line = "";
	while ((line = br.readLine()) != null) {
	    adj_mat[Integer.valueOf(line.split(" ")[0])][Integer.valueOf(line.split(" ")[1])] = 1;
	}
    }

    public static void updateHon2922(int iter) {
	if (ErrorRate() == 1) {
	    updateErrorrate();
	} else {
	    updateIter(iter);
	}
    }

    public static void updateErrorrate() {
	double[] pA, pH, a = authority_value, h = hub_value;
	double errVal = Math.pow(10, num_itrs);
	int i = 0;
	do {
	    pA = a;
	    pH = h;
	    a = matMul(h, AT);
	    h = matMul(a, adj_mat);
	    a = normalize(a);
	    h = normalize(h);
	    if (isLarge == 0)
		printIters("Iter", ++i, a, h);
	} while (!converge(a, h, pA, pH, errVal));
	if (isLarge == 1) {
	    printIters("Iteration", ++i, a, h);
	}
    }

    public static void updateIter(int iter) {
	double[] a, h = hub_value;
	int i = 0;
	while (i < iter) {
	    a = matMul(h, AT);
	    h = matMul(a, adj_mat);
	    a = normalize(a);
	    h = normalize(h);
	    printIters("Iter", ++i, a, h);
	}
    }
    
    public static double[] matMul(double[] v, int[][] a) {
	double[] res = new double[len];
	int i = 0, j = 0;
	while (i < len) {
	    j = 0;
	    while (j < len) {
		res[i] = res[i] + (a[i][j] * v[j++]);
	    }
	    i++;
	}
	return res;
    }
    
    public static double[] normalize(double[] v) {
	double[] res = new double[len];
	double sum = 0;
	int i = 0;
	while (i < len) {
	    sum += Math.pow(v[i++], 2.0);

	}
	i = 0;
	while (i < len) {
	    res[i] = v[i++] / Math.sqrt(sum);

	}
	return res;
    }
    
    public static void printIters(String cased, int i, double[] a, double[] b) {
	sop(cased + " : " + (i) + " ");
	if ((cased == "Base") || isLarge == 0) {
	    int j = 0;
	    while (j < len) {
		sop("A/H[" + j + "] " + df.format(Math.round(a[j] * _10M) / _10M) + "/"
		    + df.format(Math.round(b[j] * _10M) / _10M) + " ");
		j++;
	    }
	}
	if (isLarge == 1 && !(cased == "Base")) {
	    int j = 0;
	    while (j < len) {
		sopln("A/H[" + j + "] " + df.format(a[j]) + "/" + df.format(b[j]) + " ");
		j++;
	    }
	}
	sopln("");
    }

    public static int[][] transpose() {
	int[][] a = new int[len][len];
	int i = 0, j = 0;
	while (i < len) {
	    j = 0;
	    while (j < len) {
		a[i][j] = adj_mat[j][i];
		j++;
	    }
	    i++;
	}
	return a;
    }
    
    public static boolean converge(double[] a, double[] h, double[] pA, double[] pH, double err) {
	boolean check = true;
	boolean diff[] = new boolean[len];
	int i = 0;
	while (i < len) {
	    diff[i] = Math.abs(a[i] - pA[i]) < err && Math.abs(h[i] - pH[i]) < err ? true : false;
	    i++;
	}

	for (boolean b : diff) {
	    check = b && check;
	}

	return check;
    }

    public static int ErrorRate() {
	return err_rate;
    }

    public static void ErrorRate2922(int err_rate) {
	hits2922.err_rate = err_rate;
    }
    
    public static void main(String[] args) throws Exception {
	df = new DecimalFormat("#0.0000000");

	num_itrs = Integer.valueOf(args[0]);
	int initVal = Integer.valueOf(args[1]);
	String filename = args[2];

	br = new BufferedReader(new FileReader(filename));
	String line = br.readLine();
	len = Integer.valueOf(line.split(" ")[0]);
	adj_mat = new int[len][len];
	AT = new int[len][len];
	hub_value = new double[len];
	authority_value = new double[len];

	initializeAdjacentMatrixAndValues2922(initVal);
	if (num_itrs == 0) {
	    ErrorRate2922(1);
	    num_itrs = -5;

	} else if (num_itrs < 0) {
	    ErrorRate2922(1);
	}
	buildMatrix2922(initVal);
	AT = transpose();
	printIters("Base", 0, authority_value, hub_value);
	updateHon2922(num_itrs);
    }
}
