import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Assignment21
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		boolean check = false; 
		int len = 0;
		double den = 0;
		do {
			try{
				System.out.print("Enter array length: ");
				len = input.nextInt();
				if(len < 1){
					throw new NegativeArraySizeException();
				}
				check = true;
			}catch (NegativeArraySizeException e){
				System.out.print("invalid length\n");
			}
		}while (check != true);
		do{
			check=false;
			try{
				System.out.print("Enter array density: ");
				den = input.nextDouble();
				if(den<0.0||den>= 1.0){
					throw new Exception();
				}
				check = true;
			}catch(Exception e){
				System.out.print("invalid density\n");
			}
		}while (check != true);
		run(len,den);
		input.close();
	}

	private static void run(int len, double den){
		int[] array;
		int[] sparsetodense;
		ArrayList<int[]> densetosparse = new ArrayList<int[]>();
		ArrayList<int[]> sparse = new ArrayList<int[]>();
		
		long stime1 = System.nanoTime();
		array = createDense(len,den);
		double etime1 = (System.nanoTime() - stime1)/ 1000000.0;
		System.out.printf("create dense length: %d time: %f%n", len, etime1);
		
		long stime2 = System.nanoTime();
		densetosparse = convertoSparse(array);
		double etime2 = (System.nanoTime() - stime2)/ 1000000.0;
		System.out.printf("converted to sparse length: %d time: %f%n", densetosparse.size(), etime2);
		
		long stime3 = System.nanoTime();
		sparse = createSparse(len,den);
		double etime3 = (System.nanoTime() - stime3)/ 1000000.0;
		System.out.printf("create sparse length: %d time: %f%n", sparse.size(), etime3);
		
		long stime4 = System.nanoTime();
		sparsetodense = converttoDense(sparse,len);
		double etime4 = (System.nanoTime() - stime4)/ 1000000.0;
		System.out.printf("converted to dense length: %d time: %f%n", sparsetodense.length, etime4);
		
		long stime5 = System.nanoTime();
		densemax(array);
		double etime5 = (System.nanoTime() - stime5)/ 1000000.0;
		System.out.printf("dense find time: %f%n",etime5);

		long stime6 = System.nanoTime();
		sparsemax(sparse);
		double etime6 = (System.nanoTime() - stime6)/ 1000000.0;
		System.out.printf("sparse find time: %f%n",etime6);
	}

	private static ArrayList<int[]> createSparse(int len, double den) {
		ArrayList<int[]> list = new ArrayList<int[]>();
		Random random = new Random();
		for (int n = 0; n < len; ++n) {
			if (random.nextDouble() < den) {		
				int[]temp = {n, (random.nextInt(999999)+1)};
				list.add(temp);
			}
		}
		return list;
	}
	
	private static int[] createDense(int len, double den) {
		int[] array = new int[len];
		Random random = new Random();
		for (int i = 0; i < len; ++i) {
			if (random.nextDouble() < den) {		
				array[i]= (random.nextInt(999999)+1);
			}else{
				array[i] = 0;
			}
		}
		return array;
	}

	private static ArrayList<int[]> convertoSparse(int[] array) {
		ArrayList<int[]> sparse = new ArrayList<int[]>();
		int i = 0;
		for(int n : array) {
			if(n!=0){
				int[] array2 = {i,n};
				sparse.add(array2);
			}
			++i;
		}
		return sparse;
	}
	
	private static int[] converttoDense(ArrayList<int[]> sparse, int len) {
		int[] array = new int[len];
		for(int n = 0; n < sparse.size(); ++n){
			array[sparse.get(n)[0]] = sparse.get(n)[1];
		}
		return array;
	}
	
	private static void sparsemax(ArrayList<int[]> sparse) {
		int max = 0;
		int i = 0;
		for (int[] n: sparse){
			if(n[1]>max){
				max = n[1];
				i = n[0];
			}
		}	
		System.out.printf("find max(sparse): %d at: %d%n",max,i);
	}

	private static void densemax(int[] array) {
		int max = 0;
		int i = 0;
		for (int n =0; n<array.length; ++n){
			if(max < array[n]){
				max = array[n];
				i = n;
			}
		}
		System.out.printf("find max(dense): %d at: %d%n",max,i);
	}
}