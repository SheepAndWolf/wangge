package com.match.nonbinary.arcsCollection;

import java.util.HashSet;

import com.match.nonbinary.best.myState;

public class matchArcsSet {//����

	/**
	 * @param args
	 */
//	private final HashSet<pointCouple> arcsSet;
	private static matchArcsSet theOnlyMatch = null;
	private HashSet<String> couple;
	
	public boolean addCouple(String keyCouple){// ���� i,j
		if(couple==null)
			couple = new HashSet<String>();
		String[] reverseKey = keyCouple.split(",");
//		if(couple.contains(keyCouple)||couple.contains(reverseKey[1]+","+reverseKey[0])){
		if(couple.contains(keyCouple)){
			System.out.println("�û��Ѿ���ƥ�仡����");
			return false;
		}
		couple.add(keyCouple);
		couple.add(reverseKey[1]+","+reverseKey[0]);//һ�μ����� i,j j,i
		System.out.println("��("+keyCouple+")���뵽ƥ�仡����");
		return true;
	}
	
	public boolean removeCouple(String keyCouple){
		if(couple==null)
			return false;
		String[] reverseKey = keyCouple.split(",");
		if(couple.contains(keyCouple)){
			couple.remove(keyCouple);
			couple.remove(reverseKey[1]+","+reverseKey[0]);//һ��ɾ���� i,j j,i
			System.out.println("��("+keyCouple+")��ƥ�仡����ɾ��");
			return true;
		}
		System.out.println("�û�����ƥ�仡����");
		return false;
	}
	
	public boolean isInMatch(String twoP){
		if(couple!=null){
			if(couple.contains(twoP)){
				System.out.println("��("+twoP+")��ƥ�仡����");
				return true;
			}
		}
		return false;
	}
	
	private matchArcsSet(){
//		arcsSet = new HashSet<pointCouple>();
	}
	
	public static matchArcsSet getInstanceOfMatch(){
		if(theOnlyMatch == null)
			theOnlyMatch = new matchArcsSet();
		return theOnlyMatch;
	}
	
//	public boolean addArc(pointCouple p){
//		if(arcsSet.contains(p)){
//			System.out.println("�Ѿ�������ôһ����");
//			return false;
//		}else{
//			arcsSet.add(p);
//			return true;
//		}
//	}
//	
//	public boolean isInMatch(pointCouple p){
//		return arcsSet.contains(p);
//	}
	
	public void printMatchInfo(){
		System.out.print("��ǰƥ��Ϊ��");
		for (String element : couple) {
			System.out.print("��("+element+") ");
		}
		System.out.println("");
	}
	
	public boolean extendMatchPath(String path,myState nowState){// 1,2,3,4,5,6   ����·�ϸ�������ȡ��
															//��Ҫ��state��points������matchedPSet�����ú�
											//��Ҫ��state��points������exposedPSet�����ú�
		String[] pointOnPath = path.split(",");
		for(int i = 0; i<pointOnPath.length-1;i++){
			if(i%2==0){
				addCouple(pointOnPath[i]+","+pointOnPath[i+1]);
			}else{
				removeCouple(pointOnPath[i]+","+pointOnPath[i+1]);
			}
		}
		nowState.getPoints().addToMatch(pointOnPath[0]+","+pointOnPath[pointOnPath.length-1]);
		printMatchInfo();
		return true;
	}
}
