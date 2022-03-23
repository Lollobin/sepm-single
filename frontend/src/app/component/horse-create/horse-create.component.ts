import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {FormBuilder, Validators} from '@angular/forms';

import {HorseService} from "../../service/horse.service";
import {Horse} from "../../dto/horse";
import {debounceTime, distinctUntilChanged, Observable, of, switchMap} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-horse-create',
  templateUrl: './horse-create.component.html',
  styleUrls: ['./horse-create.component.scss']
})
export class HorseCreateComponent implements OnInit {

  horseForm = this.formBuilder.group({
    name: ['', Validators.required],
    description: '',
    dateOfBirth: ['', Validators.required],
    sex: ['male', Validators.required],
    father: '',
    mother: ''
  })
  formatter = (result: Horse) => result.name;

  searchFather = (searchText: Observable<string>) => searchText.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap((text) => this.horseService.searchParent(
      this.horseForm.valid ? this.horseForm.value.dateOfBirth : undefined,
      'male',
      text))).pipe(catchError(() => of([])));

  searchMother = (searchText: Observable<string>) => searchText.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap((text) => this.horseService.searchParent(
      this.horseForm.valid ? this.horseForm.value.dateOfBirth : undefined,
      'female',
      text))).pipe(catchError(() => of([])));

  constructor(
    private location: Location,
    private horseService: HorseService,
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

    let horse: Horse = {
      name: value.name,
      description: value.description,
      dateOfBirth: value.dateOfBirth,
      sex: value.sex,
      fatherId: value.father == null ? null : value.father.id,
      motherId: value.mother == null ? null : value.mother.id
    }

    this.horseService.create(horse)
      .subscribe({next: (horse) => console.log(horse)})

    //console.info('The horse has been submitted', this.horseForm.value);
    this.horseForm.reset();
    this.goBack();
  }

  get name() {
    return this.horseForm.get('name');
  }
}
