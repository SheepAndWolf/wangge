package com.match.nonbinary.flower;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.match.nonbinary.best.myState;

public class flowerSet {// 单例

	/**
	 * @param args
	 */
	// HashMap会进行类型擦除，所以没有数组
	//注：任何一个广泛意义上的点只会出现在一朵花里，不会出现一个点出现在多朵花里
	private HashMap<String, oneFlower> flowers = null;// String标记 整个花朵的序号，数字型字符串
	private HashSet<String> flowersPlus = null;// Integer标记 整个花朵的
	private HashSet<String> flowersMinus = null;
	private HashSet<String> flowersWave = null;
	private int startLabelNum;// 花的起始编号 应该在所有点的编号之后,三种类型的花,如一共有7个点，startLabelNum=8
	private int howMuchFlowers=0;

	private static flowerSet theOnlyFlower = null;

	private flowerSet(int start) {
		startLabelNum = start + 1;
	}

	public void update() {//更新OS集合
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
				System.out.println("flowerSet里的update方法：出现了三种标记以外的花");
			}
		}
	}

	public boolean isInOneFlowerPlus(String keys) {//keys是两个点(i,j)，用于判断这两个点是否在OS+的同一朵花里
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

	public boolean isInOneOfThree(String mark, String id) {//点id是否在os(mark)集合里
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
				System.out.println("在三个OS集合中switch出现了错误");
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

	public static flowerSet getInstanceOfFlower(int start) {// 传来点中最大的编号
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
			System.out.println("这朵花已经存在了,不用添加");
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
		if (flowersTemp.contains(Integer.toString(startLabelNum+howMuchFlowers))) {// 不会出现
			System.out.println("OS" + mark + "已经有这朵花了，不会有这种情况的发生");
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
				if (element.getValue().isInFlower(two_label)) {// pointCouple里的两个点在一朵花里面
					sum += element.getValue().getWeight();
				}
			}
		}
		return sum;
	}
	
	public void findOneFlowerToCreate(String path,myState my){
		System.out.println("出现了一朵花："+path);
		String[] pathPoint = path.split(",");
		String theFlowerStartP = pathPoint[pathPoint.length-1];
		String theFlowerEndP = pathPoint[pathPoint.length-2];
		int start = path.indexOf(","+theFlowerStartP+",")+1;
		int end = path.indexOf(","+theFlowerEndP+",")+theFlowerEndP.length()+1;
		System.out.println(path.substring(start, end)+"是一朵花");
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
		return result.substring(1);//当result一个点都没有时会报错，但是一朵花里不可能没有点
	}
	
	public int getLabelTypeByID(String id){//outer返回2,inner返回1，null返回0，不存在返回-1
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

	public boolean markPointLabel(String id,String mark){//给点标记
		if(flowers.containsKey(id)){
			flowers.get(id).setLabel(mark);
			System.out.println("花"+id+"被标记上了"+flowers.get(id).getLabel());
			return true;
		}
		System.out.println("sorry,花集中没发现标号"+id+"的花！");
		return false;
	}
	
	public String OSsets(){
		return "OS+:"+flowersPlus+"\nOS-:"+flowersMinus+"\nOS~:"+flowersWave;
	}
	
	public String getTheNearestFlowerIdForCommonPoint(String pId){//返回点pId被哪朵花包括了，若不在花里，返回自身
		String result = pId;
		for (Map.Entry<String,oneFlower> element : flowers.entrySet()) {
			if(element.getValue().isOneInFlower(pId)){
				result=element.getKey();
				break;
			}
		}
		return result;
	}
	
	public String getThefarthestFlowerIdForCommonPoint(String pId,String outP){//返回点pId被哪朵花包括了，若不在花里，返回自身
		String result = pId;//outP是外部一个节点 它不在任何一朵花里
		for (Map.Entry<String,oneFlower> element : flowers.entrySet()) {
			if(element.getValue().isOneInFlower(pId)){
				result=element.getKey();
				break;
			}
		}
		if (result.equals(pId)) {//该花不被嵌套在其他花里
			System.out.println("花"+pId+"不被嵌套在其他花里");
		}else {
			System.out.println("花"+pId+"被嵌套在花"+result+"里");
			markFromWhichPointGoOut(result, pId, outP, "+");
			result = getThefarthestFlowerIdForCommonPoint(result, outP);
		}
		return result;
	}
	
	public void setLabelsNull(){//所有点的label都设置为null
		for (oneFlower element : flowers.values()) {
			element.setLabel(null);
		}
	}
	
	public void setOutLinkedsNull(){//所有点的label都设置为null
		for (oneFlower element : flowers.values()) {
			element.clearOutLinked();
		}
	}
	
	//记录一朵花的出口是啥，即花flabel通过自己的点insideLinkp与外面的点outLinkedP,通过marklabel表示这朵花是否被当作匹配弧链接
	public void markFromWhichPointGoOut(String flabel,String insideLinkP,String outLinkedP,String markLabel){
		flowers.get(flabel).markFromWhichPointGoOut(insideLinkP,outLinkedP, markLabel);
	}
	
	public String giveOutPathThroughFlower(String former,String current,String next){//需要走出一条偶数边奇数点的路线来
		String[] testPath=flowers.get(current).getCircle().split(",");
		String result = "";
		if (former==null) {//current是花的id，
			String resultLink = flowers.get(current).findInsidePLinkedToOutP(next);//-,3：+表示通过内部点3匹配链接
			String[] temp = resultLink.split(",");
			System.out.println("花"+current+"通过内部点"+temp[1]+"与外部点"+next+"做了"+temp[0]+"链接");
			for (int i = 1; i < testPath.length; i++) {
				if (testPath[i].equals(temp[1])) {
					if (i%2==0) {//奇数个点则正序
						for (int j = 0; j <= i; j++) {
							result+=","+testPath[j];
						}
					}else {
						for (int j = testPath.length-1; j >= i; j--) {
							result+=","+testPath[j];
						}
					}//这里花是path的第一个元素，所以不能以逗号开始
					System.out.println(result.substring(1)+","+next);//不会出错的
					return result.substring(1);//不可能找不到
				}
			}
			System.out.println("flowerSet的giveOutPathThroughFlower出现了不该出现的情况：花中没找到出口");
		}else {//path有可能是逆着来，即不一定就是从花茎进入的花
			if(flowers.get(current).findInsidePLinkedToOutP(former).split(",")[1].equals(flowers.get(current).getCircle().split(",")[0])){
				System.out.println("flowerSet中的giveOutPathThroughFlower：path是从花茎进入的花");
				String resultLink = flowers.get(current).findInsidePLinkedToOutP(next);
				String[] temp = resultLink.split(",");
				System.out.println("花"+current+"通过内部点"+temp[1]+"与外部点"+next+"有"+temp[0]+"链接");
				for (int i = 1; i < testPath.length; i++) {
					if (testPath[i].equals(temp[1])) {
						if (i%2==0) {//奇数个点则正序
							for (int j = 0; j <= i; j++) {
								result+=","+testPath[j];
							}
						}else {
							for (int j = testPath.length-1; j >= i; j--) {
								result+=","+testPath[j];
							}
						}
						System.out.println(former+result+","+next);
						return result;//不可能找不到
					}
				}
				System.out.println("flowerSet的giveOutPathThroughFlower出现了不该出现的情况：花中没找到出口");
			}
			else if(flowers.get(current).findInsidePLinkedToOutP(next).split(",")[1].equals(flowers.get(current).getCircle().split(",")[0])){
				System.out.println("flowerSet中的giveOutPathThroughFlower：path不是从花茎进入的花");
				String resultLink = flowers.get(current).findInsidePLinkedToOutP(former);
				String[] temp = resultLink.split(",");
				System.out.println("花"+current+"通过内部点"+temp[1]+"与外部点"+former+"做了"+temp[0]+"链接");
				for (int i = 1; i < testPath.length; i++) {
					if (testPath[i].equals(temp[1])) {
						if (i%2==0) {//奇数个点则正序
							for (int j = i; j >= 0; j--) {
								result+=","+testPath[j];
							}
						}else {
							for (int j = i; j <= testPath.length-1; j++) {
								result+=","+testPath[j];
							}
						}
						System.out.println(former+result+","+next);
						return result;//不可能找不到
					}
				}
				System.out.println("flowerSet的giveOutPathThroughFlower出现了不该出现的情况：花中没找到出口");
			}else {
				System.out.println("flowerSet中的giveOutPathThroughFlower：出现了不该出现的情况");
				
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
