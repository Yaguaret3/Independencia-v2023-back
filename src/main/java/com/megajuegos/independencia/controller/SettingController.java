package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.auth.ManageRolesRequest;
import com.megajuegos.independencia.entities.City;
import com.megajuegos.independencia.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService service;

    @PostMapping("/create-game")
    public ResponseEntity<String> createGame(){
        return ResponseEntity.ok(service.createGame());
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
    public ResponseEntity<String> assignCity(@RequestParam Long to,
                                             @RequestParam String city) throws InstanceNotFoundException {
        return ResponseEntity.ok(service.assignCity(to, city));
    }
}
