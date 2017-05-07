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
	private flowerSet flowers = null;//��points.getPointsNum��ȡ����ܸ���
	//flowerSet.getInstanceOfFlower(points.getPointsNum)
	private pointSet points = null;
	private pointsNeighbors neighbor = null;
	private pointsNeighbors TempNeighbor = null;
	private String path = null; //��¼һ��·��
	
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
		System.out.println("�������ƥ�仡������ʽ��(1,2)=9 �Իس�����������������ϣ�������done�˳���");
//		available = new availableArcsSet();
		while(true){
			String input = sin.nextLine();
			if(input.equals("done"))
				break;
			else{
				String[] next = input.split("=");
				String arc[] = next[0].substring(1, next[0].length()-1).split(",");
				pointCouple cp = new pointCouple(arc[0], arc[1]);
				cp.setWeight(Integer.valueOf(next[1]));//����һ����
				if(available==null)
					available = new availableArcsSet();
				if(available.addArc(cp)){//����ӳɹ�
					if (points == null)
						points = new pointSet();
					points.addPoint(arc);//������������
					if(neighbor == null)
						neighbor=new pointsNeighbors();
					neighbor.addNeighbor(arc);
				}
			}
		}
	}
	
	public void getDataByADJMatrix(){//����ͼ��û������
		System.out.println("�������ڽӾ���[i,j]��ʾ��i��j�Ŀ����ӹ�ϵ��0��ʾû�����ӣ�>0��ʾ��������ȨֵΪ��ǰֵ");
		System.out.print("������һ���м����㣺");
		int times = 0;
		int nums = sin.nextInt();
		sin.nextLine();
		System.out.println("�����ʽ��[i,i,i,i,...,i,i,i]���������������");
		do{
			times++;
			System.out.println("��"+times+"��:");
			String line = sin.nextLine();
			String[] tempPointArray = line.substring(1, line.length()-1).split(",");
//			int outNum = 0;
//			for (String string : tempPointArray) {
//				outNum = Integer.valueOf(string);
//				System.out.println(outNum);
//			}
//			for(int i = 0;i<times;i++){
//				for(int j = i+1;j<nums;j++){//����������
				for(int j = times;j<nums;j++){
					if(!tempPointArray[j].equals("0")){//˵�����ڣ����һ������A��ȥ
						pointCouple cp = new pointCouple(Integer.toString(times-1), Integer.toString(j));
						cp.setWeight(Integer.valueOf(tempPointArray[j]));//����һ����
						if(available==null)
							available = new availableArcsSet();
						if(available.addArc(cp)){//����ӳɹ�
							if (points == null)
								points = new pointSet();
							points.addPoint(""+(times-1));//������������
							points.addPoint(""+j);//������������
							if(neighbor == null)
								neighbor=new pointsNeighbors();
							neighbor.addNeighbor(times-1,j);
						}
					}
				}
//			}
		}while(times<nums);
	}
	
	public void getDataByICDMatrix(){//����ͼ��û������
		int anums,pnums;
		String[][] Tarray = null;//��������ַ���
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
			System.out.println("�������������������ʾͼ�е���ܸ�����������ʾ���û���������0��ʾû�л�����޹أ�>0��ʾ��������ȨֵΪ��ǰֵ");
			System.out.print("������һ���м����㣺");
			int pointTimes = 0;
			pnums = sin.nextInt();
			sin.nextLine();
			System.out.print("������һ���м�������");
			anums = sin.nextInt();
			sin.nextLine();
			Tarray =new String[pnums][];//��������ַ���
			System.out.println("�����ʽ��[i,i,i,i,...,i,i,i]���������������");
			do{
				pointTimes++;
				System.out.println("��"+pointTimes+"��:");
				String line = sin.nextLine();
				Tarray[pointTimes-1] = line.substring(1, line.length()-1).split(",");//Ԫ�ظ�����anumsһ��
			}while(pointTimes<pnums);
		}
		
		for(int i = 0;i<anums;i++){//��
			String point1 = "";
			String point2 = "";
			for(int j = 0;j<pnums;j++){//��
				if(!Tarray[j][i].equals("0")){
					if(point1.equals(""))
						point1 += j+1;
					else{
						point2 += j+1;
						pointCouple cp = new pointCouple(point1,point2);
						cp.setWeight(Integer.valueOf(Tarray[j][i]));//����һ����
						if(available==null)
							available = new availableArcsSet();
						if(available.addArc(cp)){//����ӳɹ�
							if (points == null)
								points = new pointSet();
							points.addPoint(point1);//������������
							points.addPoint(point2);//������������
							if(neighbor == null)
								neighbor=new pointsNeighbors();
							neighbor.addNeighbor(point1,point2);
						}
						System.out.println("�������Ѿ�������");
						break;
					}
				}
			}
		}
