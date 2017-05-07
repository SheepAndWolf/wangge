package com.match.nonbinary.arcsCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.match.nonbinary.best.myState;
import com.match.nonbinary.flower.flowerSet;
import com.match.nonbinary.pointCollection.pointSet;

public class availableArcsSet {

	/**
	 * @param args
	 */

	final private HashMap<String,pointCouple> arcsSet;//String= i,j
	
	private HashSet<String> arcsPlus = null;//String= i,j
	private HashSet<String> arcsPP = null;
	
	public void update(pointSet pset,flowerSet fset){
		if(arcsSet==null)
			return;
		if(arcsPlus!=null)
			arcsPlus.clear();
		if(arcsPP!=null)
			arcsPP.clear();
		for (String element : arcsSet.keySet()) {
			if(pset.isInAPlus(element)&&(fset==null||!fset.isInOneFlowerPlus(element))){
				if(arcsPlus==null)
					arcsPlus = new HashSet<String>();
				arcsPlus.add(element);
			}
			if(pset.isInAPP(element)&&(fset==null||!fset.isInOneFlowerPlus(element))){
				if(arcsPP==null)
					arcsPP = new HashSet<String>();
				arcsPP.add(element);
			}
		}
	}
	
	public float minArcsPieWeightInPlus(){
		float result = Float.MAX_VALUE;
		if(arcsSet==null||arcsPlus==null||arcsPlus.size()==0)
			return result;
		for (String element : arcsPlus) {
			if(arcsSet.get(element).getPieWeight()<result)
				result = arcsSet.get(element).getPieWeight();
		}
		return result/2.0f;
	}
	
	public float minArcsPieWeightInPP(){
		float result = Float.MAX_VALUE;
		if(arcsSet==null||arcsPP==null||arcsPP.size()==0)
			return result;
		for (String element : arcsPP) {
			if(arcsSet.get(element).getPieWeight()<result)
				result = arcsSet.get(element).getPieWeight();
		}
		return result;
	}
	
	public availableArcsSet(){
		arcsSet = new HashMap<String,pointCouple>();
	}
	
	public boolean addArc(pointCouple p){
		if(arcsSet.containsValue(p)){
			System.out.println("�Ѿ�������ôһ����"+p);
			return false;
		}else{
			arcsSet.put(p.toString(),p);
			System.out.println("������һ���»�("+p.toString()+")");
			return true;
		}
	}
	
	public boolean isInAvailable(String key){
		return arcsSet.containsKey(key);
	}
	
	public pointCouple getElemenByKey(String key){
		if(isInAvailable(key))
			return arcsSet.get(key);
		System.out.println("�������������");
		return null;
	}
	
	public float getTheBigestWeight(){
		float i = -Float.MAX_VALUE;
		for (Map.Entry<String, pointCouple> element : arcsSet.entrySet()) {
			if(element.getValue().getWeight()>i)
				i = element.getValue().getWeight();
		}
		return i;
	}
	
	public void updatePieWeights(myState state){
		for (Map.Entry<String, pointCouple> element : arcsSet.entrySet()) {
//			String[] twoPoint = element.getKey().split(",");
//			float a1 = pSet.getPointWeightById(twoPoint[0]);
//			float a2 = pSet.getPointWeightById(twoPoint[1]);
			float a = state.getPoints().getPointWeightById_two(element.getKey());//��(i)+��(j)
			//�ڻ���
			float sumBT = 0;
			if(state.getFlowers()!=null){//��ȡ�µ�sumֵ
				sumBT = state.getFlowers().getSumBTWeight(element.getKey());
			}
			if(element.getValue().updatePieWeight(a+sumBT)){//C��==0��֮ǰ��C��!=0,�ӵ�tempAvailable�У�������tempNeighbor�����
//				System.out.println(state.showMeTheNearestFlower(element.getKey().split(",")));
				String afterFlowerInclude = state.showMeTheFarthestFlower(element.getKey().split(","));
				if (afterFlowerInclude==null) {
					System.out.println("availableArcsSet��updatePieWeights�����C��Ϊ0�ı�ʱ���ָñ���һ�仨��");
				}else {
					state.createTempNeighbor(afterFlowerInclude.split(","));
				}
			}
		}
	}
	
	public void printPieWeight(){
		for (Map.Entry<String, pointCouple> element : arcsSet.entrySet()) {
			System.out.println(element.getKey()+"��C��:"+element.getValue().getPieWeight());
		}
	}

	@Override
	public String toString() {
		String string = "" ;
		for (pointCouple element : arcsSet.values()) {
			string+="("+element.toString()+")";
		}
		return string;
	}

}
