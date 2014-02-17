package com.buyi.dal.entity.viewobject;


public class PostfeeVO {

	private int postfeeType; //邮费类型

	private int kuaidi; //快递

	private int ems; //EMS

	private int pinyou; //平邮

	public int getPostfeeType() {
		return postfeeType;
	}

	public void setPostfeeType(int postfeeType) {
		this.postfeeType = postfeeType;
	}

	public int getKuaidi() {
		return kuaidi;
	}

	public void setKuaidi(int kuaidi) {
		this.kuaidi = kuaidi;
	}

	public int getEms() {
		return ems;
	}

	public void setEms(int ems) {
		this.ems = ems;
	}

	public int getPinyou() {
		return pinyou;
	}

	public void setPinyou(int pinyou) {
		this.pinyou = pinyou;
	}
	
	public String getPostInfo() {
		StringBuilder sb = new StringBuilder();
		if (postfeeType == 1) {
			sb.append("卖家承担运费");
		} else {
			if (kuaidi > 0) {
				sb.append("快递:").append(new Money(kuaidi*100).toString());
				sb.append("    ");
			}
			if (ems > 0) {
				sb.append("EMS:").append(new Money(ems*100).toString());
				sb.append("    ");
			}
			
			if (pinyou > 0) {
				sb.append("平邮:").append(new Money(pinyou*100).toString());
			}
		}
		
		return sb.toString();
	}
	
	public String toString() {
		return this.getPostInfo();
	}

}
