package com.match.nonbinary.flower;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.match.nonbinary.best.myState;

public class flowerSet {// ����

	/**
	 * @param args
	 */
	// HashMap��������Ͳ���������û������
	//ע���κ�һ���㷺�����ϵĵ�ֻ�������һ�仨��������һ��������ڶ�仨��
	private HashMap<String, oneFlower> flowers = null;// String��� �����������ţ��������ַ���
	private HashSet<String> flowersPlus = null;// Integer��� ���������
	private HashSet<String> flowersMinus = null;
	private HashSet<String> flowersWave = null;
	private int startLabelNum;// ������ʼ��� Ӧ�������е�ı��֮��,�������͵Ļ�,��һ����7���㣬startLabelNum=8
	private int howMuchFlowers=0;

	private static flowerSet theOnlyFlower = null;

	private flowerSet(int start) {
		startLabelNum = start + 1;
	}

	public void update() {//����OS����
		if (flowers == null)
			return;
		if (flowersPlus != null)
			flowersPlus.clear();
		if (flowersMinus != null)
			flowersMinus.clear();
		if (flowersWave != null)
			flowersWave.clear();
		for (Map.Entry<String, oneFlower> element : flowers.entrySet()) {
			if (element.getValue().getLabel() == null) {
				if (flowersWave == null)
					flowersWave = new HashSet<String>();
				flowersWave.add(element.getKey());
			} else if (element.getValue().getLabel().equals("outer")) {
				if (flowersPlus == null)
					flowersPlus = new HashSet<String>();
				flowersPlus.add(element.getKey());
			} else if (element.getValue().getLabel().equals("inner")) {// inner
				if (flowersMinus == null)
					flowersMinus = new HashSet<String>();
				flowersMinus.add(element.getKey());
			}else{
				System.out.println("flowerSet���update���������������ֱ������Ļ�");
			}
		}
	}

	public boolean isInOneFlowerPlus(String keys) {//keys��������(i,j)�������ж����������Ƿ���OS+��ͬһ�仨��
		if (flowers != null) {
			if (flowersPlus != null && flowersPlus.size() != 0) {
				for (String element : flowersPlus) {
					if (flowers.get(element).isInFlower(keys))
						return true;
				}
			}
		}
		return false;
	}

	public boolean isInOneOfThree(String mark, String id) {//��id�Ƿ���os(mark)������
		if (flowers != null) {
			HashSet<String> tempFlowers = null;
			switch (mark) {
			case "~":
				tempFlowers = flowersWave;
				break;
			case "+":
				tempFlowers = flowersPlus;
				break;
			case "-":
				tempFlowers = flowersMinus;
				break;
			default:
				System.out.println("������OS������switch�����˴���");
				break;
			}
			if (tempFlowers != null && tempFlowers.size() != 0) {
				for (String element : tempFlowers) {
					if (flowers.get(element).isOneInFlower(id))
						return true;
				}
			}
		}
		return false;
	}

	public float minFlowerWeightInMinus() {
		float result = Float.MAX_VALUE;
		if (flowers == null || flowersMinus == null || flowersMinus.size() == 0)
			return result;
		for (String element : flowersMinus) {
			if (flowers.get(element).getWeight() < result)
				result = flowers.get(element).getWeight();
		}
		return result / 2.0f;
	}

	public static flowerSet getInstanceOfFlower(int start) {// �����������ı��
		if (theOnlyFlower == null) {
			theOnlyFlower = new flowerSet(start);
		}
		return theOnlyFlower;
	}

	public int getStartLabelNum() {
		return startLabelNum;
	}

	public void setStartLabelNum(int startLabelNum) {
		this.startLabelNum = startLabelNum;
	}

	public HashSet<String> getFlowersPlus() {
		return flowersPlus;
	}

	public HashSet<String> getFlowersMinus() {
		return flowersMinus;
	}

	public HashSet<String> getFlowersWave() {
		return flowersWave;
	}

	public boolean addFlower(oneFlower f, String mark) {
		if (flowers == null)
			flowers = new HashMap<String, oneFlower>();
		if (flowers.containsValue(f)) {
			System.out.println("��仨�Ѿ�������,�������");
			return false;
		}
		flowers.put(Integer.toString(startLabelNum+howMuchFlowers), f);
		f.setID(Integer.toString(startLabelNum+howMuchFlowers));
		HashSet<String> flowersTemp = null;
		switch (mark) {
		case "+":
			if (flowersPlus == null)
				flowersPlus = new HashSet<String>();
			flowersTemp = flowersPlus;
			break;
		case "-":
			if (flowersMinus == null)
				flowersMinus = new HashSet<String>();
			flowersTemp = flowersMinus;
			break;
		case "~":
			if (flowersWave == null)
				flowersWave = new HashSet<String>();
			flowersTemp = flowersWave;
			break;
		default:
			break;
		}
		if (flowersTemp.contains(Integer.toString(startLabelNum+howMuchFlowers))) {// �������
			System.out.println("OS" + mark + "�Ѿ�����仨�ˣ���������������ķ���");
			return false;
		} else {
			flowersTemp.add(Integer.toString(startLabelNum+howMuchFlowers));
			howMuchFlowers++;
			return true;
		}
	}

