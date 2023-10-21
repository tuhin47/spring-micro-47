import { NgModule } from '@angular/core';

import { ChatComponent }            from './chat.component';
import { FontAwesomeModule }        from '@fortawesome/angular-fontawesome';
import { ContactComponent }         from './contact/contact.component';
import { ReceivedMessageComponent } from './received-message/received-message.component';
import { SentMessageComponent }     from './sent-message/sent-message.component';
import { ChatRoutingModule }        from './chat-routing.module';
import { SharedModule }             from '../../shared/shared.module';

@NgModule({
  declarations: [
    ChatComponent,
    ContactComponent,
    ReceivedMessageComponent,
    SentMessageComponent,
  ],
  imports: [
    FontAwesomeModule,
    SharedModule,
    ChatRoutingModule
  ],
  providers: [],
})
export class ChatModule {
}

