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
	private float weight=0;//һ�仨�Ħ�ֵ
	private String label = null;//����Ϊ���label��outer��
	private String id = null;//����Ϊ������
	private HashMap<String, String> outLinked;//�������ӵ�Ϊkey��value�ĵ�һ���ַ�Ϊ+/-��
	//+��ʾ������ͨ���ӣ�-��ʾƥ�����ӣ��ڶ����ַ�Ϊ���ţ������Ǳ������������ӵ��������Ǹ���
	
	private String circle = null;
	
	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
		System.out.println("����仨��ȦȦ�ǣ�"+this.circle);
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
			System.out.println("�Ѿ���ӹ��õ�");
			return false;
		}else{
			pointSet.add(plabel);
			length++;
			return true;
		}
	}
	
	public boolean isOneInFlower(String plabel){//һ�����Ƿ��ڱ�����
		return pointSet.contains(plabel);
	}
	
	public boolean isInFlower(String plabel_two){//Ϊ i,j���
		String[] temp = plabel_two.split(",");
		return pointSet.contains(temp[0])&&pointSet.contains(temp[1]);
	}
	
	public void markFromWhichPointGoOut(String insideLinkedP,String outLinkedP,String markLabel){//+��ʾ����
															//��Ϊ��ͨ���������������-��ʾ��Ϊ��Ϊƥ������
		if (outLinked==null) {
			outLinked = new HashMap<String, String>();
		}
		outLinked.put(outLinkedP, markLabel+","+insideLinkedP);
		System.out.println("��"+id+"ͨ���ڵ�"+insideLinkedP+"�����"+outLinkedP+"������"+markLabel+"����");
	}
	
	public String findInsidePLinkedToOutP(String outId){
		return outLinked.get(outId);
	}
	
	public void clearOutLinked(){//��ն������ӵļ�¼
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
