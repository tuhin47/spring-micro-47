import { Component, OnInit }            from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { WebsocketService }             from '@services/websocket.service';
import { ChatService }                  from '@services/chat.service';
import { StorageHelper }                from '@helpers/storage.helper';


@UntilDestroy()
@Component({
  selector: 'app-root',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  message: string = '';
  sendMessage: any;
  messages: any[] = [];

  constructor(public webSocketService: WebsocketService,
              public chatService: ChatService) {
  }

  ngOnInit(): void {
    this.sendMessage = this.webSocketService.sendMessage;
    this.webSocketService.connect();
    if (this.webSocketService.activeContact?.id) {
      this.chatService
          .getAllChats(StorageHelper.getUserID(), this.webSocketService.activeContact.id)
          .pipe(untilDestroyed(this))
          .subscribe({
            next: (value) => {
              this.messages = value;
              console.log(value)
            },
            error: (err) => console.error(err),
          });
    }
  }
}
