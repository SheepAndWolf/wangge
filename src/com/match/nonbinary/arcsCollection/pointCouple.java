package com.match.nonbinary.arcsCollection;

public class pointCouple {

	/**
	 * @param args
	 */
	private String pointA;
	private String pointB;
	private float weight;//C
	private int isMatch=0;//X(i)
	private float pieWeight=-1.0f;//Cπ(i,j)
//	private int aWeight;//α
	public pointCouple(String i,String j){
		pointA=i;
		pointB=j;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getIsMatch() {
		return isMatch;
	}
	public void setIsMatch(int isMatch) {
		this.isMatch = isMatch;
	}
	public float getPieWeight() {
		return pieWeight;
	}
	public boolean updatePieWeight(float newValue){
		float formerPie=pieWeight;
		pieWeight = newValue - weight;
		if(pieWeight<0.001&&(formerPie>0.001||Math.abs(formerPie+1)<0.001))//新的Cπ==0且旧Cπ要么是初始值要么不是0
			return true;
		return false;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int a = (pointA == null) ? 0 : pointA.hashCode();
		int b = (pointB == null) ? 0 : pointB.hashCode();
		result = prime*result*(a+b) + prime*prime + a+b ;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		pointCouple other = (pointCouple) obj;
		if ((pointA.equals( other.pointA )&& pointB.equals( other.pointB )) || 
				(pointA.equals( other.pointB )&& pointB.equals( other.pointA )))
			return true;
		return false;
	}
	@Override
	public String toString() {
		return pointA + "," + pointB ;
	}
	
	public static void main(String[] args){
		float a = 0.0f;
		float b = 0.0f;
		System.out.println(a-b<0.001);
	}
	
}
