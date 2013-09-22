package com.buyi.dal.impl.ibatis;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

public abstract class BaseDaoIBatis<K extends Serializable> implements
		InitializingBean {

	protected SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	private SqlExecutor sqlExecutor;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate()
					.getSqlMapClient();
			if (sqlMapClient instanceof SqlMapClientImpl) {
				SqlMapExecutorDelegate delegate = ((SqlMapClientImpl) sqlMapClient)
						.getDelegate();
				Field field = delegate.getClass().getDeclaredField(
						"sqlExecutor");
				field.setAccessible(true);
				field.set(delegate, sqlExecutor);
			} else {
				throw new UnsupportedOperationException(
						"custom SqlExecutor cannot be injected.");
			}
		}
	}

	/**
	 * Set the iBATIS Database Layer SqlMapClient to work with. Either this or a
	 * "sqlMapClientTemplate" is required.
	 * 
	 * @param sqlMapClient
	 *            the configured SqlMapClient
	 */
	@Autowired
	@Required
	public final void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
	}

	/**
	 * Return the SqlMapClientTemplate for this DAO, pre-initialized with the
	 * SqlMapClient or set explicitly.
	 * 
	 * @return an initialized SqlMapClientTemplate
	 */
	public final SqlMapClientTemplate getSqlMapClientTemplate() {
		return this.sqlMapClientTemplate;
	}

	@Autowired(required = false)
	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
}
