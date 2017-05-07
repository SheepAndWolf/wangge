package com.match.nonbinary.best;

import java.util.ArrayList;
import java.util.Scanner;

import com.match.nonbinary.arcsCollection.availableArcsSet;
import com.match.nonbinary.arcsCollection.matchArcsSet;
import com.match.nonbinary.arcsCollection.pointCouple;
import com.match.nonbinary.flower.flowerSet;
import com.match.nonbinary.flower.oneFlower;
import com.match.nonbinary.pointCollection.pointSet;


public class myState {

	/**
	 * @param args
	 */
	boolean debug = true;
	
	private availableArcsSet available ;
	private matchArcsSet match ;
	private flowerSet flowers = null;//从points.getPointsNum获取点的总个数
	//flowerSet.getInstanceOfFlower(points.getPointsNum)
	private pointSet points = null;
	private pointsNeighbors neighbor = null;
	private pointsNeighbors TempNeighbor = null;
	private String path = null; //记录一条路径
	
	private ArrayList<String> LIST = new ArrayList<String>();
	
	private Scanner sin = new Scanner(System.in);
	
	public myState(){
		match = matchArcsSet.getInstanceOfMatch();
	}
	
	public flowerSet getFlowers() {
		return flowers;
	}

	public matchArcsSet getMatch() {
		return match;
	}

	public pointSet getPoints() {
		return points;
	}

	public void getDataByInput(){
		System.out.println("请输入可匹配弧集，形式是(1,2)=9 以回车键结束，若输入完毕，请输入done退出：");
//		available = new availableArcsSet();
		while(true){
			String input = sin.nextLine();
			if(input.equals("done"))
				break;
			else{
				String[] next = input.split("=");
				String arc[] = next[0].substring(1, next[0].length()-1).split(",");
				pointCouple cp = new pointCouple(arc[0], arc[1]);
				cp.setWeight(Integer.valueOf(next[1]));//创建一条弧
				if(available==null)
					available = new availableArcsSet();
				if(available.addArc(cp)){//弧添加成功
					if (points == null)
						points = new pointSet();
					points.addPoint(arc);//加入这两个点
					if(neighbor == null)
						neighbor=new pointsNeighbors();
					neighbor.addNeighbor(arc);
				}
			}
		}
	}
	
	public void getDataByADJMatrix(){//无向图，没有正负
		System.out.println("请输入邻接矩阵，[i,j]表示点i和j的可连接关系，0表示没有连接，>0表示有连接且权值为当前值");
		System.out.print("请输入一共有几个点：");
		int times = 0;
		int nums = sin.nextInt();
		sin.nextLine();
		System.out.println("输入格式：[i,i,i,i,...,i,i,i]，请输入各行数据");
		do{
			times++;
			System.out.println("第"+times+"行:");
			String line = sin.nextLine();
			String[] tempPointArray = line.substring(1, line.length()-1).split(",");
//			int outNum = 0;
//			for (String string : tempPointArray) {
//				outNum = Integer.valueOf(string);
//				System.out.println(outNum);
//			}
//			for(int i = 0;i<times;i++){
//				for(int j = i+1;j<nums;j++){//遍历上三角
				for(int j = times;j<nums;j++){
					if(!tempPointArray[j].equals("0")){//说明相邻，添加一条弧到A中去
						pointCouple cp = new pointCouple(Integer.toString(times-1), Integer.toString(j));
						cp.setWeight(Integer.valueOf(tempPointArray[j]));//创建一条弧
						if(available==null)
							available = new availableArcsSet();
						if(available.addArc(cp)){//弧添加成功
							if (points == null)
								points = new pointSet();
							points.addPoint(""+(times-1));//加入这两个点
							points.addPoint(""+j);//加入这两个点
							if(neighbor == null)
								neighbor=new pointsNeighbors();
							neighbor.addNeighbor(times-1,j);
						}
					}
				}
//			}
		}while(times<nums);
	}
	
