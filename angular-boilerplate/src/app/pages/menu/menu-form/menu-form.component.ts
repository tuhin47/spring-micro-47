import { Component, Input, OnInit }           from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router }                             from '@angular/router';
import { UntilDestroy, untilDestroyed }       from '@ngneat/until-destroy';
import { MenuService }                        from '@services/menu.service';

@UntilDestroy()
@Component({
  selector: 'app-menu-form',
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.scss']
})
export class MenuFormComponent implements OnInit {

  @Input() id?: number;

  menuForm: FormGroup;
  parentMenus: any[] = [];

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
    private router: Router
  ) {
    this.menuForm = this.fb.group({
      label: ['', Validators.required],
      icon: ['', Validators.required],
      parent: ''
    });
  }

  ngOnInit(): void {
    if (this.id) {
      this.menuService.getMenuById(this.id).pipe(untilDestroyed(this)).subscribe(menu => {
        this.menuForm.patchValue(menu);
      });
    }
    this.populateMenus();
  }

  private populateMenus() {
    this.menuService.getMenus().pipe(untilDestroyed(this)).subscribe(menus => {
      this.parentMenus = menus;
    });
  }

  onSubmit() {
    if (this.menuForm.valid) {
      const menuData = this.menuForm.value;
      this.menuService.saveMenu(menuData).pipe(untilDestroyed(this)).subscribe(() => {
        this.router.navigate(['/menu']);
      });
    }
  }
}
