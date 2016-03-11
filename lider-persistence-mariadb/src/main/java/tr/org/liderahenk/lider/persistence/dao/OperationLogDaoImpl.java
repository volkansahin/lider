package tr.org.liderahenk.lider.persistence.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import tr.org.liderahenk.lider.core.api.persistence.dao.IOperationLogDao;
import tr.org.liderahenk.lider.core.api.persistence.entities.IOperationLog;
import tr.org.liderahenk.lider.core.api.persistence.enums.CrudType;
import tr.org.liderahenk.lider.persistence.entities.OperationLogImpl;

public class OperationLogDaoImpl implements IOperationLogDao {

	EntityManager entityManager;

	private final static Logger logger = Logger.getLogger(OperationLogDaoImpl.class.getName());
	
	public void init() {
		logger.info("Initializing command DAO.");
	}

	public void destroy() {
		logger.info("Destroying command DAO.");
	}

	@Override
	public IOperationLog createLog(Long id, Date date, String userId, String pluginId, String taskId, String action,
			String serverIp, String resultCode, String logText, String checksum, CrudType crudType, String clientCN)
					throws SecurityException, IOException {

		IOperationLog returnLog = null;

		// OperationLogImpl log = new OperationLogImpl(id, date, userId,
		// pluginId,
		// taskId, action, serverIp, resultCode,
		// logText , checksum, crudType, clientCN);

		OperationLogImpl log = new OperationLogImpl();
		try {

			log.setCreationDate(new Date());
			log.setActive(true);

			if (log.getId() == null) {
				this.entityManager.persist(log);
				returnLog = log;
			} else {
				returnLog = update(log);
			}

		} catch (Exception e) {
			// this.writeToLogFile(log);
		}

		return returnLog;
	}

	@Override
	public IOperationLog update(IOperationLog log) {
		/* Only for updating checksum. */
		return this.entityManager.merge(log);
	}

