// Angular modules
import {Component, OnInit} from '@angular/core';
import { FormGroup }    from '@angular/forms';
import { FormControl }  from '@angular/forms';
import { Validators }   from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';

// Internal modules
import { environment }  from '@env/environment';
import {UntilDestroy, untilDestroyed} from '@ngneat/until-destroy';

// Services
import { AppService }   from '@services/app.service';
import { StoreService } from '@services/store.service';
import {UserService} from "@services/user.service";
import {StorageKey} from "@enums/storage-key.enum";
import {StorageHelper} from "@helpers/storage.helper";

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
    private userService : UserService
  )
  {
    this.initFormGroup();
  }

    ngOnInit(): void {
        const token: string | null = this.route.snapshot.queryParamMap.get("token");
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
    const success  = await this.appService.authenticate(email, password);

    this.storeService.setIsLoading(false);

    if (!success)
      return;

    // NOTE Redirect to home
    this.router.navigate(['/home']);
  }

  // -------------------------------------------------------------------------------
  // NOTE Helpers ------------------------------------------------------------------
  // -------------------------------------------------------------------------------

}
