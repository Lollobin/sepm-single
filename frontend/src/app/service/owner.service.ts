import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Owner} from "../dto/owner";
import {catchError} from "rxjs/operators";

const baseUri = environment.backendUrl + '/owners';

@Injectable({
  providedIn: 'root'
})

/**
 * Service to access remote owner data.
 */
export class OwnerService {

  constructor(private http: HttpClient) {
  }

  /**
   * Get all owners stored in the system.
   *
   * @return observable list of all owners
   */
  getAll(): Observable<Owner[]> {
    return this.http.get<Owner[]>(baseUri);
  }

  /**
   * Sends owner data to server to be created.
   *
   * @param owner data of owner to be created
   */
  create(owner: Owner): Observable<Owner> {
    return this.http.post<Owner>(baseUri, owner);
  }

  /**
   * Gets possible owners.
   * Matches based on name.
   *
   * @param searchString string to match owner names with
   * @return observable of all matching owners
   */
  searchOwner(searchString: string): Observable<Owner[]> {
    let queryParams = new HttpParams()
      .append("name", searchString);

    return this.http.get<Owner[]>(baseUri, {params: queryParams})
      .pipe(catchError(this.handleError<Owner[]>('searchOwner', [])))
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
      return of(result as T);
    }
  };

}
