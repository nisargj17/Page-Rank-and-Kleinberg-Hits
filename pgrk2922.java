
//Nisarg Joshi cs610 2922 prp NJIT
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

class Node2922 {
    ArrayList<Integer> incoming, outgoing;
    double pgrk = 0.0;


    public Node2922(double initVal) {
	pgrk = initVal;
	incoming = new ArrayList<Integer>(1);
	outgoing = new ArrayList<Integer>(1);
    }

    public double getPgrk2922() {
	return pgrk;
    }

    public void setNode2922(double initVal) {
	pgrk = initVal;
    }

    public void putNodeIncoming2922(Integer p) {
	while (!incoming.contains(p)) {
	    incoming.add(p);
	    break;
	}

    }

    public void putNodeOutgoing2922(Integer p) {
	while (!outgoing.contains(p)) {
	    outgoing.add(p);
	    break;
	}
    }

}

public class pgrk2922 {
    static double d = 0.85,err_rate=0;
    static DecimalFormat decimalFormat = new DecimalFormat("#.0000000");
    static BufferedReader br;
    static int numb_itr, len;
    static int  isLarge = 0;

    public static void sopln(String s) {
	System.out.println(s);
    }

    public static void sop(String s) {
	System.out.print(s);
    }

    public static void main(String[] args) throws Exception {
	ArrayList<Node2922> nodes;
	numb_itr = Integer.valueOf(args[0]);
	len = Integer.valueOf(args[1]);
	String fn = args[2];

	br = new BufferedReader(new FileReader(fn));
	String line = br.readLine();
	len = Integer.valueOf(line.split(" ")[0]);
	nodes = new ArrayList<Node2922>(len);
	double initVal = getinitVal2922(Integer.valueOf(args[1]));

	if (numb_itr == 0) {
	    err_rate = 1;
	    numb_itr = -5;
	} else if (numb_itr < 0) {
	    err_rate = 1;
	}
	nodes = intializeNodes2922(initVal, nodes);
	nodes = buildGraph2922(nodes);
	printItrs2922("Base", nodes, 0);
	updatePageRanks2922(nodes);
    }
    public static ArrayList<Node2922> intializeNodes2922(double initVal, ArrayList<Node2922> nodes) {
	nodes = new ArrayList<Node2922>(len);
	int i = 0;
	while (i < len) {
	    nodes.add(new Node2922(initVal));
	    i++;
	}
	return nodes;
    }
    public static ArrayList<Node2922> buildGraph2922(ArrayList<Node2922> nodes) throws Exception {
	String line = "";
	while ((line = br.readLine()) != null) {
	    int m, n;
	    m = Integer.valueOf(line.split(" ")[0]);
	    n = Integer.valueOf(line.split(" ")[1]);
	    nodes.get(n).putNodeIncoming2922(m);
	    nodes.get(m).putNodeOutgoing2922(n);
	}
	return nodes;
    }
    public static void printItrs2922(String cased, ArrayList<Node2922> p, int iter) {
	sop(cased + " : " + iter);
	while ((cased == "Base") || isLarge==0) {
	    for (Node2922 Node2922 : p) {
		System.out.printf(" P[" + p.indexOf(Node2922) + "] = %.7f", +(Node2922.getPgrk2922()));
	    }
	    break;
	}
	while (isLarge==1 && !(cased == "Base")) {
	    for (Node2922 Node2922 : p) {
		System.out.printf(" P[" + p.indexOf(Node2922) + "] = %.7f", +(Node2922.getPgrk2922()));
		sopln("");
	    }
	    break;
	}
	sopln("");
    }

    public static double getinitVal2922(int init) {
	double initVal = 0.0;
	if (len > 10) {
	    initVal = 1 / ((double) len);
	    numb_itr = 0;
	    isLarge = 1;
	} else {
	    if (init == 0) {
		initVal = 0.0;
	    } else if (init == 1) {
		initVal = 1.0;
	    } else if (init == -1) {
		double val = 1 / ((double) len);
		initVal = val;
	    } else if (init == -2) {
		initVal = 1 / Math.sqrt((double) len);
	    }

	}
	return initVal;
    }




    public static void updatePageRanks2922(ArrayList<Node2922> nodes) {
	if (err_rate!=1) {
	    updatePGRKIters2922(nodes);
	} else {
	    updatePGRKerrorRate2922(nodes);
	}
    }
    public static void updatePGRKIters2922(ArrayList<Node2922> nodes) {
	double sum = 0.0, restCals = (1 - d) / (double) len;
	int i = 0;
	while (i < numb_itr) {
	    nodes = update2922(sum, restCals, nodes);
	    printItrs2922("Iteration", nodes, i + 1);
	    i++;
	}
    }
    public static void updatePGRKerrorRate2922(ArrayList<Node2922> nodes) {
	double sum = 0.0, restCals = (1 - d) / (double) len, errVal = Math.pow(10.0, numb_itr);

	ArrayList<Node2922> prev_nodes = new ArrayList<Node2922>(len);
	int precision = (int) (1 / errVal), i = 0;
	String format = ("" + precision).substring(1);
	DecimalFormat dF = new DecimalFormat("#." + format);
	do {
	    prev_nodes = getValuesFromNode2922(nodes);
	    nodes = update2922(sum, restCals, nodes);
	    if (isLarge==0)
		printItrs2922("Iteration", nodes, i + 1);
	    i++;
	} while (!isConverged2922(prev_nodes, errVal, nodes, dF));
	while (isLarge==1) {
	    printItrs2922("Iteration", nodes, i + 1);
	    break;
	}
    }
    public static ArrayList<Node2922> update2922(double sum, double restCals, ArrayList<Node2922> nodes) {
	ArrayList<Node2922> t1 = new ArrayList<Node2922>(len);
	for (Node2922 Node2922 : nodes) {
	    sum = 0.0;
	    for (Integer inc : Node2922.incoming) {
		sum += ((nodes.get(inc).getPgrk2922()) / (double) (nodes.get(inc).outgoing.size()));
	    }
	    double fin_Cal = restCals + (d * sum);
	    t1.add(new Node2922(fin_Cal));
	}
	for (Node2922 n : t1) {
	    nodes.get(t1.indexOf(n)).setNode2922(n.getPgrk2922());
	}
	return nodes;
    }


    public static ArrayList<Node2922> getValuesFromNode2922(ArrayList<Node2922> nodes) {
	ArrayList<Node2922> node = new ArrayList<Node2922>(len);
	int i = 0;
	while (i < nodes.size()) {
	    node.add(new Node2922(nodes.get(i++).getPgrk2922()));
	}
	return node;
    }

    public static Boolean isConverged2922(ArrayList<Node2922> prev, double errVal, ArrayList<Node2922> nodes,
					  DecimalFormat dF) {
	boolean check = true, diff[] = new boolean[len];
	int i = 0;
	while (i < len) {
	    diff[i] = Math.abs(nodes.get(i).getPgrk2922() - prev.get(i++).getPgrk2922()) >= errVal ? false : true;
	}
	for (boolean b : diff) {
	    check = check && b;
	}
	return check;
    }






}