	public void getDataByICDMatrix(){//无向图，没有正负
		int anums,pnums;
		String[][] Tarray = null;//点个数个字符串
		if(debug){
			anums = 9;
			pnums = 7;
			Tarray =new String[pnums][];
			Tarray[0]= "8,0,0,0,0,0,0,0,0".split(",");
			Tarray[1]= "8,9,0,0,0,0,0,0,0".split(",");
			Tarray[2]= "0,9,8,7,0,0,0,0,0".split(",");
			Tarray[3]= "0,0,8,0,9,4,5,0,0".split(",");
			Tarray[4]= "0,0,0,7,9,0,0,2,0".split(",");
			Tarray[5]= "0,0,0,0,0,4,0,0,1".split(",");
			Tarray[6]= "0,0,0,0,0,0,5,2,1".split(",");
		}else{
			System.out.println("请输入关联矩阵，行数表示图中点的总个数，列数表示可用弧的条数，0表示没有弧与点无关，>0表示有连接且权值为当前值");
			System.out.print("请输入一共有几个点：");
			int pointTimes = 0;
			pnums = sin.nextInt();
			sin.nextLine();
			System.out.print("请输入一共有几条弧：");
			anums = sin.nextInt();
			sin.nextLine();
			Tarray =new String[pnums][];//点个数个字符串
			System.out.println("输入格式：[i,i,i,i,...,i,i,i]，请输入各行数据");
			do{
				pointTimes++;
				System.out.println("第"+pointTimes+"行:");
				String line = sin.nextLine();
				Tarray[pointTimes-1] = line.substring(1, line.length()-1).split(",");//元素个数与anums一致
			}while(pointTimes<pnums);
		}
		
		for(int i = 0;i<anums;i++){//弧
			String point1 = "";
			String point2 = "";
			for(int j = 0;j<pnums;j++){//点
				if(!Tarray[j][i].equals("0")){
					if(point1.equals(""))
						point1 += j+1;
					else{
						point2 += j+1;
						pointCouple cp = new pointCouple(point1,point2);
						cp.setWeight(Integer.valueOf(Tarray[j][i]));//创建一条弧
						if(available==null)
							available = new availableArcsSet();
						if(available.addArc(cp)){//弧添加成功
							if (points == null)
								points = new pointSet();
							points.addPoint(point1);//加入这两个点
							points.addPoint(point2);//加入这两个点
							if(neighbor == null)
								neighbor=new pointsNeighbors();
							neighbor.addNeighbor(point1,point2);
						}
						System.out.println("这条弧已经检查完毕");
						break;
					}
				}
			}
		}
//		do{
//			pointTimes++;
//			System.out.println("第"+pointTimes+"行:");
//			String line = sin.nextLine();
//			String[] tempPointArray = line.substring(1, line.length()-1).split(",");
//				for(int j = pointTimes;j<pnums;j++){
//					if(!tempPointArray[j].equals("0")){//说明相邻，添加一条弧到A中去
//						pointCouple cp = new pointCouple(Integer.toString(pointTimes-1), Integer.toString(j));
//						cp.setWeight(Integer.valueOf(tempPointArray[j]));//创建一条弧
//						if(available==null)
//							available = new availableArcsSet();
//						if(available.addArc(cp)){//弧添加成功
//							if (points == null)
//								points = new pointSet();
//							points.addPoint(""+(pointTimes-1));//加入这两个点
//							points.addPoint(""+j);//加入这两个点
//							if(neighbor == null)
//								neighbor=new pointsNeighbors();
//							neighbor.addNeighbor(pointTimes-1,j);
//						}
//					}
//				}
//		}while(pointTimes<pnums);
	}
	
	public void createTempNeighbor(String[] key){//参数 [i,j]  创建临时的可匹配集的邻居结构
		if(TempNeighbor == null)
			TempNeighbor=new pointsNeighbors();
		TempNeighbor.addNeighbor(key);
	}
	
	public boolean setInitState(){
		//设置每个point的初始值，想想β值怎么搞，再计算一下每个边的Cπ值的计算
		if(available!=null){
			points.setInitValue(available.getTheBigestWeight());
	 		points.printInfo();
	 		//每条弧的Cπ值
	 		available.updatePieWeights(this);
	 		return true;
		}else{
			System.out.println("没有可用弧，结束！");
			return false;
		}
	}
	
	public boolean shallWeContinue = true;
	public void calculateState(){
		if(points.isNoPointWeightBigThanZero()){//没有α>0的暴露点；
			//
		}else{
			findERoad();
		}
	}
	
