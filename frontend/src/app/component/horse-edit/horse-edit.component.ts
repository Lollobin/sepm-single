import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Horse} from "../../dto/horse";
import {Location} from "@angular/common";
import {HorseService} from "../../service/horse.service";
import {FormBuilder, Validators} from "@angular/forms";
import {debounceTime, distinctUntilChanged, Observable, of, switchMap} from "rxjs";
import {catchError} from "rxjs/operators";
import {HorseParents} from "../../dto/horseParents";
import {Owner} from "../../dto/owner";
import {OwnerService} from "../../service/owner.service";

@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit {
  sub;
  id;
  horse: Observable<HorseParents>;
  horseForm;

  ownerFormatter = (result: Owner) => result.firstName + " " + result.lastName;
  formatter = (result: Horse) => result.name;

  searchOwner = (searchText: Observable<string>) => searchText.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap((text) => this.ownerService.searchOwner(text)))
    .pipe(catchError(() => of([])));

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
    private activatedRoute: ActivatedRoute,
    private location: Location,
    private horseService: HorseService,
    private formBuilder: FormBuilder,
    private ownerService: OwnerService
  ) {
  }

  ngOnInit(): void {
    this.sub = this.activatedRoute.paramMap.subscribe(params => {
      console.log(params);
      this.id = params.get('id');
    })
    this.horse = this.horseService.getOneById(this.id);
    this.horse.subscribe(horse => {
      this.horseForm = this.formBuilder.group({
        name: [horse.name, Validators.required],
        description: horse.description,
        dateOfBirth: horse.dateOfBirth,
        sex: horse.sex,
        owner: horse.owner,
        father: horse.father,
        mother: horse.mother
      })
    })
  }

  onSubmit(): void {
    const value = this.horseForm.value;

    let horse: Horse = {
      name: value.name,
      description: value.description,
      dateOfBirth: value.dateOfBirth,
      sex: value.sex,
      ownerId: value.owner == null ? null : value.owner.id,
      fatherId: value.father == null ? null : value.father.id,
      motherId: value.mother == null ? null : value.mother.id
    }

    this.horseService.edit(this.id, horse).subscribe({next: (horse) => console.log(horse)})
  }

  goBack(): void {
    this.location.back();
  }

  get name() {
    return this.horseForm.get('name');
  }

}
