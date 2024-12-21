export class UserInfo {
  id: string;
  displayName: string;
  email: string;
  avatar: string;
  authorityNames: string[]

  constructor(id: string, displayName: string, email: string, avatar: string, authorities: string[]) {
    this.id = id;
    this.displayName = displayName;
    this.email = email;
    this.avatar = avatar;
    this.authorityNames = authorities;
  }
}