	public void setWeightForFlower(float xigema) {
		if (flowersPlus != null) {
			for (String element : flowersPlus) {
				flowers.get(element).setWeight(2 * xigema);
			}
		}
		if (flowersMinus != null) {
			for (String element : flowersMinus) {
				flowers.get(element).setWeight(-2 * xigema);
			}
		}
	}

	public float getSumBTWeight(String two_label) {
		float sum = 0;
		if (flowers != null) {
			for (Map.Entry<String, oneFlower> element : flowers.entrySet()) {
				if (element.getValue().isInFlower(two_label)) {// pointCouple�����������һ�仨����
					sum += element.getValue().getWeight();
				}
			}
		}
		return sum;
	}
	
	public void findOneFlowerToCreate(String path,myState my){
		System.out.println("������һ�仨��"+path);
		String[] pathPoint = path.split(",");
		String theFlowerStartP = pathPoint[pathPoint.length-1];
		String theFlowerEndP = pathPoint[pathPoint.length-2];
		int start = path.indexOf(","+theFlowerStartP+",")+1;
		int end = path.indexOf(","+theFlowerEndP+",")+theFlowerEndP.length()+1;
		System.out.println(path.substring(start, end)+"��һ�仨");
		oneFlower newflower = new oneFlower(path.substring(start, end));
		newflower.setCircle(path.substring(start));
		addFlower(newflower, "+");
		for (Map.Entry<String, oneFlower> element : flowers.entrySet()) {
			System.out.println(element.getKey()+":"+element.getValue());
		}
		my.updateGraph(newflower);
	}
	
	public String getPointsInsideFlower(String oneLabelNum){
		String result="";
		for (String element : flowers.get(oneLabelNum).pointsInMe().split(",")) {
			if(Integer.valueOf(element)>=startLabelNum){
				result+=","+getPointsInsideFlower(element);
			}else{
				result+=","+element;
			}
		}
		return result.substring(1);//��resultһ���㶼û��ʱ�ᱨ������һ�仨�ﲻ����û�е�
	}
	
	public int getLabelTypeByID(String id){//outer����2,inner����1��null����0�������ڷ���-1
		if(flowers.containsKey(id)){
			if(flowers.get(id).getLabel()==null)
				return 0;
			if(flowers.get(id).getLabel().equals("outer"))
				return 2;
			else
				return 1;
		}
		return -1;
	}

	public boolean markPointLabel(String id,String mark){//������
		if(flowers.containsKey(id)){
			flowers.get(id).setLabel(mark);
			System.out.println("��"+id+"���������"+flowers.get(id).getLabel());
			return true;
		}
		System.out.println("sorry,������û���ֱ��"+id+"�Ļ���");
		return false;
	}
	
	public String OSsets(){
		return "OS+:"+flowersPlus+"\nOS-:"+flowersMinus+"\nOS~:"+flowersWave;
	}
	
	public String getTheNearestFlowerIdForCommonPoint(String pId){//���ص�pId���Ķ仨�����ˣ������ڻ����������
		String result = pId;
		for (Map.Entry<String,oneFlower> element : flowers.entrySet()) {
			if(element.getValue().isOneInFlower(pId)){
				result=element.getKey();
				break;
			}
		}
		return result;
	}
	
	public String getThefarthestFlowerIdForCommonPoint(String pId,String outP){//���ص�pId���Ķ仨�����ˣ������ڻ����������
		String result = pId;//outP���ⲿһ���ڵ� �������κ�һ�仨��
		for (Map.Entry<String,oneFlower> element : flowers.entrySet()) {
			if(element.getValue().isOneInFlower(pId)){
				result=element.getKey();
				break;
			}
		}
		if (result.equals(pId)) {//�û�����Ƕ������������
			System.out.println("��"+pId+"����Ƕ������������");
		}else {
			System.out.println("��"+pId+"��Ƕ���ڻ�"+result+"��");
			markFromWhichPointGoOut(result, pId, outP, "+");
			result = getThefarthestFlowerIdForCommonPoint(result, outP);
		}
		return result;
	}
	
	public void setLabelsNull(){//���е��label������Ϊnull
		for (oneFlower element : flowers.values()) {
			element.setLabel(null);
		}
	}
	
	public void setOutLinkedsNull(){//���е��label������Ϊnull
		for (oneFlower element : flowers.values()) {
			element.clearOutLinked();
		}
	}
	
