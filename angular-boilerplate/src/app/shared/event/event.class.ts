export class EventData {
  name: string;
  value: any;

  constructor(name: string, value: any) {
    this.name = name;
    this.value = value;
  }
}

export const EVENT_TYPES = {
  LOGIN: 'login',
  LOGOUT: 'logout',
  SIDEBAR: 'sidebar',
  EDIT_ROLE: 'edit_role',
  ADD_ROLE: 'add_role',
}