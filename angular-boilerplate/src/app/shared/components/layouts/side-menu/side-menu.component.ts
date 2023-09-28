import { Component, OnInit }            from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { UserService }                  from '@services/user.service';
import { TreeNode }                     from 'primeng/api';

@UntilDestroy()
@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss']
})
export class SideMenuComponent implements OnInit {
  files!: TreeNode[];
  sidebarVisible1: boolean = false;
  constructor(private userService: UserService) {
  }

  ngOnInit() {
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
}