package com.match.nonbinary.flower;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class oneFlower {

	/**
	 * @param args
	 */
	private HashSet<String> pointSet ;
	private int length ;
	private float weight=0;//一朵花的β值
	private String label = null;//花作为点的label，outer等
	private String id = null;//花作为点的序号
	private HashMap<String, String> outLinked;//以外链接点为key，value的第一个字符为+/-，
	//+表示对外普通链接，-表示匹配链接，第二个字符为逗号，后面是本花内与外链接点相连的那个点
	
	private String circle = null;
	
	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
		System.out.println("我这朵花的圈圈是："+this.circle);
	}

	public HashSet<String> getPointSet() {
		return pointSet;
	}

	public int getLength() {
		return length;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight += weight;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String ID) {
		this.id = ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pointSet == null) ? 0 : pointSet.hashCode());
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
		oneFlower other = (oneFlower) obj;
		if (pointSet == null) {
			if (other.pointSet != null)
				return false;
		} else if (!pointSet.equals(other.pointSet))
			return false;
		return true;
	}

	public oneFlower(){
		super();
		pointSet = new HashSet<String>();
		length = pointSet.size();
	}
	
	public oneFlower(String path){
		super();
		pointSet = new HashSet<String>();
		pointSet.addAll(Arrays.asList(path.split(",")));
		length = pointSet.size();
	}

	public boolean addPoint(String plabel){
		if(pointSet.contains(plabel)){
			System.out.println("已经添加过该点");
			return false;
		}else{
			pointSet.add(plabel);
			length++;
			return true;
		}
	}
	
	public boolean isOneInFlower(String plabel){//一个点是否在本花中
		return pointSet.contains(plabel);
	}
	
	public boolean isInFlower(String plabel_two){//为 i,j设计
		String[] temp = plabel_two.split(",");
		return pointSet.contains(temp[0])&&pointSet.contains(temp[1]);
	}
	
	public void markFromWhichPointGoOut(String insideLinkedP,String outLinkedP,String markLabel){//+表示本花
															//作为普通链接与外点相连，-表示作为作为匹配链接
		if (outLinked==null) {
			outLinked = new HashMap<String, String>();
		}
		outLinked.put(outLinkedP, markLabel+","+insideLinkedP);
		System.out.println("花"+id+"通过内点"+insideLinkedP+"与外点"+outLinkedP+"建立了"+markLabel+"链接");
	}
	
	public String findInsidePLinkedToOutP(String outId){
		return outLinked.get(outId);
	}
	
	public void clearOutLinked(){//清空对外链接的记录
		if (outLinked!=null) {
			outLinked.clear();
		}
	}
	
	public String pointsInMe(){
		String result="";
		for (String element : pointSet) {
			result+=","+element;
		}
		return result.substring(1);
	}

	@Override
	public String toString() {
		return "oneFlower [pointSet=" + pointSet + ", weight=" + weight
				+ ", label=" + label + "]";
	}

}
