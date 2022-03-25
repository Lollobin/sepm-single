import {Component, OnInit} from '@angular/core';
import {HorseService} from "../../service/horse.service";
import {Location} from "@angular/common";
import {FormBuilder, Validators} from "@angular/forms";
import {Horse} from "../../dto/horse";
import {debounceTime, distinctUntilChanged, Observable, of, switchMap} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-horse-search',
  templateUrl: './horse-search.component.html',
  styleUrls: ['./horse-search.component.scss']
})
export class HorseSearchComponent implements OnInit {
  results: Horse[];

  horseForm = this.formBuilder.group({
    name: null,
    description: null,
    dateOfBirth: null,
    sex: null,
    father: null,
    mother: null
  })

  nameFormatter = (result: Horse) => result.name;

  searchName = (searchText: Observable<string>) => searchText.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap((text) => this.horseService.searchHorse(
      text,
      this.horseForm.value.description,
      this.horseForm.value.dateOfBirth,
      this.horseForm.value.sex
    ))).pipe(catchError(() => of([])));



  descriptionFormatter = (result: Horse) => result.description;

  constructor(
    private horseService: HorseService,
    private location: Location,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit(): void {
    const value = this.horseForm.value;
     let horse: Horse={
       name: value.name,
         description: value.description,
       dateOfBirth: value.dateOfBirth,
       sex: value.sex
    }


    this.horseService.searchHorse(horse.name, value.description, value.dateOfBirth, value.sex)
      .subscribe({
        next: data => {
          this.results = data;
        }
      })

  }
}
