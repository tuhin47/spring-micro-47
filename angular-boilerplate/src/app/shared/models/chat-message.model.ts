import { StorageHelper } from '@helpers/storage.helper';

export class ChatMessage {
  id!: string;
  chatId!: string;
  senderId: string = StorageHelper.getUserID();
  recipientId: string;
  senderName: string = StorageHelper.getUser().displayName;
  recipientName: string;
  content: string;
  timestamp: Date = new Date();
  status!: string;
  own: boolean = true;

  constructor(
    recipientId: string,
    recipientName: string,
    content: string,
  ) {
    this.recipientId = recipientId;
    this.recipientName = recipientName;
    this.content = content;
  }
}