package ke.ac.green;

import java.math.BigDecimal;
import java.util.Vector;

public class VecTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Integer> vec=new Vector<>();
		vec.add(1);
		vec.add(1);
		vec.add(1);
		vec.add(1);
		
		vec.set(2, 3);
		
		vec.setElementAt(5, 2);
		
		System.out.println(vec);
		
		
		String s="02";
		
		int n=Integer.parseInt(s);
		System.out.println(n);
		BigDecimal bd= new BigDecimal(0);
		System.out.println(bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
//		System.out.println(bd.toString());
//		System.out.println(String.format("%d.0",bd.intValue()));

	}

}
