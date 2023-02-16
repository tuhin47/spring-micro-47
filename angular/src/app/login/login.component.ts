import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {StorageService} from '../_services/storage.service';
import {UntilDestroy, untilDestroyed} from "@ngneat/until-destroy";
import {ActivatedRoute} from "@angular/router";

@UntilDestroy()
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService: AuthService,
              private storageService: StorageService,
              private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.roles = this.storageService.getUser().roles;
    }

    this.activatedRoute.queryParams
      .pipe(untilDestroyed(this))
      .subscribe(
        queryParams => {
          console.log(queryParams)
        }
      )
  }


  onSubmit(): void {
    const {username, password} = this.form;

    this.authService.login(username, password)
      .pipe(untilDestroyed(this))
      .subscribe({
        next: data => {
          this.storageService.saveUser(data);
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.roles = this.storageService.getUser().roles;
          this.reloadPage();
        },
        error: err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      });
  }

  reloadPage(): void {
    window.location.reload();
  }
}
