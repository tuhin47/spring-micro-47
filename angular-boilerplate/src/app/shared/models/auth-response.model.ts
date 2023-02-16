export class AuthResponse {
    constructor(
      public token: string,
      public type = 'Bearer',
      public refreshToken: string,
      public id: number,
      public username: string,
      public email: string,
      public roles: string[]
    ) {}    
    
  }