export class UserInfo {
  id: string ;
  displayName: string ;
  email: string;
  avatar: string;
  roles: string[]

  constructor(id: string, displayName: string, email: string, avatar: string, roles: string[]) {
    this.id = id;
    this.displayName = displayName;
    this.email = email;
    this.avatar = avatar;
    this.roles = roles;
  }
}

