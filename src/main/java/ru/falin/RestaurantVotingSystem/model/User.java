package ru.falin.RestaurantVotingSystem.model;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

public class User extends AbstractNamedEntity {
    private String email;
    private String password;
    private boolean voted = false;
    private boolean enabled;
    private Date registered = new Date();
    private Set<Role> roles;

    protected User() {
    }

    public User(int id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, false, true, new Date(), EnumSet.of(role, roles));
    }

    public User(int id, String name, String email, String password, boolean voted, boolean enabled, Date registered, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.voted = voted;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id= " + id +
                ", name= " + name +
                ", email= " + email +
                ", voted= " + voted +
                ", roles= " + roles +
                '}';
    }
}
