// Angular modules
import { Component, OnInit }                  from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router }             from '@angular/router';

// Internal modules
import { environment }                  from '@env/environment';
import { StorageHelper }                from '@helpers/storage.helper';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';

// Services
import { AppService }   from '@services/app.service';
import { AuthService }  from '@services/auth.service';
import { StoreService } from '@services/store.service';
import { UserService }  from '@services/user.service';

@UntilDestroy()
@Component({
  selector    : 'app-login',
  templateUrl : './login.component.html',
  styleUrls   : ['./login.component.scss']
})
export class LoginComponent implements OnInit
{
  public appName : string = environment.appName;
  googleURL = environment.GOOGLE_AUTH_URL;
  facebookURL = environment.FACEBOOK_AUTH_URL;
  githubURL = environment.GITHUB_AUTH_URL;
  linkedinURL = environment.LINKEDIN_AUTH_URL;

  public formGroup !: FormGroup<{
    email    : FormControl<string>,
    password : FormControl<string>,
  }>;

  constructor
  (
    private router       : Router,
    private storeService : StoreService,
    private appService   : AppService,
    private route : ActivatedRoute,
    private userService : UserService,
    private authService: AuthService,
  )
  {
    this.initFormGroup();
  }

    ngOnInit(): void {
        const token: string | null = (this.route.snapshot.queryParamMap.get("token") || StorageHelper.getToken());
        if(token) {
            StorageHelper.setToken(token);
            this.userService.getCurrentUser()
                .pipe(untilDestroyed(this))
                .subscribe(
                    {
                        next: (data) => {
                            StorageHelper.setAuthResponse(data);
                            this.router.navigate(['/home']);
                        },
                        error: (err) => console.error(err)
                    }
                );
        }

    }

  // -------------------------------------------------------------------------------
  // NOTE Init ---------------------------------------------------------------------
  // -------------------------------------------------------------------------------

  private initFormGroup() : void
  {
    this.formGroup = new FormGroup({
      email      : new FormControl<string>({
        value    : '',
        disabled : false
      }, { validators : [Validators.required, Validators.email], nonNullable : true }),
      password   : new FormControl<string>({
        value    : '',
        disabled : false
      }, { validators : [Validators.required], nonNullable : true })
    });
  }

  // -------------------------------------------------------------------------------
  // NOTE Actions ------------------------------------------------------------------
  // -------------------------------------------------------------------------------

  public async onClickSubmit() : Promise<void>
  {
    await this.authenticate();
  }

  // -------------------------------------------------------------------------------
  // NOTE Requests -----------------------------------------------------------------
  // -------------------------------------------------------------------------------

  private async authenticate() : Promise<void>
  {
    this.storeService.setIsLoading(true);

    const email    = this.formGroup.controls.email.getRawValue();
    const password = this.formGroup.controls.password.getRawValue();
    // const success  = await this.appService.authenticate(email, password);

    this.authService
        .login(email, password)
        .pipe(untilDestroyed(this))
        .subscribe(
          {
            next: value => {
              console.log(value)
              StorageHelper.removeToken();
              StorageHelper.setAuthResponse(value);
              this.router.navigate(['/home']);
            },
            error: err => {
              console.error(err)
            },
            complete: () => {
              this.storeService.setIsLoading(false);
            }
          }
        );
    // NOTE Redirect to home
  }

  // -------------------------------------------------------------------------------
  // NOTE Helpers ------------------------------------------------------------------
  // -------------------------------------------------------------------------------

}
