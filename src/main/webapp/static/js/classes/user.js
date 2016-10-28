function User() {
    this.username = '';
    this.email = '';
    this.password = '';
    this.confirm = '';
    this.createdAt = null;
    this.enabled = null;
    this.role = null;
}

function User(username, email, createdAt, enabled, role) {
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
    this.enabled = enabled;
    this.role = role;
}