	public void findERoad(){
		//String startPoint;
		int flag = 0;//1表示遍历结束，2表示list里没有元素，
		String oneExposedID = points.isEveryPointHasLabel();
		if(oneExposedID==null){//所有暴露点都被标记过了，jump5
			flag = 1;
			System.out.println("暴露点都被标记了哟，该跳到步骤5了");
			return;
		}else{//oneExposedID里存着一个label为null的暴露点的ID
			this.LIST.add(oneExposedID);
			this.points.markPointLabel(oneExposedID, "outer",this);
			do{
				if(this.LIST.isEmpty()){
					flag = 2;
					break;
				}else{
					String firstElement = this.LIST.remove(this.LIST.size()-1);
					//startPoint = firstElement;//记录起点
					switch (points.getLabelTypeByID(firstElement,this)) {//在list中的点一定是被标记了的
					case 2:
						System.out.println("点"+firstElement+"外点找路径");
						flag = findOneOuterP(firstElement);
						break;
					case 1:
						System.out.println("点"+firstElement+"内点找路径");
						findOneInnerP(firstElement);
						break;
					case 0:
						System.out.println("list中的点竟然没有被标记");
						break;
					case -1://不是花
						System.out.println("list中的点竟然没在pointset中登记");
						break;
					case 12://
						System.out.println("花"+firstElement+"外点找路径");
						flag = findOneOuterP(firstElement);
						break;
					case 11://
						System.out.println("花"+firstElement+"内点找路径");
						findOneInnerP(firstElement);
						break;
					case 10:
						System.out.println("list中的花竟然没有被标记");
						break;
					case 9://不是花
						System.out.println("list中的花竟然没在flowerset中登记");
						break;
					default:
						System.out.println("points.getLabelTypeById返回了不该返回的数字");
						break;
					}
				}
				if(flag == 4)
					break;
				if(flag == 35){
					//处理花，现在是外点
					if(flowers==null)
						flowers=flowerSet.getInstanceOfFlower(points.getPointsNum());
					flowers.findOneFlowerToCreate(path,this);
					points.setLabelsNull(this);
					path=null;
					break;
				}
			}while(true);
		}
		calculateState();
		
	}
	/*/
	 * 解决一个点有多个邻居但是只看第一个点的问题
	 * 亦即找的邻居不能是path的最后一个点
	 */
	public int findOneOuterP(String formerPID){
		String[] tempN = TempNeighbor.getNeighbor(formerPID);
		if(tempN==null){
			return 32;
		}
		for (String string : tempN) {
			System.out.println(string);
		}
		String theFormer =null;
		if(path!=null){
			System.out.println("这时候的path为："+path);
			theFormer = path.split(",")[path.split(",").length-2];
			System.out.println("前一个点的id为："+theFormer);
		}
		for (String oneN : tempN) {
			if(oneN.equals(theFormer)){
				if(tempN.length==1){
					System.out.println("找邻居的时候遇上了我的前一个点且我只有这么一个邻居，这时候我要把path置为空");
					path = null;
					continue;
				}
				System.out.println("找邻居的时候遇上了我的前一个点但我不只有这么一个邻居，这时候我不需要把path置为空");
				continue;
			}
			if(points.getLabelTypeByID(oneN,this)==2){//花，jump3.5
				path+=","+oneN;
				return 35;
			}else if(points.isExposedPoint(oneN)){//邻居是暴露点,找到一条增广路
				if(path == null){
					path = formerPID + "," + oneN;
					System.out.println(path+"|");
				}else{
					path+=","+oneN;
					System.out.println(path+"|");
				}
				//jump 3.6
				//展开路径上的花，修改path，jump4
				if (flowers!=null) {
					path = openFlowerInPath(path);
				}
				match.extendMatchPath(path, this);
				//展开β=0的花，
				points.setLabelsNull(this);
				path=null;
				return 4;
			}else if(points.isMatchedPoint(oneN)&& points.getLabelTypeByID(oneN,this)==0){//邻居是匹配点且没有标号
				if(path == null){
					path = formerPID + "," + oneN;
					System.out.println(path+"|");
				}else{
					path+=","+oneN;
					System.out.println(path+"|");
				}
				points.markPointLabel(oneN, "inner",this);
				this.LIST.add(oneN);
				//jump 3.2
			}else{//可能是外点发现邻居被标记为内点(未肯定)，但是如果是内点，则一定是路径上我的上一个点
				//jump 3.2
				path = null;
				
			}
			return 32;
		}
		return 32;
	}
	
