import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user = { name: '', email: '', password: '' };
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    this.authService.signup(this.user).subscribe({
      next: (res) => {
        alert('Signup successful! Please login.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage = err.error?.error || 'Signup failed';
      }
    });
  }
}
