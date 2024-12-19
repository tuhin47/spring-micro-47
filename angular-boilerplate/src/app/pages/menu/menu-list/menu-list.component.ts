import { Component, OnInit } from '@angular/core';
import { ActivatedRoute }    from '@angular/router';
import { MenuService }       from '@services/menu.service';

@Component({
  selector: 'app-menu-list',
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.scss']
})
export class MenuListComponent implements OnInit {
  menus: any[] = [];
  editMenuId: number | undefined;
  columns: any[] = [
    {header: 'ID', field: 'id', width: '', noSort: false, noFilter: false,},
    {header: 'Label', field: 'label', width: '', noSort: false, noFilter: false,},
    {header: 'Icon', field: 'icon', width: '', noSort: true, noFilter: true,},
    {header: 'Parent', field: 'parent', width: '', noSort: false, noFilter: false,},
    {header: 'Actions', field: 'actions', width: '', noSort: false, noFilter: false,},
  ];
  isAdd = false;


  constructor(protected menuService: MenuService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.loadMenus();
  }

  loadMenus() {
    this.menuService.getMenus()
        .subscribe(menus => {
          this.menus = menus;
        });
    this.route.params.subscribe(params => {
      const id = +params['id'];
      if (id) {
        this.editMenu(id);
      }
    });

  }

  editMenu(id: number) {
    this.editMenuId = undefined;
    setTimeout(() => this.editMenuId = id);
  }

  deleteMenu(id: number) {
    this.menuService.deleteMenu(id).subscribe(() => {
      this.loadMenus();
    });
  }

  addMenu() {
    this.isAdd = true;
  }
}
