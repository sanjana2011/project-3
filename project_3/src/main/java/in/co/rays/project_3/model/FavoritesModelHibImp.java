package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.FavoritesDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class FavoritesModelHibImp implements FavoritesModelInt {

	@Override
	public long add(FavoritesDTO dto) throws ApplicationException, DuplicateRecordException {
		System.out.println("in addddddddddddd");
		// TODO Auto-generated method stub
		/* log.debug("usermodel hib start"); */

//		UserDTO existDto = null;
//		existDto = findByLogin(dto.getLogin());
//		if (existDto != null) {
//			throw new DuplicateRecordException("login id already exist");
//		}
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			int pk = 0;
			tx = session.beginTransaction();

			System.out.println("trac1");
			session.save(dto);
			System.out.println("trac2");
			tx.commit();
			System.out.println("trac3");
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	@Override
	public void delete(FavoritesDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(FavoritesDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
//		UserDTO existDto = findByLogin(dto.getLogin());
//		// Check if updated LoginId already exist
//		if (existDto != null && existDto.getId() != dto.getId()) {
//			throw new DuplicateRecordException("LoginId is already exist");
//		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public FavoritesDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		FavoritesDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (FavoritesDTO) session.get(FavoritesDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public FavoritesDTO findByLogin(String login) throws ApplicationException {
		Session session = null;
		FavoritesDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (FavoritesDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FavoritesDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(FavoritesDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	@Override
	public List search(FavoritesDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		ArrayList<UserDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getItem() != null && dto.getItem() > 0) {
					criteria.add(Restrictions.eq("item", dto.getItem()));
				}
				if (dto.getDob() != null && dto.getDob().getTime() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
				if (dto.getAccountName() != null && dto.getAccountName().length() > 0) {
					criteria.add(Restrictions.like("AccountName", dto.getAccountName() + "%"));
				}
				if (dto.getNotes() != null && dto.getNotes().length() > 0) {
					criteria.add(Restrictions.like("Notes", dto.getNotes() + "%"));
				}

			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<UserDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}

		return list;
	}

}
