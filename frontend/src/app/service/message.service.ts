import {Injectable} from '@angular/core';
import {Message} from "../dto/message";

@Injectable({
  providedIn: 'root',
})

/**
 * Service for handling messages to the user.
 */
export class MessageService {
  currentMessage: Message = null;
  messages: Message[] = [];

  info(message: string) {
    let newMessage: Message={
      text: message,
      level: 'info'
    }

    this.currentMessage = newMessage;
    this.messages.push(newMessage);
  }

  success(message: string) {
    let newMessage: Message={
      text: message,
      level: 'success'
    }

    this.currentMessage = newMessage;
    this.messages.push(newMessage);
  }

  warning(message: string) {
    let newMessage: Message={
      text: message,
      level: 'warning'
    }

    this.currentMessage = newMessage;
    this.messages.push(newMessage);
  }

  error(message: string) {
    let newMessage: Message={
      text: message,
      level: 'error'
    }

    this.currentMessage = newMessage;
    this.messages.push(newMessage);
  }

  clear() {
    this.currentMessage = null;
    this.messages = [];
  }
}
