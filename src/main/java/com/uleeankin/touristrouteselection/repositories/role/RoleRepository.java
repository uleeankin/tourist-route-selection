package com.uleeankin.touristrouteselection.repositories.role;

import com.uleeankin.touristrouteselection.models.user.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String role);
}
