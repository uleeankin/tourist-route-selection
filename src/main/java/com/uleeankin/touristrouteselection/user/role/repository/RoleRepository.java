package com.uleeankin.touristrouteselection.user.role.repository;

import com.uleeankin.touristrouteselection.user.role.model.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String role);
}
