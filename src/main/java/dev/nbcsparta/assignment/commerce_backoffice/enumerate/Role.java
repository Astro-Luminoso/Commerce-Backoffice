package dev.nbcsparta.assignment.commerce_backoffice.enumerate;

public enum Role {
    CS_MANAGER("CS_Manager", 1),
    Ops_MANAGER("Ops_Manager", 2),
    Super_MANAGER("Super_Manager", 3);

    private final String roleName;
    private int accessLevel;

    Role(String roleName, int accessLevel) {
        this.roleName = roleName;
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return this.accessLevel;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
