import {Component, OnInit} from '@angular/core';
import {UserService} from "@services/user.service";
import {WebsocketService} from "@services/websocket.service";
import {UserInfo} from "@models/user.model";
import {UntilDestroy, untilDestroyed} from "@ngneat/until-destroy";

@UntilDestroy()
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  contacts: UserInfo[] = [];
  filteredContacts: UserInfo[] = [];

  _contactFilter: string = "";
  get contactFilter() {
    return this._contactFilter;
  }

  set contactFilter(newValue: string) {
    this._contactFilter = newValue;
    this.filteredContacts = this.contactFilter ? this.performFilter(this.contactFilter) : this.contacts;
  }

  constructor(private websocketService:WebsocketService,
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
          this.setActiveUser(this.contacts[0]);
        }
      }
    )
  }

  setActiveUser(contact: UserInfo) {
    this.websocketService.activeContact = contact;
  }
}
