import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HorseComponent } from './component/horse/horse.component';
import { HorseCreateComponent} from "./horse-create/horse-create.component";
import {HorseEditComponent} from "./horse-edit/horse-edit.component";

const routes: Routes = [
  {path: '', redirectTo: 'horses', pathMatch: 'full'},
  {path: 'horses', component: HorseComponent},
  {path: 'horses/create', component: HorseCreateComponent},
  {path: 'horses/:id/edit',component: HorseEditComponent},
  {path: '**', redirectTo: 'horses'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
