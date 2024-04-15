import { Component }                    from '@angular/core';
import { StorageHelper }                from '@helpers/storage.helper';
import { ChatMessage }                  from '@models/chat-message.model';
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

  messages: ChatMessage[] = [];
  message: string = '';

  constructor(private chatService: ChatService, private webSocketService: WebsocketService) {
    this.webSocketService.loadMessageObservable
        .pipe(untilDestroyed(this))
        .subscribe((id) => this.loadMessages(id));
  }

  private loadMessages(recipient: string) {
    let userID = StorageHelper.getUserID();
    this.chatService
        .getAllChats(userID, recipient)
        .pipe(untilDestroyed(this))
        .subscribe({
          next: (value) => {
            (value || []).forEach((message: ChatMessage) => {
              message.own = (message.senderId === userID);
            });
            this.messages = value;
          },
          error: (err) => console.error(err),
        })
  }

  async sendMessage(event: any) {
    let response = await this.webSocketService.sendMessage(this.message);
    if (response) {
      this.messages.push(response.message);
      this.message = '';
    }
  }
}
