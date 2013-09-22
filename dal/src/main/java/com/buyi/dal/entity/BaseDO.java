package com.buyi.dal.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode().
 * 
 * @author Ju
 */
public abstract class BaseDO<Key extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Key id;

	private Date gmtCreate;

	private Date gmtModified;

	/**
	 * @return <code>null</code> means a transient DO.
	 */
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return a String representation of this DO.
	 */
	public String toString() {
		return getClass().getName() + "[id=" + this.id + ']';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		BaseDO other = (BaseDO) obj;
		if (this.id == null || other.id == null) {
			return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}

}
