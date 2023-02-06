package com.pos.dao;

import com.pos.pojo.BrandPojo;
import com.pos.pojo.DaySalesPojo;
import com.pos.service.ApiException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DaySalesDao extends AbstractDao {

	private static final String SELECT_ALL = "select p from DaySalesPojo p";
//TODO: clean up -- move such methods to abstract
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(DaySalesPojo daySalesPojo) {
		entityManager.persist(daySalesPojo);
	}

	@Transactional(readOnly = true)
	public List<DaySalesPojo> selectAll() {
		TypedQuery<DaySalesPojo> query = getQuery(SELECT_ALL, DaySalesPojo.class);
		return query.getResultList();
	}

}
