package com.match.nonbinary.arcsCollection;

import java.util.HashSet;

import com.match.nonbinary.best.myState;

public class matchArcsSet {//单例

	/**
	 * @param args
	 */
//	private final HashSet<pointCouple> arcsSet;
	private static matchArcsSet theOnlyMatch = null;
	private HashSet<String> couple;
	
	public boolean addCouple(String keyCouple){// 参数 i,j
		if(couple==null)
			couple = new HashSet<String>();
		String[] reverseKey = keyCouple.split(",");
//		if(couple.contains(keyCouple)||couple.contains(reverseKey[1]+","+reverseKey[0])){
		if(couple.contains(keyCouple)){
			System.out.println("该弧已经在匹配弧集了");
			return false;
		}
		couple.add(keyCouple);
		couple.add(reverseKey[1]+","+reverseKey[0]);//一次加两条 i,j j,i
		System.out.println("弧("+keyCouple+")加入到匹配弧集中");
		return true;
	}
	
	public boolean removeCouple(String keyCouple){
		if(couple==null)
			return false;
		String[] reverseKey = keyCouple.split(",");
		if(couple.contains(keyCouple)){
			couple.remove(keyCouple);
			couple.remove(reverseKey[1]+","+reverseKey[0]);//一次删两条 i,j j,i
			System.out.println("弧("+keyCouple+")从匹配弧集中删除");
			return true;
		}
		System.out.println("该弧不在匹配弧集里");
		return false;
	}
	
	public boolean isInMatch(String twoP){
		if(couple!=null){
			if(couple.contains(twoP)){
				System.out.println("弧("+twoP+")在匹配弧集里");
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
//			System.out.println("已经存在这么一条弧");
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
		System.out.print("当前匹配为：");
		for (String element : couple) {
			System.out.print("边("+element+") ");
		}
		System.out.println("");
	}
	
	public boolean extendMatchPath(String path,myState nowState){// 1,2,3,4,5,6   增广路上各个弧的取反
															//还要将state中points的属性matchedPSet给设置好
											//还要将state中points的属性exposedPSet给设置好
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
