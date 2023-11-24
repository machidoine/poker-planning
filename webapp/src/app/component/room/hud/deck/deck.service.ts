import {Injectable} from '@angular/core';
import {FIBO_DECK} from "./mock-deck";
import {Observable} from "rxjs";
import {DeckModel} from "../../../../domain/deck.model";

@Injectable({
  providedIn: 'root'
})
export class DeckService {

  getDeck() {
    return new Observable<DeckModel>(o => o.next(FIBO_DECK));
  }

}
