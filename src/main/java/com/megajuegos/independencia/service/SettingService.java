package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.settings.AssignCityRequest;
import com.megajuegos.independencia.dto.request.settings.ManageRolesRequest;
import com.megajuegos.independencia.dto.response.settings.SettingsCityResponse;
import com.megajuegos.independencia.dto.response.settings.SettingsGameDataResponse;
import com.megajuegos.independencia.dto.response.settings.SettingsUserResponse;
import com.megajuegos.independencia.entities.City;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface SettingService {

    String createGame();
    String addRoles(ManageRolesRequest request);
    String removeRoles(ManageRolesRequest request);
    City getCiudad(Long id);
    String assignCity(AssignCityRequest request) throws InstanceNotFoundException;
    List<SettingsGameDataResponse> getGames();
    String deactivateGame(Long id);
    String deleteGame(Long id);
    List<SettingsUserResponse> getUsers();

    List<SettingsCityResponse> getCities();
}
