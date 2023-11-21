import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SseServiceService {

  getEventSource(url: string):EventSource {
    return new EventSource(url);
  }
}
