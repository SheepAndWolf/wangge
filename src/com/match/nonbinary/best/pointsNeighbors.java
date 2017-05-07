package com.match.nonbinary.best;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class pointsNeighbors {

	/**
	 * @param args
	 */
	private HashMap<String,String> neighbor;
	
	public HashMap<String, String> getNeighbor() {
		return neighbor;
	}

	public void setNeighbor(HashMap<String, String> neighbor) {
		this.neighbor = neighbor;
	}
	
	public boolean removeNeighbor(String keyID){
//		if(neighbor.containsKey(keyID)){
//			neighbor.remove(keyID);
//			System.out.println("存在该keyID,成功删除");
//		}
//		else
//			System.out.println("不用删，本来就没有这个keyID");
		for(Iterator<Map.Entry<String, String>> it = this.getNeighbor().entrySet().iterator();it.hasNext();){
			Map.Entry<String, String> item = it.next();
			if(item.getKey().equals(keyID))
				it.remove();
		}
		return true;
	}
	
	public boolean removeNeighborAsFlower(String[] keyIDs){
		for(Iterator<Map.Entry<String, String>> it = this.getNeighbor().entrySet().iterator();it.hasNext();){
			Map.Entry<String, String> item = it.next();
			if(isInStringArray(keyIDs, item.getKey()))
				it.remove();
		}
		return true;
	}
	
	public boolean addNeighborAsFlower(String[] keyIDs,String flowerID,myState state){//创建一个8，将3，4，5的邻居全部加进来
		HashSet<String> FtempNeighbor = new HashSet<String>();
		String temp = "";
		for (String element : keyIDs) {
			for (String string : neighbor.get(element).split(",")) {
				FtempNeighbor.add(string);
			}
		}
		//一朵花是不可能没有外部的邻居的，至少有一个接入的点，所以temp不可能为""；
		FtempNeighbor.removeAll(Arrays.asList(keyIDs));
		for (String element : FtempNeighbor) {
			temp+=","+element;
		}
		neighbor.put(flowerID, temp.substring(1));
		System.out.println("新增一个邻居关系：花"+flowerID+"的邻居们有这些点"+temp);
		for (String string : temp.substring(1).split(",")) {//string是花的外链接点
			//可能花的某一个点有多个邻居
			for (String element : neighbor.get(string).split(",")) {//element是花的外接点的邻居的一个元素
				if (isInStringArray(keyIDs,element)) {
					state.getFlowers().markFromWhichPointGoOut(flowerID, element, string, "+");
				}
			}
		}
		return true;
	}
	
	public boolean replaceNeighborAsFlower(String[] keyIDs,String flowerID){//3,4,5用8替换
		for (Map.Entry<String, String> element : getNeighbor().entrySet()) {
			String tempNeighbor = "";
			boolean flag = false;
			for (String string : element.getValue().split(",")) {
				if(!isInStringArray(keyIDs, string)){
					tempNeighbor+=","+string;
				}else{
					flag = true;
				}
			}
			if(!tempNeighbor.equals(""))
				tempNeighbor = flag? tempNeighbor.substring(1)+","+flowerID:tempNeighbor.substring(1);
			else
				tempNeighbor = flag?flowerID:"";
			neighbor.put(element.getKey(), tempNeighbor);
		}
		return true;
	}
	
	private boolean isInStringArray(String[] keysArray,String key){
		for (String element : keysArray) {
			if(key.equals(element)){
				System.out.println("这个key在keys数组中");
				return true;
			}
		}
		//用花的ID替代那些点的ID
		return false;
	}

	public boolean addNeighbor(String[] twoPoint){// 参数 [i,j]
		if(neighbor==null)
			neighbor = new HashMap<String,String>();
		if(neighbor.containsKey(twoPoint[0])){
			if(neighbor.containsKey(twoPoint[1])){//点i,j都已存在，才有互相已经添加的风险
				String[] tempSplit = neighbor.get(twoPoint[0]).split(",");
				for (int i=0;i<tempSplit.length;i++) {//仅需要验证一个点的邻居，因为每次都是两个点一起加邻居,不会我加了你 你没加我的情况
					if(tempSplit[i].equals(twoPoint[1])){//说明(i,j)已经存在，如果在init中添加限制条件，则不会出现这种情况
						System.out.println("重复添加弧的时候不会到我这儿来的");
						return false;
					}
				}//从这个循环出来说明这个弧上的点未互相添加邻居
				neighbor.put(twoPoint[1],neighbor.get(twoPoint[1])+","+twoPoint[0]);
			}else{//i在j不在
				neighbor.put(twoPoint[1],twoPoint[0]);
			}
			neighbor.put(twoPoint[0],neighbor.get(twoPoint[0])+","+twoPoint[1]);
		}else{//头一回添加点i
			if(neighbor.containsKey(twoPoint[1])){//i不在，j在
				neighbor.put(twoPoint[1],neighbor.get(twoPoint[1])+","+twoPoint[0]);
			}else{//都不在
				neighbor.put(twoPoint[1],twoPoint[0]);
			}
			neighbor.put(twoPoint[0],twoPoint[1]);
		}
		return true;
	}
	
	public boolean addNeighbor(int i,int j){
		String[] cp = new String[]{""+i,""+j};
		System.out.println(cp[0]+","+cp[1]);
		return addNeighbor(cp);
	}
	
	public boolean addNeighbor(String i,String j){
		String[] cp = {i,j};
		System.out.println(cp[0]+","+cp[1]);
		return addNeighbor(cp);
	}
	
	public String[] getNeighbor(String key){//i
		if(neighbor.containsKey(key)){
			return neighbor.get(key).split(",");
		}else{
			System.out.println("这个点"+key+"没有注册呢");
			return null;
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Map.Entry<String, String> element : neighbor.entrySet()) {
			result += element.getKey()+"的邻居有："+element.getValue()+" | ";
		}
		return "neighbor:" + result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pointsNeighbors p = new pointsNeighbors();
		p.addNeighbor("1","2");
		p.addNeighbor("11","2");
		p.addNeighbor("1","22");
		p.addNeighbor("11","22");
		p.addNeighbor("1","222");
		p.addNeighbor("11","222");
		p.addNeighbor("1","2222");
		p.addNeighbor("11","2222");
		p.addNeighbor("11","5555");
		p.addNeighbor("1","22222");
		String[] a = p.getNeighbor("22");
		for (String string : a) {
			System.out.print(string+" ");
		}
		p.addNeighbor("22","1");
		String[] aa = p.getNeighbor("2");
		for (String string : aa) {
			System.out.println(string+" ");
		}
		for (Map.Entry<String, String> element : p.getNeighbor().entrySet()) {
			System.out.println(element.getKey()+":"+element.getValue());
		}
//		for (Map.Entry<String, String> element : p.getNeighbor().entrySet()) {
//			if(element.getKey().equals("2"))
//				p.removeNeighbor(element.getKey());
//		}
		p.removeNeighborAsFlower(p.getNeighbor("1"));
		p.replaceNeighborAsFlower(p.getNeighbor("1"),"8");
		for (Map.Entry<String, String> element : p.getNeighbor().entrySet()) {
			System.out.println(element.getKey()+":"+element.getValue());
		}
	}

}
