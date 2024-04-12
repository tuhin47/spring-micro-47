import { Component, OnInit } from '@angular/core';
import { WebsocketService }  from '@services/websocket.service';


@Component({
  selector: 'app-root',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  message: string = '';
  sendMessage: any;

  constructor(public webSocketService: WebsocketService) {
  }

  ngOnInit(): void {
    this.sendMessage = this.webSocketService.sendMessage;
    this.webSocketService.connect();
  }
}
