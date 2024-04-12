import { Component }                    from '@angular/core';
import { StorageHelper }                from '@helpers/storage.helper';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { ChatService }                  from '@services/chat.service';
import { WebsocketService }             from '@services/websocket.service';

@UntilDestroy()
@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./../chat.component.css']
})
export class MessagesComponent {

  messages: any[] = [];

  constructor(private chatService: ChatService, private webSocketService: WebsocketService) {
    this.webSocketService.loadMessageObservable
        .pipe(untilDestroyed(this))
        .subscribe((id) => this.loadMessages(id));
  }

  private loadMessages(recipient: string) {
    this.chatService
        .getAllChats(StorageHelper.getUserID(), recipient)
        .pipe(untilDestroyed(this))
        .subscribe({
          next: (value) => {
            this.messages = value;
          },
          error: (err) => console.error(err),
        })
  }
}
