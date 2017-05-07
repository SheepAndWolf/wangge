package com.match.nonbinary.pointCollection;

public class point {

	/**
	 * @param args
	 */
	private String id;
	private String label = null;
	private float weight;//��
	
	public point(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
//		if(label==null)
//			System.out.println("��"+id+"�ı�ǩΪnull");
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public void changeWeight(float change) {
		this.weight += change;
	}

	@Override
	public String toString() {
		return "point [id=" + id + "]";
	}

}
