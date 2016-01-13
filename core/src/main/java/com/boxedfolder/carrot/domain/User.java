/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "users")
@Entity
public class User extends AbstractEntity implements UserDetails {
    public static final String ROLE_USER = "USER";

    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 6)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @JsonIgnore
    @Column(name = "activation_token")
    private String activationToken;

    @JsonIgnore
    @Column(name = "reset_token")
    private String resetToken;

    @Transient
    private String token;

    @JsonIgnore
    @Transient
    private Set<GrantedAuthority> authorities;


    public User() {
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public String toString() {
        return "{enabled:" + isEnabled() + ", username:'" + getUsername() + "', password:'" + getPassword() + "'}";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(role(User.ROLE_USER)));
        }

        return authorities;
    }

    @JsonIgnore
    @JsonProperty
    @Override
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String role(String i) {
        return "ROLE_" + i;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    @JsonProperty
    public String getToken() {
        return token;
    }

    @JsonProperty
    @JsonIgnore
    public void setToken(String token) {
        this.token = token;
    }
}
