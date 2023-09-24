import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-clock',
  templateUrl: './clock.component.html',
  styleUrls: ['./clock.component.scss']
})
export class ClockComponent implements OnInit {

  ngOnInit(): void {
    this.displayTime();
  }


  displayTime() {
    const hr = document.getElementById('hour');
    const min = document.getElementById('min');
    const sec = document.getElementById('sec');

    function displayTime() {
      const date = new Date();

      const hh = date.getHours();
      const mm = date.getMinutes();
      const ss = date.getSeconds();

      const hRotation = 30 * hh + mm / 2;
      const mRotation = 6 * mm;
      const sRotation = 6 * ss;

      if (hr) hr.style.transform = `rotate(${hRotation}deg)`;
      if (min) min.style.transform = `rotate(${mRotation}deg)`;
      if (sec) sec.style.transform = `rotate(${sRotation}deg)`;

    }

    setInterval(displayTime, 1000);
  }
}
