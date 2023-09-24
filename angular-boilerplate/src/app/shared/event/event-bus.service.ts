import { Injectable }                   from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { Subject, Subscription }        from 'rxjs';
import { filter, map }                  from 'rxjs/operators';
import { EventData }                    from './event.class';

@UntilDestroy()
@Injectable({
  providedIn: 'root'
})
export class EventBusService {
  private subject$ = new Subject<EventData>();

  constructor() {
  }

  emit(event: EventData) {
    this.subject$.next(event);
  }

  on(eventName: string, action: any): Subscription {
    return this.subject$
               .pipe(filter((e: EventData) => e.name === eventName), map((e: EventData) => e['value']))
               .pipe(untilDestroyed(this))
               .subscribe(action);
  }
}
