/**
 * 
 */
package de.ws.wikibeanclient.json;

import com.google.gson.annotations.SerializedName;

import de.ws.wikibeanclient.json.wikidata.WdEntities;
import de.ws.wikibeanclient.json.wikidata.WdEntity;

/**
 * @author Michael
 *
 */
public class Api {

	private String servedby;
	private Login login;
	private Error error;
	private String result;
	private Query query;
	
	@SerializedName("query-continue")
	private QueryContinue querycontinue;
	
	private WdEntities entities;
	private WdEntity entity;
	
	private String success;
	private PageInfo pageinfo;
	
	/**
	 * @return the servedby
	 */
	public String getServedby() {
		return servedby;
	}
	/**
	 * @param servedby the servedby to set
	 */
	public void setServedby(String servedby) {
		this.servedby = servedby;
	}
	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}
	/**
	 * @return the error
	 */
	public Error getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(Query query) {
		this.query = query;
	}
	/**
	 * @return the querycontinue
	 */
	public QueryContinue getQuerycontinue() {
		return querycontinue;
	}
	/**
	 * @param querycontinue the querycontinue to set
	 */
	public void setQuerycontinue(QueryContinue querycontinue) {
		this.querycontinue = querycontinue;
	}
	/**
	 * @return the entities
	 */
	public WdEntities getEntities() {
		return entities;
	}
	/**
	 * @param entities the entities to set
	 */
	public void setEntities(WdEntities entities) {
		this.entities = entities;
	}
	/**
	 * @return the success
	 */
	public String getSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(String success) {
		this.success = success;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the pageinfo
	 */
	public PageInfo getPageinfo() {
		return pageinfo;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param pageinfo the pageinfo to set
	 */
	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}
	/**
	 * @author Michael
	 * @date 01.07.2013
	 * @return the entity
	 */
	public WdEntity getEntity() {
		return entity;
	}
	/**
	 * @author Michael
	 * @date 01.07.2013
	 * @param entity the entity to set
	 */
	public void setEntity(WdEntity entity) {
		this.entity = entity;
	}


}
