import { Component, Injectable } from '@angular/core';
import { ActivatedRoute, Router, UrlSegment } from '@angular/router';
import { Observable } from 'rxjs';
import { NavigationService } from '../navigation.service';

@Injectable({
  providedIn: 'root'
})
@Component({
  selector: 'app-navigation',
  template: '<button [disabled]="disabled" (click)="navigtiaonService.goBack()">Back</button>',
})
export class NavigationComponent {

  disabled: boolean = ['/', '/farms'].indexOf(this.navigtiaonService.getUrl()) >= 0;

  constructor(private navigtiaonService: NavigationService) { }
}