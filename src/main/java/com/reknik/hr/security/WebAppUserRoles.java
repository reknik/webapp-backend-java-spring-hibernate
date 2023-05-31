package com.reknik.hr.security;

public enum WebAppUserRoles {
    ADMIN("ADMIN"), EMPLOYEE("EMPLOYEE"), USER("USER");

    public final String value;

    WebAppUserRoles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
