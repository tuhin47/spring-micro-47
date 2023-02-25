import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {ChatComponent} from './chat.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ContactComponent} from './contact/contact.component';
import {ReceivedMessageComponent} from './received-message/received-message.component';
import {SentMessageComponent} from './sent-message/sent-message.component';
import {ChatRoutingModule} from "./chat-routing.module";
import {CommonModule} from "@angular/common";
import {WebsocketService} from "@services/websocket.service";

@NgModule({
  declarations: [
    ChatComponent,
    ContactComponent,
    ReceivedMessageComponent,
    SentMessageComponent,
  ],
  imports: [
    FontAwesomeModule,
    FormsModule,
    CommonModule,
    ChatRoutingModule
  ],
  providers: [

  ],
})
export class ChatModule {
}

