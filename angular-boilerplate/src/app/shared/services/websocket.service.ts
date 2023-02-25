import * as Stomp from 'stompjs';
import {Client} from 'stompjs';
import * as SockJS from 'sockjs-client';
import {AuthResponse} from "@models/auth-response.model";
import {StorageHelper} from "@helpers/storage.helper";
import {UserService} from "@services/user.service";
import {Injectable} from "@angular/core";
import {IContact} from "../../pages/chat/contact/contact";

// import { AppComponent } from './app.component';


@Injectable({
  providedIn: 'root',
})
export class WebsocketService {

  constructor(private userService: UserService) {
  }

  private stompClient: Client = Stomp.over(new SockJS("http://localhost:8080/ws"));
  private currentUser: AuthResponse | null = StorageHelper.getUser();
  private activeContact: IContact | undefined;

  connect = () => {
    this.stompClient.connect({}, this.onConnected, this.onError);
  };

  onConnected = () => {
    console.log("connected");
    console.log();
    this.stompClient.subscribe(
      "/user/" + this.currentUser?.id + "/queue/messages",
      this.onMessageReceived,
      this.onError
    );
  };

  onError = (err: any) => {
    console.error(err);
  };

  onMessageReceived = (msg: any) => {
    const notification = JSON.parse(msg.body);
    console.log(notification);
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
    if (msg.trim() !== "") {
      const message = {
        senderId: this.currentUser?.id,
        recipientId: this.activeContact?.id,
        senderName: this.currentUser?.username,
        recipientName: this.activeContact?.name,
        content: msg,
        timestamp: new Date(),
      };
      this.stompClient.send("/app/chat", {}, JSON.stringify(message));

      /* const newMessages = [...messages];
       newMessages.push(message);
       setMessages(newMessages);*/
    }
  };

  loadContacts = () => {
    this.userService.getUsers()

      .subscribe((users: any[]) => {
          if (this.activeContact === undefined && users.length > 0) {
            // setActiveContact(users[0]);
            this.activeContact = users[0];
          }
          return users;/*users.map((contact) =>
          countNewMessages(contact.id, this.currentUser?.id).then((count) => {
            contact.newMessages = count;
            return contact;
          })
        )*/
        }
      );
  };

  setChatUser(contact: IContact) {
    this.activeContact = contact;
  }
}
