package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.auth.ManageRolesRequest;
import com.megajuegos.independencia.entities.City;

import javax.management.InstanceNotFoundException;

public interface SettingService {

    String createGame();
    String addRoles(ManageRolesRequest request);
    String removeRoles(ManageRolesRequest request);
    City getCiudad(Long id);
    String assignCity(Long to, String ciudad) throws InstanceNotFoundException;
}
