import { NgOptimizedImage }  from '@angular/common';
import { NgModule }          from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SharedModule }      from '../../shared/shared.module';
import { ChatRoutingModule } from './chat-routing.module';

import { ChatComponent }        from './chat.component';
import { ContactListComponent } from './contact-list/contact-list.component';
import { MessagesComponent }    from './messages/messages.component';

@NgModule({
  declarations: [
    ChatComponent,
    ContactListComponent,
    MessagesComponent,
  ],
  imports: [
    FontAwesomeModule,
    SharedModule,
    ChatRoutingModule,
    NgOptimizedImage
  ],
  providers: [],
})
export class ChatModule {
}