	@Override
	public List<OperationLogImpl> getLogsByUserId(String userId, int maxResults) {
		String query = "SELECT l FROM OperationLogImpl l " + "WHERE l.userId = :userId ";

		Query q = entityManager.createQuery(query);
		q.setParameter("userId", userId);

		q.setMaxResults(maxResults);

		@SuppressWarnings("unchecked")
		List<OperationLogImpl> list = q.getResultList();
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationLogImpl> getLogsByCrudType(CrudType crudType, int maxResults) {
		return (List<OperationLogImpl>) entityManager
				.createQuery("select model from OperationLogImpl model where model.crudType = :crudType")
				.setParameter("crudType", crudType).setMaxResults(maxResults).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationLogImpl> getLogsByClientCN(String clientCN, int maxResults) {
		List<OperationLogImpl> list = (List<OperationLogImpl>) entityManager
				.createQuery("select model from OperationLogImpl model where model.clientCN = :clientCN")
				.setParameter("clientCN", clientCN).setMaxResults(maxResults).getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationLogImpl> getLogsByResultCode(String resultCode, int maxResults) {
		return (List<OperationLogImpl>) entityManager
				.createQuery("select model from OperationLogImpl model where model.resultCode = :resultCode")
				.setParameter("resultCode", resultCode).setMaxResults(maxResults).getResultList();
	}

	@Override
	public List<OperationLogImpl> getLogsByPluginId(String pluginId, int maxResults) {
		String query = "SELECT l FROM OperationLogImpl l " + "WHERE l.pluginId = :pluginId";

		Query q = entityManager.createQuery(query);
		q.setParameter("pluginId", pluginId);

		q.setMaxResults(maxResults);

		@SuppressWarnings("unchecked")
		List<OperationLogImpl> list = q.getResultList();
		return list;

	}

	@Override
	public List<OperationLogImpl> getLogsByTaskId(String taskId, int maxResults) {

		String query = "SELECT l FROM OperationLogImpl l " + "WHERE l.taskId = :taskId";

		Query q = entityManager.createQuery(query);
		q.setParameter("taskId", taskId);

		q.setMaxResults(maxResults);

		@SuppressWarnings("unchecked")
		List<OperationLogImpl> list = q.getResultList();
		return list;

	}

	@Override
	public List<OperationLogImpl> getLogsByDate(Date startDate, Date finishDate, int maxResults) {
		String query = "SELECT l FROM OperationLogImpl l " + "WHERE l.date between :startDate and :finishDate";
		// + " ORDER BY createdAt DESC";

		Query q = entityManager.createQuery(query);

		q.setParameter("startDate", startDate, TemporalType.DATE);
		q.setParameter("finishDate", finishDate, TemporalType.DATE);

		q.setMaxResults(maxResults);

		@SuppressWarnings("unchecked")
		List<OperationLogImpl> list = q.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationLogImpl> getLogsByText(String freeText, int maxResults) {
		return (List<OperationLogImpl>) entityManager
				.createQuery("select model from OperationLogImpl model where model.logText like :freeText")
				.setParameter("freeText", "%" + freeText + "%").setMaxResults(maxResults).getResultList();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void fileToDB(String path) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
		} catch (Exception e) {

		}

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] rows = line.split("-");

			// this.createLog(rows[0], new Date(rows[1]), rows[2], rows[3],
			// rows[4], rows[5], rows[6], rows[7], rows[8],
			// CrudType.valueOf(rows[9]), rows[10]);
		}

	}

	// @SuppressWarnings({ "unchecked" })
	// @Override
	// public List<? extends IOperationLog> getAllLogs(Integer maxResults) {
	// Query query = entityManager.createQuery("select model from
	// OperationLogImpl model order by model.date desc ");
	//
	// if (null != maxResults)
	// query.setMaxResults(maxResults);
	//
	// return (List<OperationLogImpl>) query.getResultList();
	// }

	// @Override
	// public List<? extends IOperationLog> getLogsBy(Map<String, Object>
	// params, Integer maxResults) {
	// BaseDaoImpl<OperationLogImpl> baseEntity = new
	// BaseDaoImpl<OperationLogImpl>();
	// baseEntity.setEntityManager(entityManager);
	// return baseEntity.findByProperties(OperationLogImpl.class, params, null,
	// maxResults);
	// }
	//
	// @Override
	// public List<? extends IOperationLog> find(IQueryCriteria[] logCriterias,
	// int offset, int maxResults, boolean check)
	// throws Exception {
	//
	// Query q = createQuery(logCriterias, offset, maxResults);
	// List<? extends IOperationLog> logs = q.getResultList();
	//
	// if (check) {
	// List<IOperationLog> checkedLogs = new ArrayList<IOperationLog>();
	// for (IOperationLog iOperationLog : logs) {
	// if
	// (!iOperationLog.getChecksum().equals(CalculateChecksum(iOperationLog))) {
	// checkedLogs.add(iOperationLog);
	// }
	// }
	// return checkedLogs;
	// }
	// return logs;
	// }

	// public static String CalculateChecksum(IOperationLog log) throws
	// NoSuchAlgorithmException {
	// MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
	//
	// byte[] salt = new String("teslaRulz").getBytes();// TODO: base64 encode
	// // for better
	// // checksum
	//
	// if (null != log.getAction() && !log.getAction().isEmpty())
	// messageDigest.update(log.getAction().getBytes());
	// if (null != log.getActive())
	// messageDigest.update(log.getActive().toString().getBytes());
	// if (null != log.getClientCN() && !log.getClientCN().isEmpty())
	// messageDigest.update(log.getClientCN().getBytes());
	// if (null != log.getCrudType())
	// messageDigest.update(log.getCrudType().toString().getBytes());
	// // if(null != log.getDate())
	// // messageDigest.update(log.getDate().toString().getBytes());
	// // if(null != log.getId())
	// // messageDigest.update(log.getId().toString().getBytes());
	// if (null != log.getLogText() && !log.getLogText().isEmpty())
	// messageDigest.update(log.getLogText().getBytes());
	// if (null != log.getServerIp() && !log.getServerIp().isEmpty())
	// messageDigest.update(log.getServerIp().getBytes());
	// if (null != log.getPluginId() && !log.getPluginId().isEmpty())
	// messageDigest.update(log.getPluginId().getBytes());
	// if (null != log.getResultCode() && !log.getResultCode().isEmpty())
	// messageDigest.update(log.getResultCode().getBytes());
	// if (null != log.getTaskId() && !log.getTaskId().isEmpty())
	// messageDigest.update(log.getTaskId().getBytes());
	// if (null != log.getUserId() && !log.getUserId().isEmpty())
	// messageDigest.update(log.getUserId().getBytes());
	//
	// messageDigest.update(salt);
	//
	// byte[] digestBytes = messageDigest.digest();
	//
	// StringBuffer sb = new StringBuffer("");
	//
	// for (int i = 0; i < digestBytes.length; i++) {
	// sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100,
	// 16).substring(1));
	// }
	//
	// return sb.toString();
	// }
	//
	// private Query createQuery(IQueryCriteria[] logCriterias, int offset, int
	// maxResults) throws Exception {
	// CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	// CriteriaQuery<OperationLogImpl> cq =
	// cb.createQuery(OperationLogImpl.class);
	// Root<OperationLogImpl> log = cq.from(OperationLogImpl.class);
	//
	// List<Predicate> predicates = new ArrayList<Predicate>();
	//
	// for (Object criteria : logCriterias) {
	// predicates.add(buildExpression(cb, log, (IQueryCriteria) criteria));
	// }
	//
	// cq.where(predicates.toArray(new Predicate[] {}));
	// cq.select(log);
	// // Order by Creation Date
	// if (null != log.get("creationDate")) {
	// cq.orderBy(cb.desc(log.get("creationDate")));
	// }
	//
	// TypedQuery<OperationLogImpl> q = entityManager.createQuery(cq);
	//
	// if (offset >= 0) {
	// q.setFirstResult(offset);
	// } else {
	// q.setFirstResult(0);
	// }
	// if (maxResults >= 1 && maxResults <= 100) {
	// q.setMaxResults(maxResults);
	// } else {
	// q.setMaxResults(20);
	// }
	// return q;
	// }
	//
	// public Predicate buildExpression(CriteriaBuilder cb, Root root,
	// IQueryCriteria queryCriteria) throws Exception {
	//
	// String fieldName = queryCriteria.getField();
	// CriteriaOperator operator = queryCriteria.getOperator();
	// Object[] values = queryCriteria.getValues();
	//
	// Field field = OperationLogImpl.class.getDeclaredField(fieldName);
	//
	// for (int i = 0; i < values.length; i++) {
	// values[i] = ConvertUtils.convert(values[i], field.getType());
	// }
	//
	// switch (operator) {
	// case EQ:
	// return cb.equal(root.get(fieldName), values[0]);
	// case NE:
	// return cb.notEqual(root.get(fieldName), values[0]);
	// case GT:
	// return cb.greaterThan(root.get(fieldName), (Comparable) values[0]);
	// case GE:
	// return cb.greaterThanOrEqualTo(root.get(fieldName), (Comparable)
	// values[0]);
	// case LT:
	// return cb.lessThan(root.get(fieldName), (Comparable) values[0]);
	// case LE:
	// return cb.lessThanOrEqualTo(root.get(fieldName), (Comparable) values[0]);
	// case BT:
	// return cb.between(root.get(fieldName), (Comparable) values[0],
	// (Comparable) values[1]);
	// case NOT_NULL:
	// return cb.isNotNull(root.get(fieldName));
	// case NULL:
	// return cb.isNull(root.get(fieldName));
	// case IN:
	// return root.get(fieldName).in(values[0]);
	// case NOT_IN:
	// return cb.not(root.get(fieldName).in(values[0]));
	// case LIKE:
	// return cb.like(root.get(fieldName), "%" + values[0] + "%");
	// default:
	// return null;
	//
	// }
	// }
	//
	// @Override
	// public List<? extends IOperationLog> getLogsByDate(Long startDate, Long
	// endDate) {
	//
	// List<OperationLogImpl> list = new ArrayList<OperationLogImpl>();
	//
	// if (endDate != null && endDate > 0) {
	// String query = "SELECT l FROM OperationLogImpl l "
	// + "WHERE l.creationDate between :startDate and :finishDate";
	//
	// Query q = entityManager.createQuery(query);
	//
	// q.setParameter("startDate", new Date(startDate), TemporalType.DATE);
	// q.setParameter("finishDate", new Date(endDate), TemporalType.DATE);
	//
	// // q.setMaxResults(maxResults);
	//
	// list = q.getResultList();
	// } else {
	// String query = "SELECT l FROM OperationLogImpl l " + "WHERE
	// l.creationDate >= :startDate ";
	//
	// Query q = entityManager.createQuery(query);
	//
	// q.setParameter("startDate", new Date(startDate), TemporalType.DATE);
	//
	// // q.setMaxResults(maxResults);
	//
	// list = q.getResultList();
	// }
	//
	// return list;
	// }

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<? extends IOperationLog> getAllLogs(Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends IOperationLog> getLogsBy(Map<String, Object> params, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<? extends IOperationLog> find(IQueryCriteria[] logCriterias, int offset, int maxResults, boolean check)
//			throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<? extends IOperationLog> getLogsByDate(Long startDate, Long endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}