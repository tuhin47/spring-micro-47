// Angular modules
import { NgModule }          from '@angular/core';
import { Routes }            from '@angular/router';
import { RouterModule }      from '@angular/router';
import {AuthComponent} from "../auth/auth/auth.component";
import {LoginComponent} from "../auth/auth/login/login.component";
import {RegisterComponent} from "../auth/auth/register/register.component";
import {ForgotPasswordComponent} from "../auth/auth/forgot-password/forgot-password.component";
import {ValidateAccountComponent} from "../auth/auth/validate-account/validate-account.component";
import {ChatComponent} from "./chat.component";
import {ContactComponent} from "./contact/contact.component";

// Components


const routes : Routes = [
  {
    path      : '',
    component : ChatComponent,
  }
];

@NgModule({
  imports : [RouterModule.forChild(routes)],
  exports : [RouterModule]
})
export class ChatRoutingModule { }
