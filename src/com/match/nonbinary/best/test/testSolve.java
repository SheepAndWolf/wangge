package com.match.nonbinary.best.test;

import java.util.HashMap;

import com.match.nonbinary.flower.oneFlower;

public class testSolve {

	/**
	 * @param args
	 * 
[1,1,0,0,0,0,0,0,0]
��2��:
[1,0,1,1,0,0,0,0,0]
��3��:
[0,1,0,0,0,1,0,1,0]
��4��:
[0,0,1,0,1,0,0,0,0]
��5��:
[0,0,0,1,0,1,1,0,0]
��6��:
[0,0,0,0,1,0,1,0,1]
��7��:
[0,0,0,0,0,0,0,1,1]

[8,0,0,0,0,0,0,0,0]
��2��:
[8,9,0,0,0,0,0,0,0]
��3��:
[0,9,8,7,0,0,0,0,0]
��4��:
[0,0,8,0,9,4,5,0,0]
��5��:
[0,0,0,7,9,0,0,2,0]
��6��:
[0,0,0,0,0,4,0,0,1]
��7��:
[0,0,0,0,0,0,5,2,1]
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,oneFlower> flowers=new HashMap<String,oneFlower>();
		oneFlower a = new oneFlower();
		a.addPoint("1");
		a.addPoint("11");
		a.addPoint("111");
		oneFlower b = new oneFlower();
		b.addPoint("1");
		b.addPoint("11");
		b.addPoint("11111");
		flowers.put("11", a);
		flowers.put("11", b);
		System.out.println(flowers.size());
		System.out.println(flowers.containsKey(11));
	}

}
