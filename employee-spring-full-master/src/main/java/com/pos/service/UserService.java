package com.pos.service;

import com.pos.dao.UserDao;
import com.pos.pojo.UserPojo;
import org.commons.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(UserPojo p) throws ApiException {
        normalize(p);
        UserPojo existing = dao.select(p.getEmail());
        if (existing != null) {
            throw new ApiException("User with given email already exists");
        }
        dao.insert(p);
    }

    @Transactional(rollbackFor = ApiException.class)
    public UserPojo get(String email) throws ApiException {
        return dao.select(email);
    }

    @Transactional(readOnly = true)
    public UserPojo get(int id) throws ApiException {
        UserPojo userPojo = dao.select(id);
        if (Objects.isNull(userPojo)) {
            throw new ApiException("Error: user with id doesn't exist");
        }
        return userPojo;
    }

    @Transactional(readOnly = true)
    public List<UserPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, UserPojo userPojo) throws ApiException {
        UserPojo existingUserPojo = get(userPojo.getEmail());
        if (!Objects.isNull(existingUserPojo)) {
            throw new ApiException("Error: user with this email already exists");
        }
        existingUserPojo = dao.select(id);
        if (Objects.isNull(existingUserPojo)) {
            throw new ApiException("Error: user doesn't exists");
        }
        if (!userPojo.getRole().equals(existingUserPojo.getRole())) {
            throw new ApiException("Error: can't modify fields other than email");
        }
        existingUserPojo.setEmail(userPojo.getEmail());
    }

    protected static void normalize(UserPojo p) {
        p.setEmail(p.getEmail().toLowerCase().trim());
        p.setRole(p.getRole().toLowerCase().trim());
    }
}
