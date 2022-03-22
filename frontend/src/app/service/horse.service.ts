import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Horse} from '../dto/horse';
import {catchError, map, tap} from 'rxjs/operators';
import {MessageService} from "./message.service";

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {
  }

  /**
   * Get all horses stored in the system
   *
   * @return observable list of found horses.
   */
  getAll(): Observable<Horse[]> {
    return this.http.get<Horse[]>(baseUri)
      .pipe(
        tap(_=>this.log('fetched horses')),
        catchError(this.handleError<Horse[]>('getAll', [])));
  }

  /**
   * Get horse with id from the database
   *
   * @param id id of horse to be fetched.
   */
  getOneById(id: BigInteger):Observable<Horse>{
    return this.http.get<Horse>(baseUri+"/"+id)
      .pipe(
        tap(_=>this.log('fetching horse with id ' + id)),
        catchError(this.handleError<Horse>('getOneById'))
      )
  }

  /**
   * Sends horse to server to be created.
   *
   * @param horse data of horse to be created.
   */
  create(horse: Horse): Observable<Horse> {
    return this.http.post<Horse>(baseUri, horse)
      .pipe(catchError(this.handleError<Horse>('create')));
  }

  /**
   * Sends data to update a specific horse to server.
   *
   * @param id id of horse to be edited.
   * @param horse new data for horse.
   */
  edit(id:bigint, horse: Horse):Observable<Horse>{
    return this.http.put<Horse>(baseUri+"/"+id,horse)
      .pipe(catchError(this.handleError<Horse>('edit')))
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

      switch (error.status){
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
    this.messageService.add(`${message}`);
  }
}
