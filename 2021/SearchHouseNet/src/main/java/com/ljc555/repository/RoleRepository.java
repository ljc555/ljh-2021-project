package com.ljc555.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ljc555.entity.Role;

/**
 * 角色数据DAO
 * Created by 瓦力.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findRolesByUserId(Long userId);
}
