import {Injectable} from '@angular/core';
import {IContact} from './contact';

@Injectable({
    providedIn: 'root'
})
export class ContactService {
    getContacts(): IContact[] {
        return [
            /*{
                'avatar': 'https://bootdey.com/img/Content/avatar/avatar1.png',
                'name': 'John Doe',
                'messages': ['First Message', 'Previous Message'],
                'time': '10:20 PM',
                'status': 'online'
            },
            {
                'avatar': 'https://bootdey.com/img/Content/avatar/avatar2.png',
                'name': 'Mark Doe',
                'messages': ['0', '0'],
                'time': '10:10 PM',
                'status': 'offline'
            },
            {
                'avatar': 'https://bootdey.com/img/Content/avatar/avatar3.png',
                'name': 'Jean Doe',
                'messages': ['1', '1'],
                'time': '10:00 PM',
                'status': 'online'
            }*/
        ];
    }
}
