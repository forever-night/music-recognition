function User() {
    this.username = '';
    this.email = '';
    this.password = '';
    this.confirm = '';
    this.createdAt = null;
    this.displayCreatedAt = null;
    this.enabled = null;
    this.role = null;
}

function User(username, email, createdAt, enabled, role) {
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
    this.displayCreatedAt = null;
    this.enabled = enabled;
    this.role = role;
}

function User(username, email, createdAt, displayCreatedAt, enabled, role) {
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
    this.displayCreatedAt = displayCreatedAt;
    this.enabled = enabled;
    this.role = role;
}

function copy(user) {
    return new User(
        user.username,
        user.email,
        user.createdAt,
        user.displayCreatedAt,
        user.enabled,
        user.role
    );
}
