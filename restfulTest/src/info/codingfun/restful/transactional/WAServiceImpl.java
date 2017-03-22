package info.codingfun.restful.transactional;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.ParameterMode;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import info.codingfun.restful.beanwa.WABlog;
import info.codingfun.restful.beanwa.WABlogPK;
import info.codingfun.restful.beanwa.WAIPDeny;
import info.codingfun.restful.constants.BLOG_STATUS;
import info.codingfun.restful.constants.DATA_ENTRY_TYPE;

@Transactional
@Repository("WAServiceDao")
public class WAServiceImpl extends ServiceBase implements WAServiceDao {
	protected Logger logger = LogManager.getLogger(this.getClass().getName());

	//0.Start
	@Override
	public boolean modifyWA(DATA_ENTRY_TYPE data_entry_type, Object object) {
		Session mySession = getSession();
		Transaction trx= null;
		try {
			trx = mySession.getTransaction();
			trx.begin();
			switch (data_entry_type) {
			case ADD : mySession.save(object);break;
			case MODIFY : mySession.saveOrUpdate(object);break;
			case UPDATE : mySession.update(object);break;
			case DELETE : mySession.delete(object);break;
			default:
				break;
			}
			
			trx.commit();
			return true;
		} catch (Exception e) {
			logger.error(e);
			if (trx != null)
				trx.rollback();
			return false;
		} finally {
			if (mySession != null && mySession.isOpen() == true)
				mySession.close();
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<WABlog> retriveWABlog(int iMaxRecord) {
		Session session = getSession();
		try {
			TypedQuery<WABlog> query = session.createQuery(" FROM WABlog c Order By blogId desc");
			query.setMaxResults(iMaxRecord);
			query.setFirstResult(0);
			List<WABlog> waBlogList = query.getResultList();
			return waBlogList;
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (session != null && session.isOpen() == true)
				session.close();
		}
	}
	
	public List<WABlog> retriveWABlogNew(int iMaxRecord,int iLastMaxId) {
		Session session = getSession();
		try {
			TypedQuery<WABlog> query = session.createQuery(
					" FROM WABlog c WHERE blogId > :BlogId Order By blogId asc");
			query.setParameter("BlogId", iLastMaxId);
			query.setMaxResults(iMaxRecord);
			query.setFirstResult(0);
			List<WABlog> waBlogList = query.getResultList();
			return waBlogList;
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (session != null && session.isOpen() == true)
				session.close();
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public WABlog retriveWABlogData(int blogId) {
		Session session = getSession();
		try {
			TypedQuery<WABlog> query = session.createQuery(
					" FROM WABlog c WHERE blogId = :BlogId");
			query.setParameter("BlogId", blogId);
			
			List<WABlog> waBlogList = query.getResultList();
			if (waBlogList.size() > 0)
				return waBlogList.get(0);
			return null;
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (session != null && session.isOpen() == true)
				session.close();
		}
	}

	@Override
	public WABlog addWABlog(String title,String slogan,String description,String content,
			int imgKind,String imgUrl,String modifyEmp) {
		Session mySession = getSession();
		Transaction trx= null;
		try {
			trx = mySession.getTransaction();
			trx.begin();

			ProcedureCall procedureCall = mySession.createStoredProcedureCall("WAAddBlog");
			
			procedureCall.registerParameter("Title", String.class, ParameterMode.IN);
			procedureCall.registerParameter("Slogan", String.class, ParameterMode.IN);
			procedureCall.registerParameter("Description", String.class, ParameterMode.IN);
			procedureCall.registerParameter("Content", String.class, ParameterMode.IN);
			procedureCall.registerParameter("ImgKind", Integer.class, ParameterMode.IN);
			procedureCall.registerParameter("ImgUrl", String.class, ParameterMode.IN);
			procedureCall.registerParameter("ModifyEmp", String.class, ParameterMode.IN);

			procedureCall.getParameterRegistration("Title").bindValue(title);
			procedureCall.getParameterRegistration("Slogan").bindValue(slogan);
			procedureCall.getParameterRegistration("Description").bindValue(description);
			procedureCall.getParameterRegistration("Content").bindValue(content);
			procedureCall.getParameterRegistration("ImgKind").bindValue(imgKind);
			procedureCall.getParameterRegistration("ImgUrl").bindValue(imgUrl);
			procedureCall.getParameterRegistration("ModifyEmp").bindValue(modifyEmp);

			
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List<Object> waBlogList = resultSetOutput.getResultList();
			trx.commit();
			
			if (waBlogList.size() > 0) {
				Object[] objects = (Object[]) waBlogList.get(0);
				int Id = (int) objects[0];
				WABlog waBlog = new WABlog(new WABlogPK(Id));
				waBlog.setTitle((String) objects[1]);
				waBlog.setSlogan((String) objects[2]);
				waBlog.setDescription((String) objects[3]);
				waBlog.setModifyTime((Date) objects[4]);
				waBlog.setModifyEmp((String) objects[5]);
				logger.debug("waBlog : " + waBlog);
				return waBlog;
			} else
				return null;
		} catch (Exception e) {
			logger.error(e);
			if (trx != null)
				trx.rollback();
			return null;
		} finally {
			if (mySession != null && mySession.isOpen() == true)
				mySession.close();
		}
	}	
	
	@Override
	public boolean modifyWABlogStatus(int blogId,int status,String modifyEmp) {
		Session mySession = getSession();
		Transaction trx= null;
		try {
			trx = mySession.getTransaction();
			trx.begin();
			String sHql = "UPDATE WABlog SET status = :Status,modifyTime = :ModifyTime,modifyEmp = :ModifyEmp WHERE blogId = :BlogId ";
			if (status == BLOG_STATUS.BLOG_EDITING.getValue()) {
				sHql += " AND status in (2,5,6,9) ";
			} else if (status == BLOG_STATUS.BLOG_AUDIT.getValue()) {
				sHql += " AND status = 1 ";
			} else if (status == BLOG_STATUS.BLOG_ONBOARD.getValue()) {
				sHql += " AND status = 2 ";
			} else if (status == BLOG_STATUS.BLOG_CANCEL.getValue()) {
				sHql += " AND status = 1 ";
			} else if (status == BLOG_STATUS.BLOG_REVOCATION.getValue()) {
				sHql += " AND status = 5 ";
			}
			TypedQuery<WABlog> query = mySession.createQuery(sHql);
			query.setParameter("Status", status);
			query.setParameter("BlogId", blogId);
			query.setParameter("ModifyTime", new Date());
			query.setParameter("ModifyEmp", modifyEmp);
			 
			int iUpdCount = query.executeUpdate();
			logger.debug("iUpdCount (WABlog) : " + iUpdCount);
			
			trx.commit();
			return (iUpdCount > 0) ? true : false;
		} catch (Exception e) {
			logger.error(e);
			if (trx != null)
				trx.rollback();
			return false;
		} finally {
			if (mySession != null && mySession.isOpen() == true)
				mySession.close();
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<WAIPDeny> retriveWAIPDeny() {
		Session session = getSession();
		try {
			TypedQuery<WAIPDeny> query = session.createQuery(" FROM WAIPDeny c ORDER BY NetworkIp ASC ");
			
			List<WAIPDeny> rows = query.getResultList();
			return rows;
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (session != null && session.isOpen() == true)
				session.close();
		}
	}
}
