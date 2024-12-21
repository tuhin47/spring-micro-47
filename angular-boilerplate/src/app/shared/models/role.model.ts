import { Privilege } from '@models/privilege.model';

export class Role {
  id: number;
  name: string;
  privileges: Privilege[];

  constructor(roleId: number, name: string, privileges: Privilege[]) {
    this.id = roleId;
    this.name = name;
    this.privileges = privileges;
  }
}