	//��¼һ�仨�ĳ�����ɶ������flabelͨ���Լ��ĵ�insideLinkp������ĵ�outLinkedP,ͨ��marklabel��ʾ��仨�Ƿ񱻵���ƥ�仡����
	public void markFromWhichPointGoOut(String flabel,String insideLinkP,String outLinkedP,String markLabel){
		flowers.get(flabel).markFromWhichPointGoOut(insideLinkP,outLinkedP, markLabel);
	}
	
	public String giveOutPathThroughFlower(String former,String current,String next){//��Ҫ�߳�һ��ż�����������·����
		String[] testPath=flowers.get(current).getCircle().split(",");
		String result = "";
		if (former==null) {//current�ǻ���id��
			String resultLink = flowers.get(current).findInsidePLinkedToOutP(next);//-,3��+��ʾͨ���ڲ���3ƥ������
			String[] temp = resultLink.split(",");
			System.out.println("��"+current+"ͨ���ڲ���"+temp[1]+"���ⲿ��"+next+"����"+temp[0]+"����");
			for (int i = 1; i < testPath.length; i++) {
				if (testPath[i].equals(temp[1])) {
					if (i%2==0) {//��������������
						for (int j = 0; j <= i; j++) {
							result+=","+testPath[j];
						}
					}else {
						for (int j = testPath.length-1; j >= i; j--) {
							result+=","+testPath[j];
						}
					}//���ﻨ��path�ĵ�һ��Ԫ�أ����Բ����Զ��ſ�ʼ
					System.out.println(result.substring(1)+","+next);//��������
					return result.substring(1);//�������Ҳ���
				}
			}
			System.out.println("flowerSet��giveOutPathThroughFlower�����˲��ó��ֵ����������û�ҵ�����");
		}else {//path�п�����������������һ�����Ǵӻ�������Ļ�
			if(flowers.get(current).findInsidePLinkedToOutP(former).split(",")[1].equals(flowers.get(current).getCircle().split(",")[0])){
				System.out.println("flowerSet�е�giveOutPathThroughFlower��path�Ǵӻ�������Ļ�");
				String resultLink = flowers.get(current).findInsidePLinkedToOutP(next);
				String[] temp = resultLink.split(",");
				System.out.println("��"+current+"ͨ���ڲ���"+temp[1]+"���ⲿ��"+next+"��"+temp[0]+"����");
				for (int i = 1; i < testPath.length; i++) {
					if (testPath[i].equals(temp[1])) {
						if (i%2==0) {//��������������
							for (int j = 0; j <= i; j++) {
								result+=","+testPath[j];
							}
						}else {
							for (int j = testPath.length-1; j >= i; j--) {
								result+=","+testPath[j];
							}
						}
						System.out.println(former+result+","+next);
						return result;//�������Ҳ���
					}
				}
				System.out.println("flowerSet��giveOutPathThroughFlower�����˲��ó��ֵ����������û�ҵ�����");
			}
			else if(flowers.get(current).findInsidePLinkedToOutP(next).split(",")[1].equals(flowers.get(current).getCircle().split(",")[0])){
				System.out.println("flowerSet�е�giveOutPathThroughFlower��path���Ǵӻ�������Ļ�");
				String resultLink = flowers.get(current).findInsidePLinkedToOutP(former);
				String[] temp = resultLink.split(",");
				System.out.println("��"+current+"ͨ���ڲ���"+temp[1]+"���ⲿ��"+former+"����"+temp[0]+"����");
				for (int i = 1; i < testPath.length; i++) {
					if (testPath[i].equals(temp[1])) {
						if (i%2==0) {//��������������
							for (int j = i; j >= 0; j--) {
								result+=","+testPath[j];
							}
						}else {
							for (int j = i; j <= testPath.length-1; j++) {
								result+=","+testPath[j];
							}
						}
						System.out.println(former+result+","+next);
						return result;//�������Ҳ���
					}
				}
				System.out.println("flowerSet��giveOutPathThroughFlower�����˲��ó��ֵ����������û�ҵ�����");
			}else {
				System.out.println("flowerSet�е�giveOutPathThroughFlower�������˲��ó��ֵ����");
				
			}
		}
		return current;
	}
	
	public static void main(String[] args) {
		flowerSet ffset = flowerSet.getInstanceOfFlower(10);
		oneFlower f1 = new oneFlower();
		f1.addPoint("1");
		f1.addPoint("11");
		f1.addPoint("111");
		f1.setWeight(10);
		ffset.addFlower(f1, "+");
		oneFlower f2 = new oneFlower();
		f2.addPoint("11");
		f2.addPoint("11");
		f2.addPoint("1");
		f2.setWeight(20);
		ffset.addFlower(f2, "-");
		System.out.println(ffset.getSumBTWeight("1,11"));
	}
}
