import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HorseService} from "../../service/horse.service";
import {HorseParents} from "../../dto/horseParents";
import {Location} from "@angular/common";

@Component({
  selector: 'app-horse-details',
  templateUrl: './horse-details.component.html',
  styleUrls: ['./horse-details.component.scss']
})
export class HorseDetailsComponent implements OnInit {

  sub;
  id;
  horse: HorseParents;

  constructor(
    private activatedRoute: ActivatedRoute,
    private horseService: HorseService,
    private location: Location,
  ) { }

  ngOnInit(): void {
    this.sub = this.activatedRoute.paramMap.subscribe(params => {
      console.log(params);
      this.id = params.get('id');
      this.getHorse();
    })
  }

  getHorse(): void{
    this.horseService.getOneById(this.id).subscribe(horse=>{
      this.horse =horse;
    })
  }

  goBack(): void {
    this.location.back();
  }


}
