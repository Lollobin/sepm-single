import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {debounceTime, distinctUntilChanged, Observable, of, switchMap} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Horse} from '../dto/horse';
import {catchError, map, tap} from 'rxjs/operators';
import {MessageService} from "./message.service";
import {HorseParents} from "../dto/horseParents";

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
        tap(_ => this.log('fetched horses')),
        catchError(this.handleError<Horse[]>('getAll', [])));
  }

  /**
   * Get horse with id from the database
   *
   * @param id id of horse to be fetched.
   */
  getOneById(id: bigint): Observable<HorseParents> {
    return this.http.get<HorseParents>(baseUri + "/" + id)
      .pipe(
        tap(_ => this.log('fetching horse with id ' + id)),
        catchError(this.handleError<HorseParents>('getOneById'))
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
  edit(id: bigint, horse: Horse): Observable<Horse> {
    return this.http.put<Horse>(baseUri + "/" + id, horse)
      .pipe(catchError(this.handleError<Horse>('edit')))
  }

  /**
   * todo add doc
   * @param dateOfBirth
   * @param parentSex
   * @param searchString
   */
  searchParent(dateOfBirth: Date, parentSex: string, searchString: string): Observable<Horse[]> {
    this.log("searchString: "+ searchString)

    let queryParams = new HttpParams()
      .append("dateOfBirth", dateOfBirth.toString())
      .append("parentSex", parentSex)
      .append("searchString", searchString);

    return this.http.get<Horse[]>(baseUri, {params: queryParams})
      .pipe(catchError(this.handleError<Horse[]>('searchParent', [])));
  }

  searchHorse(name: string, description: string, dateOfBirth: Date, sex: string): Observable<Horse[]> {
    let queryParams = new HttpParams();
    if (name != null && name != '')
      queryParams = queryParams.append('name', name);
    if (description != null && description != '')
      queryParams = queryParams.append('description', description);
    if (dateOfBirth != null)
      queryParams = queryParams.append('dateOfBirth', dateOfBirth.toString());
    if (sex != null)
      queryParams = queryParams.append('sex', sex);
    this.log("params " + queryParams.toString())

    return this.http.get<Horse[]>(baseUri + "/search", {params: queryParams})
      .pipe(catchError(this.handleError<Horse[]>('searchHorse', [])));
  }

  /**
   * todo add doc
   * @param id
   */
  delete(id: bigint): Observable<void> {
    this.log("deleting horse with id " + id)
    return this.http.delete<void>(baseUri + "/" + id)
      .pipe(catchError(this.handleError<void>('delete')));
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
