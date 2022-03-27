import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {HorseService} from "../../service/horse.service";
import {Horse} from "../../dto/horse";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'app-horse-delete',
  templateUrl: './horse-delete.component.html',
  styleUrls: ['./horse-delete.component.scss']
})
export class HorseDeleteComponent implements OnInit {

  sub;
  id;
  horse: Horse;

  constructor(
    private activatedRoute: ActivatedRoute,
    private location: Location,
    private horseService: HorseService,
    private messageService: MessageService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.sub = this.activatedRoute.paramMap.subscribe(params => {
      console.log(params);
      this.id = params.get('id');
    })

    this.horseService.getOneById(this.id).subscribe(horse => {
      this.horse = horse;
    })
  }

  goBack(): void {
    this.location.back();
  }

  delete(): void {
    this.horseService.delete(this.id).subscribe({
      error: (e) => this.messageService.error("Error deleting horse"),
      complete: () => this.router.navigate(["/horses"])
    });
  }
}
