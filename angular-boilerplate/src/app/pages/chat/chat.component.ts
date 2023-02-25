import {Component, OnInit} from '@angular/core';
import {faCog, faPaperclip, faCommentAlt, faUser, faSearch} from '@fortawesome/free-solid-svg-icons';
import {ContactService} from './contact/contact.service';
import {WebsocketService} from "@services/websocket.service";
import {UserService} from "@services/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
  providers: [ContactService]
})
export class ChatComponent implements OnInit {
  faSetting = faCog;
  faPaperClip = faPaperclip;
  faCommentDots = faCommentAlt;
  faUser = faUser;
  faSearch = faSearch;
  message: string = "";
  sendMessage: any;


  constructor(public webSocketService: WebsocketService) {
    this.sendMessage = webSocketService.sendMessage;
  }

  ngOnInit(): void {
    this.connect();
  }

  private connect() {
    this.webSocketService.connect();
  }
}
