export class AuthResponse {
    constructor(
      public accessToken: string,
      public authenticated: boolean,
      public type = 'Bearer',
      public refreshToken: string,
      public id: number,
      public username: string,
      public email: string,
      public user: any,
      public roles: string[]
    ) {}

  }
