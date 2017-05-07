package com.match.nonbinary.pointCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.match.nonbinary.best.myState;
import com.match.nonbinary.flower.flowerSet;

public class pointSet {

	/**
	 * @param args
	 */
	private HashMap<String,point> pointSet;
	private HashSet<String> exposedPset = new HashSet<String>();
	private HashSet<String> matchedPSet = new HashSet<String>();
	private HashSet<String> pointsPlus = null;
	private HashSet<String> pointsMinus = null;
	private HashSet<String> pointsWave = null;
	
//	private myState mystate = null;
	
	public void updateValue(float change){//更新点的α值
		if(pointsPlus!=null){
			for (String string : pointsPlus) {
				pointSet.get(string).changeWeight(-change);
			}
		}
		if(pointsMinus!=null){
			for (String string : pointsMinus) {
				pointSet.get(string).changeWeight(change);
			}
		}
	}
	
	public void update(flowerSet fset){//更新Ｖ三个集合
		if(pointSet==null)//不会这样
			return;
		if(pointsPlus!=null)
			pointsPlus.clear();
		if(pointsMinus!=null)
			pointsMinus.clear();
		if(pointsWave!=null)
			pointsWave.clear();
		for (Map.Entry<String, point> element : pointSet.entrySet()) {
			if(element.getValue().getLabel()=="outer" || (fset!=null&&fset.isInOneOfThree("+",element.getKey()))){
				if(pointsPlus==null)
					pointsPlus = new HashSet<String>();
				pointsPlus.add(element.getKey());
			}else if(element.getValue().getLabel()=="inner" || (fset!=null&&fset.isInOneOfThree("-",element.getKey()))){//inner
				if(pointsMinus==null)
					pointsMinus = new HashSet<String>();
				pointsMinus.add(element.getKey());
			}else if(element.getValue().getLabel()==null || (fset!=null&&fset.isInOneOfThree("~",element.getKey()))){
				if(pointsWave==null)
					pointsWave = new HashSet<String>();
				pointsWave.add(element.getKey());
			}
		}
	}
	
	public boolean isInAPlus(String keys){
		String[] twokey = keys.split(",");
		if(pointsPlus!=null&&pointsPlus.contains(twokey[0])&&pointsPlus.contains(twokey[1]))
			return true;
		return false;
	}
	
	public boolean isInAPP(String keys){
		String[] twokey = keys.split(",");
		if(pointsPlus!=null&&pointsWave!=null&&((pointsPlus.contains(twokey[0])&&pointsWave.contains(twokey[1]))||(pointsWave.contains(twokey[0])&&pointsPlus.contains(twokey[1]))))
			return true;
		return false;
	}
	
	public float minPointWeightInPlus(){//找到Ｖ＋中最小的α值
		float result = Float.MAX_VALUE;
		if(pointSet==null||pointsPlus==null||pointsPlus.size()==0)
			return result;
		for (String element : pointsPlus) {
			if(pointSet.get(element).getWeight()<result)
				result = pointSet.get(element).getWeight();
		}
		return result;
	}
	
	public boolean isExposedPoint(String key){
		return exposedPset.contains(key);
	}
	public void ridOfExposedPSet(String key){//脱离暴露点集合
		if(exposedPset.remove(key))
			System.out.println(exposedPset);
	}
	public boolean addPoint(point p){
		if ( pointSet == null ){
			pointSet = new HashMap<String,point>();
		}
		if(pointSet.containsKey(p.getId())){
			System.out.println("已经有这个点了");
			return false;
		}else{
			pointSet.put(p.getId(),p);
			System.out.println("通过point添加了点"+p.getId());
			return true;
		}
	}
	public boolean addPoint(String id){
		if ( pointSet == null ){
			pointSet = new HashMap<String,point>();
		}
		if(pointSet.containsKey(id)){
			System.out.println("已经有这个点了");
			return false;
		}else{
			point p = new point(id);
			pointSet.put(id,p);
			System.out.println("通过string添加了点"+id);
			return true;
		}
	}
	public boolean addPoint(String[] idArray){
		boolean isAddNone = true;
		if ( pointSet == null )
			pointSet = new HashMap<String,point>();
		for(int i=0;i<idArray.length;i++){
			if(pointSet.containsKey(idArray[i])){
				System.out.println("已经有点"+idArray[i]+"了");
			}else{
				point p = new point(idArray[i]);
				pointSet.put(idArray[i],p);
				isAddNone = false;
				System.out.println("通过string[]添加了点"+idArray[i]);
			}
		}
		return !isAddNone;
	}
	
	public void setInitValue(float a_er_fa){
		for (point element : pointSet.values()) {
			element.setWeight(a_er_fa/2.0f);
		}
		exposedPset.addAll(pointSet.keySet());
	}
	
	public float getPointWeightById(String id){//一个点的ID,返回α(i)
		return pointSet.get(id).getWeight();
	}
	
	public float getPointWeightById_two(String id){//字符串--i,j 返回α(i)+α(j)
		return pointSet.get(id.split(",")[0]).getWeight()+pointSet.get(id.split(",")[1]).getWeight();
	}
	
	public int getPointsNum(){//一共几个点
		return pointSet.size();
	}
	
