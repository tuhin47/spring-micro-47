export class Privilege {
  id: number;
  name: string;
  description: string;

  constructor(privilegeId: number, name: string, description: string) {
    this.id = privilegeId;
    this.name = name;
    this.description = description;
  }
}
