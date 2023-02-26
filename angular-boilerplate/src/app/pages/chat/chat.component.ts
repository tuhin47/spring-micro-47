import {Component} from '@angular/core';
import {faCog, faCommentAlt, faPaperclip, faSearch, faUser} from '@fortawesome/free-solid-svg-icons';
import {WebsocketService} from "@services/websocket.service";

@Component({
  selector: 'app-root',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {
  faSetting = faCog;
  faPaperClip = faPaperclip;
  faCommentDots = faCommentAlt;
  faUser = faUser;
  faSearch = faSearch;
  message: string = "";
  sendMessage: any;


  constructor(public webSocketService: WebsocketService) {
    this.sendMessage = webSocketService.sendMessage;
    this.webSocketService.connect();
  }
}
