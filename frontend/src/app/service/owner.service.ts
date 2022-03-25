import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {MessageService} from "./message.service";
import {Observable, of, tap} from "rxjs";
import {Owner} from "../dto/owner";
import {catchError} from "rxjs/operators";

const baseUri = environment.backendUrl + '/owners';

@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {
  }

  getAll(): Observable<Owner[]> {
    return this.http.get<Owner[]>(baseUri)
      .pipe(
        tap(_ => this.log('fetched owners')),
        catchError(this.handleError<Owner[]>('getAll', []))
      )
  }

  /**
   * Sends owner data to server to be created.
   *
   * @param owner data of owner to be created
   */
  create(owner: Owner): Observable<Owner>{
    return this.http.post<Owner>(baseUri,owner)
      .pipe(catchError(this.handleError<Owner>('create')))
  }

  /**
   * Gets possible owners.
   * Matches based on name.
   *
   * @param searchString string to match owner names with
   */
  searchOwner(searchString: string):Observable<Owner[]>{
    let queryParams = new HttpParams()
      .append("name",searchString);

    return this.http.get<Owner[]>(baseUri,{params: queryParams})
      .pipe(catchError(this.handleError<Owner[]>('searchOwner',[])))
  }


  /**
   * Handle Http operation that failed.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      switch (error.status) {
        case 422:
          this.log('Submit failed because of invalid input.')
          break;
        default:
          this.log(`${operation} failed: ${error.message}`);
          return of(result as T); // Let the app keep running by returning an empty result.
      }
    };
  }

  log(message: string) {
    console.log(message)
    this.messageService.add(`${message}`);
  }
}
