import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {FormBuilder, Validators} from '@angular/forms';

import {HorseService} from "../../service/horse.service";
import {Horse} from "../../dto/horse";
import {debounceTime, distinctUntilChanged, Observable, of, switchMap} from "rxjs";
import {catchError} from "rxjs/operators";
import {Owner} from "../../dto/owner";
import {OwnerService} from "../../service/owner.service";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'app-horse-create',
  templateUrl: './horse-create.component.html',
  styleUrls: ['./horse-create.component.scss']
})
export class HorseCreateComponent implements OnInit {

  horseForm = this.formBuilder.group({
    name: '',
    description: '',
    dateOfBirth: '',
    sex: 'male',
    owner: null,
    father: null,
    mother: null
  })

  ownerFormatter = (result: Owner) => result.firstName + " " + result.lastName;
  formatter = (result: Horse) => result.name;

  searchOwner = (searchText: Observable<string>) => searchText.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap((text) => this.ownerService.searchOwner(text)))
    .pipe(catchError(() => of([]))
    )

  searchFather = (searchText: Observable<string>) => searchText.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap((text) => this.horseService.searchParent(
      this.horseForm.value.dateOfBirth,
      'male',
      text))).pipe(catchError(() => of([])));

  searchMother = (searchText: Observable<string>) => searchText.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap((text) => this.horseService.searchParent(
      this.horseForm.value.dateOfBirth,
      'female',
      text))).pipe(catchError(() => of([])));

  constructor(
    private location: Location,
    private horseService: HorseService,
    private ownerService: OwnerService,
    private formBuilder: FormBuilder,
    private messageService: MessageService
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
      ownerId: value.owner ? value.owner.id : null,
      fatherId: value.father == null ? null : value.father.id,
      motherId: value.mother == null ? null : value.mother.id
    };

    this.horseService.create(horse)
      .subscribe({
        error: (e) => this.messageService.error(e.error.message),
        complete: () => {
          this.messageService.success("Successfully created horse");
          this.horseForm.reset();
        }
      });
  }

  get name() {
    return this.horseForm.get('name');
  }
}