	public void findOneInnerP(String formerPID){//内点找匹配点是不可能找到前一个点去的
		String[] tempN = TempNeighbor.getNeighbor(formerPID);//必定有匹配的点存在
		String matchedPtoFormer = null;
		for (String oneN : tempN) {
			if(match.isInMatch(oneN+","+formerPID)){//
				matchedPtoFormer = oneN;
				break;
			}
			//邻居有花
			if(Integer.valueOf(oneN)>points.getPointsNum()){
				for (String string : flowers.getPointsInsideFlower(oneN).split(",")) {
					if(match.isInMatch(string+","+formerPID)){//花里有一个点跟前一个点是匹配
						matchedPtoFormer = oneN;//则花作为一个点与其匹配，这里是花的序号
						flowers.markFromWhichPointGoOut(oneN, string, formerPID,"-");
						break;
					}
				}
			}
		}
		if(matchedPtoFormer == null){
			System.out.println("肯定出错了，不会到我这儿来的");
		}else{
			if(points.getLabelTypeByID(matchedPtoFormer,this)==1||(flowers!=null&&flowers.getLabelTypeByID(matchedPtoFormer)==1)){//inner碰到inner，感觉不会出现这种情况啊=w=
				System.out.println("一定要查一查这是怎么做到的，inner碰到inner啦");
				//jump 3.5
			}else if(points.getLabelTypeByID(matchedPtoFormer,this)==0||(flowers!=null&&flowers.getLabelTypeByID(matchedPtoFormer)==0)){
				//邻居无标号，记录路径，jump3.2
				if(path == null){//不可能出现这种情况，匹配弧不可能作为路的地第一条弧
					path = formerPID + "," + matchedPtoFormer;
					System.out.println("不可能出现这种情况，匹配弧不可能作为路的地第一条弧");
				}else{
					path+=","+matchedPtoFormer;
					System.out.println(path+"|");
				}
				points.markPointLabel(matchedPtoFormer, "outer",this);//可能给花加上标记
				this.LIST.add(matchedPtoFormer);
				//jump 3.2
			}else{
				//jump 3.2
				path = null;
			}
		}
	}
	
	public float findTheMin(float a,float b,float c,float d){
		a=a<b?a:b;
		c=c<d?c:d;
		return a<c?a:c;
	}
	
	public void updateAandB(float change){
		if(points!=null){
			points.updateValue(change);
	 		points.printInfo();
		}
		if(flowers!=null){
			flowers.setWeightForFlower(change);
		}
	}
	
	public void updateMyState(){
		//花的集合更新
		//重新计算Cπ，α，β
		float a2 = Float.MAX_VALUE;
		if(flowers!=null){
			flowers.update();
			System.out.println(flowers.OSsets());
		}
		points.update(flowers);
		System.out.println(points.VPsets());
		available.update(points, flowers);
		float a1 = points.minPointWeightInPlus();
		if(flowers!=null){
			a2 = flowers.minFlowerWeightInMinus();
		}
		float a3 = available.minArcsPieWeightInPlus();
		float a4 = available.minArcsPieWeightInPP();
		float a = findTheMin(a1,a2,a3,a4);
		System.out.println("a1:"+a1+"	a2:"+a2+"	a3:"+a3+"   a4:"+a4);
		System.out.println("最小的α值为："+a); 
		//更新α和β值
		updateAandB(a);
//		TempNeighbor=null;
		if (points.isNoPointWeightBigThanZero()) {
			System.out.println("已经没有α大于0的暴露点了");
			return;
		}
		available.updatePieWeights(this);
		points.setLabelsNull(this);
	}
	
	public void updateGraph(oneFlower one){//花导致的更新图
		System.out.print("打印临时的邻居");
		System.out.println(TempNeighbor);
		TempNeighbor.addNeighborAsFlower(one.pointsInMe().split(","),one.getID(),this);
		System.out.print("打印临时的邻居");
		System.out.println(TempNeighbor);
		TempNeighbor.removeNeighborAsFlower(one.pointsInMe().split(","));
		System.out.print("打印临时的邻居");
		System.out.println(TempNeighbor);
		TempNeighbor.replaceNeighborAsFlower(one.pointsInMe().split(","),one.getID());
		System.out.print("打印临时的邻居");
		System.out.println(TempNeighbor);
	}
	
