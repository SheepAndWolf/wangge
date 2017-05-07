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
//			System.out.println("���ڸ�keyID,�ɹ�ɾ��");
//		}
//		else
//			System.out.println("����ɾ��������û�����keyID");
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
	
	public boolean addNeighborAsFlower(String[] keyIDs,String flowerID,myState state){//����һ��8����3��4��5���ھ�ȫ���ӽ���
		HashSet<String> FtempNeighbor = new HashSet<String>();
		String temp = "";
		for (String element : keyIDs) {
			for (String string : neighbor.get(element).split(",")) {
				FtempNeighbor.add(string);
			}
		}
		//һ�仨�ǲ�����û���ⲿ���ھӵģ�������һ������ĵ㣬����temp������Ϊ""��
		FtempNeighbor.removeAll(Arrays.asList(keyIDs));
		for (String element : FtempNeighbor) {
			temp+=","+element;
		}
		neighbor.put(flowerID, temp.substring(1));
		System.out.println("����һ���ھӹ�ϵ����"+flowerID+"���ھ�������Щ��"+temp);
		for (String string : temp.substring(1).split(",")) {//string�ǻ��������ӵ�
			//���ܻ���ĳһ�����ж���ھ�
			for (String element : neighbor.get(string).split(",")) {//element�ǻ�����ӵ���ھӵ�һ��Ԫ��
				if (isInStringArray(keyIDs,element)) {
					state.getFlowers().markFromWhichPointGoOut(flowerID, element, string, "+");
				}
			}
		}
		return true;
	}
	
	public boolean replaceNeighborAsFlower(String[] keyIDs,String flowerID){//3,4,5��8�滻
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
				System.out.println("���key��keys������");
				return true;
			}
		}
		//�û���ID�����Щ���ID
		return false;
	}

	public boolean addNeighbor(String[] twoPoint){// ���� [i,j]
		if(neighbor==null)
			neighbor = new HashMap<String,String>();
		if(neighbor.containsKey(twoPoint[0])){
			if(neighbor.containsKey(twoPoint[1])){//��i,j���Ѵ��ڣ����л����Ѿ���ӵķ���
				String[] tempSplit = neighbor.get(twoPoint[0]).split(",");
				for (int i=0;i<tempSplit.length;i++) {//����Ҫ��֤һ������ھӣ���Ϊÿ�ζ���������һ����ھ�,�����Ҽ����� ��û���ҵ����
					if(tempSplit[i].equals(twoPoint[1])){//˵��(i,j)�Ѿ����ڣ������init����������������򲻻�����������
						System.out.println("�ظ���ӻ���ʱ�򲻻ᵽ���������");
						return false;
					}
				}//�����ѭ������˵��������ϵĵ�δ��������ھ�
				neighbor.put(twoPoint[1],neighbor.get(twoPoint[1])+","+twoPoint[0]);
			}else{//i��j����
				neighbor.put(twoPoint[1],twoPoint[0]);
			}
			neighbor.put(twoPoint[0],neighbor.get(twoPoint[0])+","+twoPoint[1]);
		}else{//ͷһ����ӵ�i
			if(neighbor.containsKey(twoPoint[1])){//i���ڣ�j��
				neighbor.put(twoPoint[1],neighbor.get(twoPoint[1])+","+twoPoint[0]);
			}else{//������
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
			System.out.println("�����"+key+"û��ע����");
			return null;
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Map.Entry<String, String> element : neighbor.entrySet()) {
			result += element.getKey()+"���ھ��У�"+element.getValue()+" | ";
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