//		do{
//			pointTimes++;
//			System.out.println("��"+pointTimes+"��:");
//			String line = sin.nextLine();
//			String[] tempPointArray = line.substring(1, line.length()-1).split(",");
//				for(int j = pointTimes;j<pnums;j++){
//					if(!tempPointArray[j].equals("0")){//˵�����ڣ����һ������A��ȥ
//						pointCouple cp = new pointCouple(Integer.toString(pointTimes-1), Integer.toString(j));
//						cp.setWeight(Integer.valueOf(tempPointArray[j]));//����һ����
//						if(available==null)
//							available = new availableArcsSet();
//						if(available.addArc(cp)){//����ӳɹ�
//							if (points == null)
//								points = new pointSet();
//							points.addPoint(""+(pointTimes-1));//������������
//							points.addPoint(""+j);//������������
//							if(neighbor == null)
//								neighbor=new pointsNeighbors();
//							neighbor.addNeighbor(pointTimes-1,j);
//						}
//					}
//				}
//		}while(pointTimes<pnums);
	}
	
	public void createTempNeighbor(String[] key){//���� [i,j]  ������ʱ�Ŀ�ƥ�伯���ھӽṹ
		if(TempNeighbor == null)
			TempNeighbor=new pointsNeighbors();
		TempNeighbor.addNeighbor(key);
	}
	
	public boolean setInitState(){
		//����ÿ��point�ĳ�ʼֵ�������ֵ��ô�㣬�ټ���һ��ÿ���ߵ�C��ֵ�ļ���
		if(available!=null){
			points.setInitValue(available.getTheBigestWeight());
	 		points.printInfo();
	 		//ÿ������C��ֵ
	 		available.updatePieWeights(this);
	 		return true;
		}else{
			System.out.println("û�п��û���������");
			return false;
		}
	}
	
	public boolean shallWeContinue = true;
	public void calculateState(){
		if(points.isNoPointWeightBigThanZero()){//û�Ц�>0�ı�¶�㣻
			//
		}else{
			findERoad();
		}
	}
	
	public void findERoad(){
		//String startPoint;
		int flag = 0;//1��ʾ����������2��ʾlist��û��Ԫ�أ�
		String oneExposedID = points.isEveryPointHasLabel();
		if(oneExposedID==null){//���б�¶�㶼����ǹ��ˣ�jump5
			flag = 1;
			System.out.println("��¶�㶼�������Ӵ������������5��");
			return;
		}else{//oneExposedID�����һ��labelΪnull�ı�¶���ID
			this.LIST.add(oneExposedID);
			this.points.markPointLabel(oneExposedID, "outer",this);
			do{
				if(this.LIST.isEmpty()){
					flag = 2;
					break;
				}else{
					String firstElement = this.LIST.remove(this.LIST.size()-1);
					//startPoint = firstElement;//��¼���
					switch (points.getLabelTypeByID(firstElement,this)) {//��list�еĵ�һ���Ǳ�����˵�
					case 2:
						System.out.println("��"+firstElement+"�����·��");
						flag = findOneOuterP(firstElement);
						break;
					case 1:
						System.out.println("��"+firstElement+"�ڵ���·��");
						findOneInnerP(firstElement);
						break;
					case 0:
						System.out.println("list�еĵ㾹Ȼû�б����");
						break;
					case -1://���ǻ�
						System.out.println("list�еĵ㾹Ȼû��pointset�еǼ�");
						break;
					case 12://
						System.out.println("��"+firstElement+"�����·��");
						flag = findOneOuterP(firstElement);
						break;
					case 11://
						System.out.println("��"+firstElement+"�ڵ���·��");
						findOneInnerP(firstElement);
						break;
					case 10:
						System.out.println("list�еĻ���Ȼû�б����");
						break;
					case 9://���ǻ�
						System.out.println("list�еĻ���Ȼû��flowerset�еǼ�");
						break;
					default:
						System.out.println("points.getLabelTypeById�����˲��÷��ص�����");
						break;
					}
				}
				if(flag == 4)
					break;
				if(flag == 35){
					//���������������
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
	 * ���һ�����ж���ھӵ���ֻ����һ���������
	 * �༴�ҵ��ھӲ�����path�����һ����
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
			System.out.println("��ʱ���pathΪ��"+path);
			theFormer = path.split(",")[path.split(",").length-2];
			System.out.println("ǰһ�����idΪ��"+theFormer);
		}
		for (String oneN : tempN) {
			if(oneN.equals(theFormer)){
				if(tempN.length==1){
					System.out.println("���ھӵ�ʱ���������ҵ�ǰһ��������ֻ����ôһ���ھӣ���ʱ����Ҫ��path��Ϊ��");
					path = null;
					continue;
				}
				System.out.println("���ھӵ�ʱ���������ҵ�ǰһ���㵫�Ҳ�ֻ����ôһ���ھӣ���ʱ���Ҳ���Ҫ��path��Ϊ��");
				continue;
			}
			if(points.getLabelTypeByID(oneN,this)==2){//����jump3.5
				path+=","+oneN;
				return 35;
			}else if(points.isExposedPoint(oneN)){//�ھ��Ǳ�¶��,�ҵ�һ������·
				if(path == null){
					path = formerPID + "," + oneN;
					System.out.println(path+"|");
				}else{
					path+=","+oneN;
					System.out.println(path+"|");
				}
				//jump 3.6
				//չ��·���ϵĻ����޸�path��jump4
				if (flowers!=null) {
					path = openFlowerInPath(path);
				}
				match.extendMatchPath(path, this);
				//չ����=0�Ļ���
				points.setLabelsNull(this);
				path=null;
				return 4;
			}else if(points.isMatchedPoint(oneN)&& points.getLabelTypeByID(oneN,this)==0){//�ھ���ƥ�����û�б��
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
			}else{//��������㷢���ھӱ����Ϊ�ڵ�(δ�϶�)������������ڵ㣬��һ����·�����ҵ���һ����
				//jump 3.2
				path = null;
				
			}
			return 32;
		}
		return 32;
	}
	
	public void findOneInnerP(String formerPID){//�ڵ���ƥ����ǲ������ҵ�ǰһ����ȥ��
		String[] tempN = TempNeighbor.getNeighbor(formerPID);//�ض���ƥ��ĵ����
		String matchedPtoFormer = null;
		for (String oneN : tempN) {
			if(match.isInMatch(oneN+","+formerPID)){//
				matchedPtoFormer = oneN;
				break;
			}
			//�ھ��л�
			if(Integer.valueOf(oneN)>points.getPointsNum()){
				for (String string : flowers.getPointsInsideFlower(oneN).split(",")) {
					if(match.isInMatch(string+","+formerPID)){//������һ�����ǰһ������ƥ��
						matchedPtoFormer = oneN;//����Ϊһ��������ƥ�䣬�����ǻ������
						flowers.markFromWhichPointGoOut(oneN, string, formerPID,"-");
						break;
					}
				}
			}
		}
		if(matchedPtoFormer == null){
			System.out.println("�϶������ˣ����ᵽ���������");
		}else{
			if(points.getLabelTypeByID(matchedPtoFormer,this)==1||(flowers!=null&&flowers.getLabelTypeByID(matchedPtoFormer)==1)){//inner����inner���о�����������������=w=
				System.out.println("һ��Ҫ��һ��������ô�����ģ�inner����inner��");
				//jump 3.5
			}else if(points.getLabelTypeByID(matchedPtoFormer,this)==0||(flowers!=null&&flowers.getLabelTypeByID(matchedPtoFormer)==0)){
				//�ھ��ޱ�ţ���¼·����jump3.2
				if(path == null){//�����ܳ������������ƥ�仡��������Ϊ·�ĵص�һ����
					path = formerPID + "," + matchedPtoFormer;
					System.out.println("�����ܳ������������ƥ�仡��������Ϊ·�ĵص�һ����");
				}else{
					path+=","+matchedPtoFormer;
					System.out.println(path+"|");
				}
				points.markPointLabel(matchedPtoFormer, "outer",this);//���ܸ������ϱ��
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
		//���ļ��ϸ���
		//���¼���C�У�������
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
		System.out.println("��С�Ħ�ֵΪ��"+a); 
		//���¦��ͦ�ֵ
		updateAandB(a);
//		TempNeighbor=null;
		if (points.isNoPointWeightBigThanZero()) {
			System.out.println("�Ѿ�û�Ц�����0�ı�¶����");
			return;
		}
		available.updatePieWeights(this);
		points.setLabelsNull(this);
	}
	
	public void updateGraph(oneFlower one){//�����µĸ���ͼ
		System.out.print("��ӡ��ʱ���ھ�");
		System.out.println(TempNeighbor);
		TempNeighbor.addNeighborAsFlower(one.pointsInMe().split(","),one.getID(),this);
		System.out.print("��ӡ��ʱ���ھ�");
		System.out.println(TempNeighbor);
		TempNeighbor.removeNeighborAsFlower(one.pointsInMe().split(","));
		System.out.print("��ӡ��ʱ���ھ�");
		System.out.println(TempNeighbor);
		TempNeighbor.replaceNeighborAsFlower(one.pointsInMe().split(","),one.getID());
		System.out.print("��ӡ��ʱ���ھ�");
		System.out.println(TempNeighbor);
	}
	
	public String showMeTheNearestFlower(String[] keyCouple){//����[i,j]һ����
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
	
	public String showMeTheFarthestFlower(String[] keyCouple){//����[i,j]һ����
		String temp0 = keyCouple[0];
		String temp1 = keyCouple[1];
		if (flowers!=null) {
			temp0 = flowers.getTheNearestFlowerIdForCommonPoint(temp0);
			temp1 = flowers.getTheNearestFlowerIdForCommonPoint(temp1);
			if (temp0.equals(keyCouple[0])&&temp1.equals(keyCouple[1])){//���䣬�����ڻ���
				System.out.println("��"+keyCouple[0]+"�͵�"+keyCouple[1]+"�����ڻ���");
			}else if (!temp0.equals(keyCouple[0])) {//��һ�����ڻ���
				if (!temp1.equals(keyCouple[1])) {//�ڶ���Ҳ�ڻ������һ��ʼ����ͬһ�仨������������Զ�Ļ�������ͬһ��
					System.out.println("����Ϊ����C�м��������µıߣ������ܳ�����һ�����ͷ");
					System.out.println("��ʱ��һ����һ����İְ־����Ƕ�󻨣����Ǵ󻨲�һ���������󻨰���");
					return null;
				}else {//�ڶ��㲻�ڻ���,�����Ļ�����һ���������������������ϲ�Ļ������ϲ㻨Ҫ���²㻨Ϊ�ڽӵ���ڶ������
					flowers.markFromWhichPointGoOut(temp0, keyCouple[0], temp1, "+");
					temp0 = flowers.getThefarthestFlowerIdForCommonPoint(temp0,temp1);//��һ��ȥѰ����Զ����
				}
			}else{////��һ���㲻�ڻ���ڶ������ڻ���
				flowers.markFromWhichPointGoOut(temp1, keyCouple[1], temp0, "+");
				temp1 = flowers.getThefarthestFlowerIdForCommonPoint(temp1,temp0);//�ڶ���ȥѰ����Զ����
			}
		}
		return temp0+","+temp1;
	}
	
	public String openFlowerInPath(String path){//չ��path�ϵĻ�,�Ѿ�ȷ�ϳ����˻������ǲ��϶�·������û�л�
		String resultPath = "";
		String[] pointInPath = path.split(",");
		for (int i = 0;i < pointInPath.length;i++) {
			if (Integer.valueOf(pointInPath[i])>points.getPointsNum()) {
				if (i==0) {//����Ϊ��㣬
					resultPath+=flowers.giveOutPathThroughFlower(null, pointInPath[i], pointInPath[i+1]);
				}else {//�����л��ӻ������
					resultPath+=flowers.giveOutPathThroughFlower(pointInPath[i-1],pointInPath[i],pointInPath[i+1]);//��һ������·�ϣ��������ǵ�һ���㣬Ҳ�����������һ����
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
		if(!my.setInitState()){//��ʼ��ʧ�ܣ��������
			return;
		}else{
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("��ӡ��ʱ���ھ�");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("��ӡ��ʱ���ھ�");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("��ӡ��ʱ���ھ�");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("��ӡ��ʱ���ھ�");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.updateMyState();
			my.available.printPieWeight();
			System.out.print("��ӡ��ʱ���ھ�");
			System.out.println(my.TempNeighbor);
			
			my.calculateState();
			my.getMatch().printMatchInfo();
		}
	}

}