	public String showMeTheNearestFlower(String[] keyCouple){//参数[i,j]一条弧
		String result = "";
		for (String string : keyCouple) {
			if (flowers!=null) {
				result+=","+flowers.getTheNearestFlowerIdForCommonPoint(string);
			}else {
				result+=","+string;
			}
		}
		return result.substring(1);
	}
	
	public String showMeTheFarthestFlower(String[] keyCouple){//参数[i,j]一条弧
		String temp0 = keyCouple[0];
		String temp1 = keyCouple[1];
		if (flowers!=null) {
			temp0 = flowers.getTheNearestFlowerIdForCommonPoint(temp0);
			temp1 = flowers.getTheNearestFlowerIdForCommonPoint(temp1);
			if (temp0.equals(keyCouple[0])&&temp1.equals(keyCouple[1])){//不变，都不在花里
				System.out.println("点"+keyCouple[0]+"和点"+keyCouple[1]+"都不在花里");
			}else if (!temp0.equals(keyCouple[0])) {//第一个点在花里
				if (!temp1.equals(keyCouple[1])) {//第二点也在花里，可能一开始不是同一朵花，但是他们最远的花必须是同一朵
					System.out.println("我认为经过Cπ计算来的新的边，不可能出现是一朵大花里头");
					System.out.println("这时候，一定有一个点的爸爸就是那朵大花，但是大花不一定不被更大花包括");
					return null;
				}else {//第二点不在花里,这样的话，第一个点所在最近花如果存在上层的花，该上层花要以下层花为内接点与第二点外接
					flowers.markFromWhichPointGoOut(temp0, keyCouple[0], temp1, "+");
					temp0 = flowers.getThefarthestFlowerIdForCommonPoint(temp0,temp1);//第一点去寻找最远花，
				}
			}else{////第一个点不在花里，第二个点在花里
				flowers.markFromWhichPointGoOut(temp1, keyCouple[1], temp0, "+");
				temp1 = flowers.getThefarthestFlowerIdForCommonPoint(temp1,temp0);//第二点去寻找最远花，
			}
		}
		return temp0+","+temp1;
	}
	
	public String openFlowerInPath(String path){//展开path上的花,已经确认出现了花，但是不肯定路径上有没有花
		String resultPath = "";
		String[] pointInPath = path.split(",");
		for (int i = 0;i < pointInPath.length;i++) {
			if (Integer.valueOf(pointInPath[i])>points.getPointsNum()) {
				if (i==0) {//花作为起点，
					resultPath+=flowers.giveOutPathThroughFlower(null, pointInPath[i], pointInPath[i+1]);
				}else {//不会有花接花的情况
					resultPath+=flowers.giveOutPathThroughFlower(pointInPath[i-1],pointInPath[i],pointInPath[i+1]);//在一条增广路上，花不能是第一个点，也不可能是最后一个点
				}
			}else {
				resultPath+=","+pointInPath[i];
			}
		}
		return resultPath.substring(1);
	}
	
	public static void main(String[] args) {
//		final HashMap<String,oneFlower> matchSet = new HashMap<String,oneFlower>();
//		matchSet.put("101",new pointCouple(2,4));
//		oneFlower a = new oneFlower();
//		a.addPoint(1);
//		a.addPoint(11);
//		a.addPoint(111);
//		matchSet.put("111",a);
//		oneFlower b = new oneFlower();
//		b.addPoint(111);
//		b.addPoint(11);
//		b.addPoint(1);
//		System.out.println(matchSet.containsValue(b));
//		System.out.println( matchSet.get(new pointCouple(1,2)));
//		System.out.println(matchSet);
//		System.out.println( new pointCouple(1,2).equals(new pointCouple(2,1)) );
//		int[] a = new int[10];
//		for(int i=0;i<10;i++){
//			a[i]=i;
//		}
//		for (int b:a) {
//			System.out.println(b);
//		}
		myState my = new myState();
//		my.getDataByInput();
//		my.getDataByADJMatrix();
		my.getDataByICDMatrix();
		System.out.println(my.neighbor);
		System.out.println(my.available);
		System.out.println(my.points);
		if(!my.setInitState()){//初始化失败，程序结束
			return;
		}else{
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("打印临时的邻居");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("打印临时的邻居");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("打印临时的邻居");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("打印临时的邻居");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("打印临时的邻居");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.getMatch().printMatchInfo();
		}
	}

}
