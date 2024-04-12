import { Component }                    from '@angular/core';
import { UserInfo }                     from '@models/user.model';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { UserService }                  from '@services/user.service';
import { WebsocketService }             from '@services/websocket.service';

@UntilDestroy()
@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./../chat.component.css']
})
export class ContactListComponent {

  contacts: UserInfo[] = [];
  filteredContacts: UserInfo[] = [];

  _contactFilter: string = '';
  get contactFilter() {
    return this._contactFilter;
  }

  set contactFilter(newValue: string) {
    this._contactFilter = newValue;
    this.filteredContacts = this.contactFilter ? this.performFilter(this.contactFilter) : this.contacts;
  }

  constructor(private websocketService: WebsocketService,
              private userService: UserService) {
    this.contactFilter = '';
  }

  performFilter(filterBy: string): UserInfo[] {
    filterBy = filterBy.toLocaleLowerCase();
    return this.contacts.filter(
      (contact: UserInfo) => contact.displayName.toLocaleLowerCase().indexOf(filterBy) > -1
    );
  }

  ngOnInit(): void {
    this.userService.getUsers()
        .pipe(untilDestroyed(this))
        .subscribe(
          data => {
            this.contacts = data;
            this.filteredContacts = this.contacts;
            if (!this.websocketService.activeContact && this.contacts.length > 0) {
              this.setCurrentUser(this.contacts[0]);
            }
          }
        );
  }

  setCurrentUser(contact: UserInfo) {
    this.websocketService.activeContact = contact;
    this.websocketService.loadMessageSub.next(contact.id);
  }
}
