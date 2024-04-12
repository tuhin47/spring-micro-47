import { Injectable }    from '@angular/core';
import { StorageHelper } from '@helpers/storage.helper';
import { UserInfo }      from '@models/user.model';
import { UserService }   from '@services/user.service';
import { Subject }       from 'rxjs';
import * as SockJS       from 'sockjs-client';
import * as Stomp        from 'stompjs';
import { Client }        from 'stompjs';

// import { AppComponent } from './app.component';


@Injectable({
  providedIn: 'root',
})
export class WebsocketService {


  constructor(private userService: UserService) {
  }

  private stompClient: Client = Stomp.over(new SockJS('/api/ws'));
  private _activeContact: UserInfo | undefined;
  loadMessageSub = new Subject<string>();
  loadMessageObservable = this.loadMessageSub.asObservable();


  get currentUser(): UserInfo | undefined {
    return StorageHelper.getUser()?.user;
  }

  connect = () => {
    this.stompClient.connect({}, this.onConnected, this.onError);
  };

  onConnected = () => {
    this.stompClient
        .subscribe(
          '/user/' + (this.currentUser?.id) + '/queue/messages',
          this.onMessageReceived,
          this.onError
        );
  };

  onError = (err: any) => {
    console.error(err);
  };

  onMessageReceived = (msg: any) => {
    const notification = JSON.parse(msg.body);
    /* const active = JSON.parse(sessionStorage.getItem("recoil-persist"))
       .chatActiveContact;

     if (active.id === notification.senderId) {
       findChatMessage(notification.id).then((message) => {
         const newMessages = JSON.parse(sessionStorage.getItem("recoil-persist"))
           .chatMessages;
         newMessages.push(message);
         setMessages(newMessages);
       });
     } else {
       message.info("Received a new message from " + notification.senderName);
     }*/
    this.loadContacts();
  };

  sendMessage = (msg: any) => {
    if (msg.trim() !== '') {
      const message = {
        senderId: this.currentUser?.id,
        recipientId: this._activeContact?.id,
        senderName: this.currentUser?.displayName,
        recipientName: this._activeContact?.displayName,
        content: msg,
        timestamp: new Date(),
      };
      this.stompClient.send('/app/chat', {}, JSON.stringify(message));

      /* const newMessages = [...messages];
       newMessages.push(message);
       setMessages(newMessages);*/
    }
  };

  loadContacts = () => {
    /*this.userService.getUsers()

      .subscribe((users: any[]) => {
          if (this._activeContact === undefined && users.length > 0) {
            // setActiveContact(users[0]);
            this._activeContact = users[0];
          }
          return users;/!*users.map((contact) =>
          countNewMessages(contact.id, this.currentUser?.id).then((count) => {
            contact.newMessages = count;
            return contact;
          })
        )*!/
        }
      );*/
  };

  set activeContact(value: UserInfo | undefined) {
    this._activeContact = value;
  }

  get activeContact(): UserInfo | undefined {
    return this._activeContact;
  }
}
