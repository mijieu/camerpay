package cm.busime.camerpay.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractServiceDB<T> {

	@PersistenceContext (unitName="CAMERPAYPU")
	private EntityManager em;
	private final Class<T> entityClass;
	
	public AbstractServiceDB() {
		entityClass = null;
	}
	
	public AbstractServiceDB (Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public T read (Object id) {
		return getEntityManager().find(entityClass, id);
	}
	
	public T save(T entity) {
		T merge = getEntityManager().merge(entity);
		return merge;
	}
	
	public void delete (Object entity) {
		if (isAttached(entity)) {
			getEntityManager().remove(entity);
		}
		else {
			getEntityManager().remove(getEntityManager().merge(entity));
		}
	}
	
	public T find (Class<T> entityClass, Object id) {
		return getEntityManager().find(entityClass, id);
	}
	
	public T findFresh(Class<T> entityClass, Object id) {
		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.cache.retrieveMode", "BYPASS");
		return getEntityManager().find(entityClass, id, hints);
	}
	
	public List<T> findAll(Class<T> entityClass){
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	public List<T> findRange(Class<T> entityClass, int[] range){
		return findRange(entityClass, range[0], range[1]);
	}
	
	protected List<T> findRange(Class<T> entityClass, int from, int to){
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(to-from-1);
		q.setFirstResult(from);
		return q.getResultList();
	}
	
	public int count(Class<T> entityClass){
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}
	
	protected boolean isAttached (Object entity) {
		return getEntityManager().contains(entity);
	}
	
	protected void clearCache() {
		getEntityManager().flush();
		getEntityManager().getEntityManagerFactory().getCache().evictAll();
	}
	
}
