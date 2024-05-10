package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.auth.ManageRolesRequest;
import com.megajuegos.independencia.dto.response.settings.SettingsGameDataResponse;
import com.megajuegos.independencia.dto.response.settings.SettingsUserResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.entities.City;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface SettingService {

    String createGame();
    String addRoles(ManageRolesRequest request);
    String removeRoles(ManageRolesRequest request);
    City getCiudad(Long id);
    String assignCity(Long to, String ciudad) throws InstanceNotFoundException;
    List<SettingsGameDataResponse> getGames();
    String deactivateGame(Long id);
    String deleteGame(Long id);
    List<SettingsUserResponse> getUsers();
}
