import {UserInfo} from "@models/user.model";

export class AuthResponse{
  accessToken: string;
  authenticated: boolean;
  user: UserInfo;

  constructor(accessToken: string, authenticated: boolean, user: UserInfo) {
    this.accessToken = accessToken;
    this.authenticated = authenticated;
    this.user = user;
  }
}
