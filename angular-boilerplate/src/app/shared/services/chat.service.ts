import { Injectable } from '@angular/core';
import { ApiService } from '@services/api.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  constructor(private apiService: ApiService) {
  }

  getAllChats(senderId: string, recipientId: string): Observable<any> {
    return this.apiService.get('chat/messages/' + `${senderId}/${recipientId}`);
  }
}
