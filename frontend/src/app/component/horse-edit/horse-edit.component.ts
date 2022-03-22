import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Horse} from "../../dto/horse";
import {Location} from "@angular/common";
import {HorseService} from "../../service/horse.service";
import {FormBuilder, Validators} from "@angular/forms";
import {Observable} from "rxjs";

@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit {
  sub;
  id;
  horse: Observable<Horse>;
  horseForm;

  constructor(
    private activatedRoute: ActivatedRoute,
    private location: Location,
    private horseService: HorseService,
    private formBuilder: FormBuilder
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
        sex: horse.sex
      })
    })
  }

  onSubmit(): void {
    this.horseService.edit(this.id, this.horseForm.value).subscribe({next: (horse) => console.log(horse)})
  }

  goBack(): void {
    this.location.back();
  }

  get name() {
    return this.horseForm.get('name');
  }

}
