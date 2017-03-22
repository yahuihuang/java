package info.codingfun.restful.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceBase {
	@Autowired
	private SessionFactory sessionFactory;
	private static Logger logger = LogManager.getLogger(ServiceBase.class.getName());
	
	protected Session getSession() {
		Session session = sessionFactory.openSession();
		logger.debug("session:" + session.hashCode());
		return session;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		logger.debug(sessionFactory.toString());
	}
}
