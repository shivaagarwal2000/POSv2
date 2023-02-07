package com.pos.dao;

import com.pos.pojo.InventoryPojo;
import org.commons.util.ApiException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao {

    private static final String delete_id = "delete from InventoryPojo p where id=:id";
    private static final String select_id = "select p from InventoryPojo p where id=:id";
    private static final String select_all = "select p from InventoryPojo p";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(rollbackFor = ApiException.class)
    public void insert(InventoryPojo inventoryPojo) {
        entityManager.persist(inventoryPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public int delete(int id) {
        Query query = entityManager.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Transactional(readOnly = true)
    public InventoryPojo select(int id) {
        TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional(readOnly = true)
    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

}
