import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PersistenceService} from "./persistence.service";

@Injectable({
  providedIn: 'root'
})
export class MapObjectiveNameService extends PersistenceService {

  constructor(http: HttpClient) {
    super("map-objective-name", http);
  }
}
