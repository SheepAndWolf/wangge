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
	
	public void updateValue(float change){//���µ�Ħ�ֵ
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
	
	public void update(flowerSet fset){//���£���������
		if(pointSet==null)//��������
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
	
	public float minPointWeightInPlus(){//�ҵ��֣�����С�Ħ�ֵ
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
	public void ridOfExposedPSet(String key){//���뱩¶�㼯��
		if(exposedPset.remove(key))
			System.out.println(exposedPset);
	}
	public boolean addPoint(point p){
		if ( pointSet == null ){
			pointSet = new HashMap<String,point>();
		}
		if(pointSet.containsKey(p.getId())){
			System.out.println("�Ѿ����������");
			return false;
		}else{
			pointSet.put(p.getId(),p);
			System.out.println("ͨ��point����˵�"+p.getId());
			return true;
		}
	}
	public boolean addPoint(String id){
		if ( pointSet == null ){
			pointSet = new HashMap<String,point>();
		}
		if(pointSet.containsKey(id)){
			System.out.println("�Ѿ����������");
			return false;
		}else{
			point p = new point(id);
			pointSet.put(id,p);
			System.out.println("ͨ��string����˵�"+id);
			return true;
		}
	}
	public boolean addPoint(String[] idArray){
		boolean isAddNone = true;
		if ( pointSet == null )
			pointSet = new HashMap<String,point>();
		for(int i=0;i<idArray.length;i++){
			if(pointSet.containsKey(idArray[i])){
				System.out.println("�Ѿ��е�"+idArray[i]+"��");
			}else{
				point p = new point(idArray[i]);
				pointSet.put(idArray[i],p);
				isAddNone = false;
				System.out.println("ͨ��string[]����˵�"+idArray[i]);
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
	
	public float getPointWeightById(String id){//һ�����ID,���ئ�(i)
		return pointSet.get(id).getWeight();
	}
	
	public float getPointWeightById_two(String id){//�ַ���--i,j ���ئ�(i)+��(j)
		return pointSet.get(id.split(",")[0]).getWeight()+pointSet.get(id.split(",")[1]).getWeight();
	}
	
	public int getPointsNum(){//һ��������
		return pointSet.size();
	}
	
	public boolean isPointWeightBigThanZero(String id){//��(id)>0��һ����¶��Ħ�ֵ
		System.out.println("��鱩¶��Ħ�ֵ�Ƿ����0��"+(pointSet.containsKey(id)&&pointSet.get(id).getWeight()>0.001));
		return pointSet.containsKey(id)&&pointSet.get(id).getWeight()>0.001;
	}
	
	public boolean isNoPointWeightBigThanZero(){//���еı�¶��Ħ�ֵ
		for (String element : exposedPset) {
			if(isPointWeightBigThanZero(element))
				return false;
		}
		return true;//û��һ����¶��Ħ�ֵ����0
	}
	
	public boolean isPointHasLabel(String id){//һ����¶���labelֵ
		System.out.println("�����¶���ǲ��Ǳ���ǹ��ˣ�"+(pointSet.containsKey(id)&&pointSet.get(id).getLabel()!=null));
		return pointSet.containsKey(id)&&pointSet.get(id).getLabel()!=null;
	}
	
	public String isEveryPointHasLabel(){//�鿴���б�¶���labelֵ
		System.out.println(exposedPset);
		for (String element : exposedPset) {
			if(!isPointHasLabel(element))
				return element;//����һ��labelΪnull�ĵ��id
		}
		return null;//��¶�㶼��label��
	}
	
	public void setLabelsNull(myState state){//���е��label������Ϊnull
		System.out.println(exposedPset);
		System.out.println(matchedPSet);
		for (point element : pointSet.values()) {
			element.setLabel(null);
		}
		if (state.getFlowers()!=null) {
			state.getFlowers().setLabelsNull();
		}
	}
	
	public boolean markPointLabel(String id,String mark,myState my){//������
		if(pointSet.containsKey(id)){
			pointSet.get(id).setLabel(mark);
			System.out.println("��"+id+"���������"+pointSet.get(id).getLabel());
			return true;
		}else if(Integer.valueOf(id)> my.getPoints().getPointsNum()){//id�ȵ�ĸ�����˵���ǻ�
			return my.getFlowers().markPointLabel(id, mark);
		}
		System.out.println("sorry,�㼯���߻�����û���ֱ��"+id+"�㣡");
		return false;
	}
	
	public int getLabelTypeByID(String id,myState my){//outer����2,inner����1��null����0�������ڷ���-1
													//�Ի���outer����12,inner����11��null����10�������ڷ���9
		if(pointSet.containsKey(id)){
			if(pointSet.get(id).getLabel()==null)
				return 0;
			if(pointSet.get(id).getLabel().equals("outer"))
				return 2;
			else
				return 1;
		}else if(Integer.valueOf(id)> my.getPoints().getPointsNum()){//id�ȵ�ĸ�����˵���ǻ�
			return 10+my.getFlowers().getLabelTypeByID(id);
		}
		return -1;
	}
	
	public boolean isMatchedPoint(String key){
		return matchedPSet.contains(key);
	}
	
	public int addToMatch(String twoPoint){//��һ������·�ϵ���β������ӵ�ƥ��㼯����ȥ
		// -1��ʾֻ�еڶ���ӳɹ� ��1��ʾֻ�е�һ��ӳɹ� 
		String[] twoP = twoPoint.split(",");
		this.ridOfExposedPSet(twoP[0]);
		this.ridOfExposedPSet(twoP[1]);//�ӱ�¶����ȥ��������ƥ���
		if(matchedPSet.contains(twoP[0])){
			System.out.println("��"+twoP[0]+"�Ѿ���ƥ������˰��������ϲ�Ӧ�ó����ظ���ӵ������");
			if(matchedPSet.contains(twoP[1])){
				System.out.println("��"+twoP[1]+"�Ѿ���ƥ������˰��������ϲ�Ӧ�ó����ظ���ӵ������");
				return -2;
			}else{
				matchedPSet.add(twoP[1]);
				System.out.println("matchedPSet�����һ����"+twoP[1]);
				return -1;
			}
		}else{
			matchedPSet.add(twoP[0]);
			System.out.println("matchedPSet�����һ����"+twoP[0]);
			if(matchedPSet.contains(twoP[1])){
				System.out.println("��"+twoP[1]+"�Ѿ���ƥ������˰��������ϲ�Ӧ�ó����ظ���ӵ������");
				return 1;
			}else{
				matchedPSet.add(twoP[1]);
				System.out.println("matchedPSet�����һ����"+twoP[1]);
				return 2;
			}
		}
	}
	
	public void printInfo(){
		for (Map.Entry<String, point> element : pointSet.entrySet()) {
			System.out.print("��"+element.getKey()+ "�Ħ�ֵΪ��" + element.getValue().getWeight()+" ");
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
