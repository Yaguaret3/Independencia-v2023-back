package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.settings.AssignCityRequest;
import com.megajuegos.independencia.dto.request.settings.CreateGameRequest;
import com.megajuegos.independencia.dto.request.settings.ManageRolesRequest;
import com.megajuegos.independencia.dto.response.settings.SettingsCityResponse;
import com.megajuegos.independencia.dto.response.settings.SettingsGameDataResponse;
import com.megajuegos.independencia.dto.response.settings.SettingsUserResponse;
import com.megajuegos.independencia.entities.City;
import com.megajuegos.independencia.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService service;

    @PostMapping("/create-game")
    public ResponseEntity<String> createGame(@RequestBody CreateGameRequest request){
        return ResponseEntity.ok(service.createGame(request));
    }

    @PostMapping("/add-role")
    public ResponseEntity<String> addRole(@RequestBody ManageRolesRequest request){
        return ResponseEntity.ok(service.addRoles(request));
    }

    @PostMapping("/remove-role")
    public ResponseEntity<String> removeRole(@RequestBody ManageRolesRequest request){
        return ResponseEntity.ok(service.removeRoles(request));
    }

    @GetMapping("/get-city")
    public ResponseEntity<City> getCiudad(@RequestParam Long id){
        return ResponseEntity.ok(service.getCiudad(id));
    }

    @PostMapping("/assign-city")
    public ResponseEntity<String> assignCity(@RequestBody AssignCityRequest request) throws InstanceNotFoundException {
        return ResponseEntity.ok(service.assignCity(request));
    }
    @GetMapping("/users")
    public ResponseEntity<List<SettingsUserResponse>> getUsers(){
        return ResponseEntity.ok(service.getUsers());
    }
    @GetMapping("/games")
    public ResponseEntity<List<SettingsGameDataResponse>> getGames(){
        return ResponseEntity.ok(service.getGames());
    }
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateGame(@PathVariable Long id){
        return ResponseEntity.ok(service.deactivateGame(id));
    }
    @DeleteMapping("/{id}/")
    public ResponseEntity<String> deleteGame(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteGame(id));
    }
    @GetMapping("/cities")
    public ResponseEntity<List<SettingsCityResponse>> getCities(){
        return ResponseEntity.ok(service.getCities());
    }
}