	public boolean isPointWeightBigThanZero(String id){//α(id)>0，一个暴露点的α值
		System.out.println("检查暴露点的α值是否大于0："+(pointSet.containsKey(id)&&pointSet.get(id).getWeight()>0.001));
		return pointSet.containsKey(id)&&pointSet.get(id).getWeight()>0.001;
	}
	
	public boolean isNoPointWeightBigThanZero(){//所有的暴露点的α值
		for (String element : exposedPset) {
			if(isPointWeightBigThanZero(element))
				return false;
		}
		return true;//没有一个暴露点的α值大于0
	}
	
	public boolean isPointHasLabel(String id){//一个暴露点的label值
		System.out.println("这个暴露点是不是被标记过了？"+(pointSet.containsKey(id)&&pointSet.get(id).getLabel()!=null));
		return pointSet.containsKey(id)&&pointSet.get(id).getLabel()!=null;
	}
	
	public String isEveryPointHasLabel(){//查看所有暴露点的label值
		System.out.println(exposedPset);
		for (String element : exposedPset) {
			if(!isPointHasLabel(element))
				return element;//返回一个label为null的点的id
		}
		return null;//暴露点都有label了
	}
	
	public void setLabelsNull(myState state){//所有点的label都设置为null
		System.out.println(exposedPset);
		System.out.println(matchedPSet);
		for (point element : pointSet.values()) {
			element.setLabel(null);
		}
		if (state.getFlowers()!=null) {
			state.getFlowers().setLabelsNull();
		}
	}
	
	public boolean markPointLabel(String id,String mark,myState my){//给点标记
		if(pointSet.containsKey(id)){
			pointSet.get(id).setLabel(mark);
			System.out.println("点"+id+"被标记上了"+pointSet.get(id).getLabel());
			return true;
		}else if(Integer.valueOf(id)> my.getPoints().getPointsNum()){//id比点的个数大，说明是花
			return my.getFlowers().markPointLabel(id, mark);
		}
		System.out.println("sorry,点集或者花集中没发现标号"+id+"点！");
		return false;
	}
	
	public int getLabelTypeByID(String id,myState my){//outer返回2,inner返回1，null返回0，不存在返回-1
													//对花，outer返回12,inner返回11，null返回10，不存在返回9
		if(pointSet.containsKey(id)){
			if(pointSet.get(id).getLabel()==null)
				return 0;
			if(pointSet.get(id).getLabel().equals("outer"))
				return 2;
			else
				return 1;
		}else if(Integer.valueOf(id)> my.getPoints().getPointsNum()){//id比点的个数大，说明是花
			return 10+my.getFlowers().getLabelTypeByID(id);
		}
		return -1;
	}
	
	public boolean isMatchedPoint(String key){
		return matchedPSet.contains(key);
	}
	
	public int addToMatch(String twoPoint){//将一个增广路上的首尾两个点加到匹配点集合中去
		// -1表示只有第二点加成功 ，1表示只有第一点加成功 
		String[] twoP = twoPoint.split(",");
		this.ridOfExposedPSet(twoP[0]);
		this.ridOfExposedPSet(twoP[1]);//从暴露点中去掉这两个匹配点
		if(matchedPSet.contains(twoP[0])){
			System.out.println("点"+twoP[0]+"已经在匹配点里了啊，理论上不应该出现重复添加的情况啊");
			if(matchedPSet.contains(twoP[1])){
				System.out.println("点"+twoP[1]+"已经在匹配点里了啊，理论上不应该出现重复添加的情况啊");
				return -2;
			}else{
				matchedPSet.add(twoP[1]);
				System.out.println("matchedPSet添加了一个点"+twoP[1]);
				return -1;
			}
		}else{
			matchedPSet.add(twoP[0]);
			System.out.println("matchedPSet添加了一个点"+twoP[0]);
			if(matchedPSet.contains(twoP[1])){
				System.out.println("点"+twoP[1]+"已经在匹配点里了啊，理论上不应该出现重复添加的情况啊");
				return 1;
			}else{
				matchedPSet.add(twoP[1]);
				System.out.println("matchedPSet添加了一个点"+twoP[1]);
				return 2;
			}
		}
	}
	
	public void printInfo(){
		for (Map.Entry<String, point> element : pointSet.entrySet()) {
			System.out.print("点"+element.getKey()+ "的α值为：" + element.getValue().getWeight()+" ");
		}
		System.out.println("");
	}
	
	@Override
	public String toString() {
		String response ="";
		for (Map.Entry<String, point> element : pointSet.entrySet()) {
			response+=element.getValue()+" ";
		}
		return response;
	}
	
	public String VPsets(){
		return "V+:"+pointsPlus+"\nV-:"+pointsMinus+"\nV~:"+pointsWave;
	}

	public static void main(String[] args){
		pointSet s=new pointSet();
		s.addPoint(new point("12"));
		s.addPoint("123");
		String[] a= {"123","321"};
		s.addPoint(a);
		for (Map.Entry<String, point> element : s.pointSet.entrySet()) {
			System.out.println(element.getKey()+ "," + element.getValue().getWeight());
		}
		s.setInitValue(15);
		for (Map.Entry<String, point> element : s.pointSet.entrySet()) {
			System.out.println(element.getKey()+ "," + element.getValue().getWeight());
		}
	}
}
