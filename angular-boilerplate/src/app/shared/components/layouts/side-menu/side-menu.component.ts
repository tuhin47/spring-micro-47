import { Component, OnInit }            from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { UserService }                  from '@services/user.service';
import { TreeNode }                     from 'primeng/api';
import { TreeNodeSelectEvent }          from 'primeng/tree/tree.interface';
import { EventBusService }              from '../../../event/event-bus.service';

@UntilDestroy()
@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html'
})
export class SideMenuComponent implements OnInit {
  files!: TreeNode[];
  sidebar: boolean = false;

  constructor(private userService: UserService,
              private eventBusService: EventBusService) {
  }

  ngOnInit() {
    this.eventBusService.on('sidebar', () => this.toggleSideBar());
    this.userService.getFiles()
        .pipe(untilDestroyed(this))
        .subscribe((data) => (this.files = data));
  }

  expandAll() {
    this.files.forEach((node) => {
      this.expandRecursive(node, true);
    });
  }

  collapseAll() {
    this.files.forEach((node) => {
      this.expandRecursive(node, false);
    });
  }

  private expandRecursive(node: TreeNode, isExpand: boolean) {
    node.expanded = isExpand;
    if (node.children) {
      node.children.forEach((childNode) => {
        this.expandRecursive(childNode, isExpand);
      });
    }
  }

  selectionChanged(item: TreeNodeSelectEvent) {
    item.node.expanded = !item.node.expanded;
  };

  private toggleSideBar() {
    this.sidebar = !this.sidebar;
  }
}