package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.RoleEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserIndependencia implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    @JoinTable(name = "join_user_role")
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    private List<RoleEnum> roles;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<PlayerData> playerDataList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(rol -> (GrantedAuthority) rol::name)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
