import { Component, Input, OnInit }     from '@angular/core';
import { Router }                       from '@angular/router';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { MenuService }                  from '@services/menu.service';

@UntilDestroy()
@Component({
  selector: 'app-menu-form',
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.scss']
})
export class MenuFormComponent implements OnInit {

  @Input() id?: number;

  menu: any = {
    label: '',
    icon: '',
    parent: ''
  };
  parentMenus: any[] = [];

  constructor(
    private menuService: MenuService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    if (this.id) {
      this.menuService.getMenuById(this.id).pipe(untilDestroyed(this)).subscribe(menu => {
        this.menu = menu;
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
    if (this.menu.label && this.menu.icon) {
      this.router.navigate(['/menu']);
      // this.menuService.saveMenu(this.menu).pipe(untilDestroyed(this)).subscribe(() => {});
    }
  }
}
