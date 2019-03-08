import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CrudService } from '../crud.service';
import { NavigationService } from '../../navigation.service';
import { CrudItem } from '../crud.item';

@Component({
  selector: 'crud-view',
  templateUrl: './crud-view.component.html',
  styleUrls: ['./crud-view.component.css']
})
export class CrudViewComponent<T extends CrudItem> implements OnInit {

  crudItem: T;
  canDelete: boolean = false;
  childNames: string[];

  constructor(
    private route: ActivatedRoute,
    private navigationService: NavigationService,
    private crudService: CrudService<T>) {
  
    this.crudService.setRoute(this.route);
  }

  ngOnInit() {
    
    this.childNames = this.crudService.getChildNames();
    
    this.crudService.get()
      .subscribe((crudItem: T) => this.crudItem = crudItem);
    
    this.crudService.canDelete()
      .subscribe((canDelete: boolean) => this.canDelete = canDelete);
  }

  deleteCrudItem() {
    if (window.confirm('Really Delete?')) {
      this.crudService.delete()
        .subscribe(_ => this.navigationService.goBack() );
    }
  }
}
