package com.blair.blairspring.model.userschema;

import com.blair.blairspring.model.validation.AdminRegistration;
import com.blair.blairspring.model.validation.UserRegistration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@XmlRootElement
public class User implements UserDetails {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(groups = {UserRegistration.class, AdminRegistration.class})
    @Size(min = 4, max = 25)
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(groups = {UserRegistration.class, AdminRegistration.class})
    @Size(min = 6, max = 40, groups = {UserRegistration.class, AdminRegistration.class})
    private String password;

    @Column(name = "enabled")
    @NotNull(groups = AdminRegistration.class)
    private Boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    @NotEmpty(groups = AdminRegistration.class)
    @JsonIgnoreProperties("users")
    private Set<Role> roles;

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
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
}
